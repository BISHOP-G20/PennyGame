package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.*;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    private int frameRefresh = 0;

    BufferedImage image;
    BufferedImage[] characterImages;
    Font basicFont;
    Font scriptFont;
    Color[] buttonColor;

    Rectangle[] buttons;
    String[] buttonNames;
    String[] credits;

    UtilityTools utilityTools = new UtilityTools();

    String text;
    String errorText;

    private boolean startScreenOn;
    private boolean menuScreenOn;
    private boolean characterSelectScreenOn;
    private boolean gameSaved;
    private boolean loadError;

    private int globalY;
    public  int creditYLocation;

    public UI(GamePanel gp){
        this.gp = gp;

        basicFont = new Font("Arial", Font.BOLD, 80);
        scriptFont = new Font("Segoe Script", Font.PLAIN, 40);

        globalY = - 60;
        gameSaved = false;
        loadError = false;
    }

    public void setColorGray(int i){
        buttonColor[i] = GRAY;
    }
    public void setColorWhite(int i){
        buttonColor[i] = WHITE;
    }

    public void setCharacterSelect(){
        text = "Choose Character";

        int npcNum = gp.getNPCs().size();

        buttonNames = new String[npcNum];

        for(int i = 0; i < npcNum; ++i){
            String name = gp.getNPCs().get(i).getCharacterName();
            buttonNames[i] = name;
        }

        characterImages = new BufferedImage[npcNum];

        for(int i = 0; i < npcNum; ++i){
            try{
            characterImages[i] = gp.getNPCs().get(i).getEntity().getCharDownImage();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        buttonColor = new Color[npcNum];

        for(Color color: buttonColor){
            color = WHITE;
        }

        buttons = new Rectangle[npcNum];

        for(int i = 0; i < npcNum; ++i){

            int xMultiplier = i;
            int ySpaced = 0;

            if(i > 3){
                xMultiplier = i - 4;
                ySpaced = 168;
            }
            buttons[i] = new Rectangle(100 + (xMultiplier * 180), 300 + ySpaced, 180, 150);
        }

    }

    public void drawCharacterSelect(){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.getGAME_WIDTH()/2) - (length/2);
        int y = gp.getScaledTile();

        g2.setColor(WHITE);
        g2.drawString(text, x, y);

        for(int i = 0; i < gp.getNPCs().size(); ++i){

            g2.drawImage(characterImages[i], buttons[i].x + 10, buttons[i].y, gp.getScaledPxlTile() * 2, gp.getScaledPxlTile() * 2, null);
            g2.setColor(buttonColor[i]);
            g2.setFont(basicFont.deriveFont(basicFont.getStyle(), 40F));
            g2.drawString(buttonNames[i], buttons[i].x, buttons[i].y + 40 + gp.getScaledPxlTile() * 2);
        }
    }

    public void characterSelectButtonAction(int i){
        gp.choosePlayer(i);
        gp.newMap();
        gp.playRandomMusic();
        gp.setGameState(gp.getPlayState());
    }

    public void setMenuScreen(){

        try{
            image = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Menu_Graphic.png")),
                    672, 192);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        buttonNames = new String[3];

        buttonNames[0] = "New Game";
        buttonNames[1] = "Continue";
        buttonNames[2] = "Exit";

        buttonColor = new Color[3];

        buttonColor[0] = WHITE;
        buttonColor[1] = WHITE;
        buttonColor[2] = WHITE;

        buttons = new Rectangle[3];

        g2.setFont(basicFont);

        int length = (int)g2.getFontMetrics().getStringBounds(buttonNames[0], g2).getWidth();
        int height = (int)g2.getFontMetrics().getStringBounds(buttonNames[0], g2).getHeight();
        buttons[0] = new Rectangle((gp.getGAME_WIDTH()/2) - (length/2), gp.getScaledTile() + 100, length, height);

        length = (int)g2.getFontMetrics().getStringBounds(buttonNames[1], g2).getWidth();
        buttons[1] = new Rectangle((gp.getGAME_WIDTH()/2) - (length/2), gp.getScaledTile() + 200, length, height);

        length = (int)g2.getFontMetrics().getStringBounds(buttonNames[2], g2).getWidth();
        buttons[2] = new Rectangle((gp.getGAME_WIDTH()/2) - (length/2), gp.getScaledTile() + 300, length, height);

        errorText = "No Saved Game Found";
    }

    public void drawMenuScreen(){

        g2.drawImage(image, (gp.getGAME_WIDTH() / 2) - (image.getWidth()/2), 50, null);

        for(int i = 0; i < 3; ++i){
            g2.setColor(buttonColor[i]);
            g2.drawString(buttonNames[i], buttons[i].x, buttons[i].y + 50);
        }

        if(loadError && frameRefresh < 100){
            g2.setColor(RED);

            g2.drawString(errorText, (gp.getGAME_WIDTH()/2) - ((int) g2.getFontMetrics().getStringBounds(errorText, g2).getWidth() / 2)
                    , gp.getGAME_HEIGHT() - 50);
            ++frameRefresh;
        }
    }

    public void menuButtonAction(int i){

        if(i == 0){
            gp.getGameMemory().newGame();
            gp.setNPCs();
            setMenuScreenBool(false);
            setCharacterSelectScreenBool(true);
        }
        else if(i == 1){
            gp.getGameMemory().loadGame();
        }
        else if(i == 2){
            System.exit(130);
        }
    }

    public void setStartScreen(){
        text = "Chaos at the Cafe";

        try{
            image = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Start Screen.png")),
                    gp.getGAME_WIDTH(), gp.getGAME_HEIGHT());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void drawStartScreen(){
        String textS = "Press 'ENTER'";

        g2.setColor(WHITE);

        g2.setFont(basicFont.deriveFont(basicFont.getStyle(), 40F));
        int localY = gp.getGAME_HEIGHT() - (gp.getGAME_HEIGHT()/5);

        g2.drawImage(image, 0, 0, null);

        if(frameRefresh % 2 == 0 && globalY < 350){
            globalY += 4;
        }

        if(frameRefresh < 60 && globalY >= 350) {
            g2.setFont(basicFont.deriveFont(basicFont.getStyle(), 40F));
            g2.drawString(textS, (gp.getGAME_WIDTH()/2) - ((int)g2.getFontMetrics().getStringBounds(textS, g2).getWidth()/2), localY);
        }
        else if(frameRefresh > 100){
            frameRefresh = 0;
        }

        g2.setFont(scriptFont);

        int lengthSubTitle = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int heightSubTitle = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        int xST = (gp.getGAME_WIDTH()/2) - (lengthSubTitle/2);

        g2.setColor(BLACK);
        g2.fillRect(xST - 20, globalY - 50, lengthSubTitle + 35, heightSubTitle + 15);

        g2.setColor(WHITE);
        g2.drawString(text, xST, globalY);


        ++frameRefresh;
    }

    public void setPauseScreen(){

        try{
            image = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Pause_Graphic.png"))
                    , 672, 192);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        buttonColor = new Color[3];

        buttonColor[0] = WHITE;
        buttonColor[1] = WHITE;
        buttonColor[2] = WHITE;


        buttonNames = new String[3];
        buttonNames[0] = "Main Menu";
        buttonNames[1] = "Save Game";
        buttonNames[2] = "Exit";

        buttons = new Rectangle[3];
        int length = (int)g2.getFontMetrics().getStringBounds(buttonNames[0], g2).getWidth();
        int height = (int)g2.getFontMetrics().getStringBounds(buttonNames[0], g2).getHeight();
        buttons[0] = new Rectangle((gp.getGAME_WIDTH()/2) - (length/2), gp.getScaledTile() + 100, length, height);

        length = (int)g2.getFontMetrics().getStringBounds(buttonNames[1], g2).getWidth();
        buttons[1] = new Rectangle((gp.getGAME_WIDTH()/2) - (length/2), gp.getScaledTile() + 200, length, height);

        length = (int)g2.getFontMetrics().getStringBounds(buttonNames[2], g2).getWidth();
        buttons[2] = new Rectangle((gp.getGAME_WIDTH()/2) - (length/2), gp.getScaledTile() + 300, length, height);

    }

    public void pauseButtonAction(int i){
        if(i == 0){
            gp.setGameState(gp.getStartState());
            setMenuScreenBool(true);
        }
        else if(i == 1){
            gp.getGameMemory().saveGame();
            gameSaved = true;
        }
        else if(i == 2){
            System.exit(130);
        }
    }

    public void drawPauseScreen(){

        g2.setColor(BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .80F));
        g2.fillRect(0, 0, gp.getGAME_WIDTH(), gp.getGAME_HEIGHT());

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        g2.drawImage(image, (gp.getGAME_WIDTH()/2) - (image.getWidth()/2), 50, null);

        if(gameSaved){
            buttonColor[1] = GRAY;
        }

        for(int i = 0; i < 3; ++i){
            g2.setColor(buttonColor[i]);
            g2.drawString(buttonNames[i], buttons[i].x, buttons[i].y + 50);
        }

        if(gameSaved){
            g2.setColor(GRAY);
            g2.drawString("Game Saved!", 200, 50);
        }


    }

    public void setCredits(){

        try{
            image = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Start Screen.png")),
                    gp.getGAME_WIDTH(), gp.getGAME_HEIGHT());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        credits = new String[49];

        credits[0] = "Penny Path: Chaos at the Cafe";
        credits[1] = "";
        credits[2] = "Created by: G***** Bishop";
        credits[3] = "";
        credits[4] = "";
        credits[5] = "With Special Thanks to the Spring Crew of '22 ";
        credits[6] = "";
        credits[7] = "";
        credits[8] = "Annie ******";
        credits[9] = "Adriene ******";
        credits[10] = "Brad ******";
        credits[11] = "Caleb ******";
        credits[12] = "Carina ******";
        credits[13] = "Carlos ******";
        credits[14] = "Carson ******";
        credits[15] = "Cate ******";
        credits[16] = "Collin ******";
        credits[17] = "Connor ******";
        credits[18] = "Emelia ******";
        credits[19] = "Grant ******";
        credits[20] = "Hadassah ******";
        credits[21] = "Izzy ******";
        credits[22] = "Jana ******";
        credits[23] = "Miro ******";
        credits[24] = "Julia ******";
        credits[25] = "Reina ******";
        credits[26] = "Karae ******";
        credits[27] = "Lena ******";
        credits[28] = "Lexi ******";
        credits[29] = "Mark ******";
        credits[30] = "Megan ******";
        credits[31] = "Olivia ******";
        credits[32] = "Sarah ******";
        credits[33] = "Sasha ******";
        credits[34] = "Stephen ******";
        credits[35] = "Turner ******";
        credits[36] = "Walter ******";
        credits[37] = "Elise ******";
        credits[38] = "";
        credits[39] = "Music: ";
        credits[40] = "Dog - C418";
        credits[41] = "Barefoot in the Park - Shiro Sagisu";
        credits[42] = "Staff Roll - Koji Kondo";
        credits[43] = "World Map 3 - Koji Kondo";
        credits[44] = "Inside a House - Koji Kondo";
        credits[45] = "Kokiri Forest - Koji Kondo";
        credits[46] = "Can You Really Call This A Hotel I Didn't";
        credits[47] = "Receive A Mint On My Pillow - Toby Fox";
        credits[48] = "At Doom's Gate - Mick Gordon";

        creditYLocation = 0;
        frameRefresh = 0;
    }

    public void drawCredits(){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .80F));
        g2.drawImage(image, 0, 0, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g2.setFont(basicFont.deriveFont(basicFont.getStyle(), 30F));
        g2.setColor(WHITE);

        for(int i = 0; i < credits.length; ++i){
            int x = (gp.getGAME_WIDTH()/2) - ((int)g2.getFontMetrics().getStringBounds(credits[i], g2).getWidth() / 2);
            int y = (gp.getGAME_HEIGHT() + (i * 50)) - creditYLocation;

            if(y < gp.getGAME_HEIGHT() + 100 && y > -50){
                g2.drawString(credits[i], x, y);
            }
            if(frameRefresh > 4){
                creditYLocation += 4;
                frameRefresh = 0;
            }
            if(i == 48 && y <= -50){
                setMenuScreen();
                menuScreenOn = true;
                gp.setGameState(gp.getStartState());
            }
        }

        ++frameRefresh;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(basicFont);
        g2.setColor(WHITE);

        if(gp.getGameState() == gp.getPlayState()){

        }
        else if(gp.getGameState() == gp.getPauseState()){
            drawPauseScreen();
        }
        else if(gp.getGameState() == gp.getStartState() && startScreenOn){
            drawStartScreen();
        }
        else if(gp.getGameState() == gp.getStartState() && menuScreenOn){
            drawMenuScreen();
        }
        else if(gp.getGameState() == gp.getStartState() && characterSelectScreenOn){
            drawCharacterSelect();
        }

        if(gp.getGameState() == gp.getCreditsState()){
            drawCredits();
        }
    }

    public void setStartScreenBool(boolean bool) {
        startScreenOn = bool;
        if(startScreenOn) {
            setStartScreen();
            setMenuScreenBool(false);
            setCharacterSelectScreenBool(false);
        }
    }

    public void setMenuScreenBool(boolean bool) {
        menuScreenOn = bool;
        if(menuScreenOn) {
            setMenuScreen();
            setStartScreenBool(false);
            setCharacterSelectScreenBool(false);
        }
    }

    public void setCharacterSelectScreenBool(boolean bool) {
        characterSelectScreenOn = bool;
        if(characterSelectScreenOn) {
            setCharacterSelect();
            setMenuScreenBool(false);
            setStartScreenBool(false);
        }
    }

    public Rectangle[] getButtons() {
        return buttons;
    }

    public boolean isStartScreenOn() {
        return startScreenOn;
    }

    public boolean isMenuScreenOn() {
        return menuScreenOn;
    }

    public boolean isCharacterSelectScreenOn() {
        return characterSelectScreenOn;
    }

    public boolean isGameSaved() {
        return gameSaved;
    }

    public void loadError(){
        loadError = true;
        frameRefresh = 0;
    }

    public void resetGameSaved() {
        gameSaved = false;
    }
}
