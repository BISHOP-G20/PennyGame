package Entity;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NPC{

    private GamePanel gp;
    private Entity entity;
    private UtilityTools uTools = new UtilityTools();

    private String characterName;
    private String direction;

    private ArrayList<String> dialogue;
    private int dialogueText;

    private Rectangle collisionBox;
    private Font dialogueFont;

    BufferedImage[] dialogueIndicator;
    BufferedImage dialogueFrame;
    BufferedImage dialogueBG;

    String line;
    String newLine;
    int letterCount;
    int secondLetCount;

    private int indicatorState;
    private int indicatorNull = -1;
    private int indicatorNeutral = 0;
    private int indicatorHover = 1;
    private int indicatorClicked = 2;
    private int frameRefresh = 0;

    int worldX;
    int worldY;

    int screenX;
    int screenY;

    public NPC(Entity entity, GamePanel gp){
        super();

        this.gp = gp;
        this.entity = entity;

        indicatorState = indicatorNull;

        dialogueText = 0;
        letterCount = 1;
        line = "";
        newLine = "";
        dialogueFont = new Font("Arial", Font.PLAIN, 30);
        dialogueIndicator = new BufferedImage[2];

        characterName = entity.getCharacterName();
        direction = entity.getDirection();

        collisionBox = new Rectangle(entity.getWorldX(), entity.getWorldY(), 48, 48);

        getImages();
    }

    public void getImages(){
        try{
            dialogueIndicator[0] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Indicator.png")),
                    24, 48);

            dialogueIndicator[1] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Indicator_hover.png")),
                    24, 48);

            dialogueBG = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/D_Frame_Background.png")),
                    672, 192);

            dialogueFrame = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/D_Frame.png")),
                    672, 192);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(int worldX, int worldY, String direction){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        entity.setDialogue();
    }

    public void update(){

        screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        collisionBox.setRect(screenX, screenY, 48, 48);

    }

    public void drawDialogue(Graphics2D g2){


        String text = "";
        int x = gp.getScaledTile() - 50;
        int y = gp.getGAME_HEIGHT() - 150;

        if(dialogueText < entity.getDialogue().size()) {
            text = entity.getDialogue().get(dialogueText);
        }else{
            gp.setGameState(gp.getPlayState());
            entity.nextDialogueSet();
            entity.setDialogue();
            indicatorState = indicatorNeutral;
            dialogueText = 0;
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .80F));
        g2.drawImage(dialogueBG, (gp.getGAME_WIDTH()/2) - (dialogueBG.getWidth()/2), gp.getGAME_HEIGHT() - 200, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g2.drawImage(dialogueFrame, (gp.getGAME_WIDTH()/2) - (dialogueBG.getWidth()/2), gp.getGAME_HEIGHT() - 200, null);

        g2.setColor(Color.WHITE);
        g2.setFont(dialogueFont);

        if(!line.contains("\n") && frameRefresh > 5 && text.length() > line.length()) {
            line = text.substring(0,letterCount);

            ++letterCount;
            frameRefresh = 0;
            secondLetCount = letterCount;
            if(line.length() % 2 == 0) {
                gp.playSFX(3);
            }
        }else if(line.contains("\n") && frameRefresh > 5 && text.length() - 1 > (line.length() + newLine.length())){
            newLine = text.substring(letterCount, secondLetCount);
            if(newLine.length() % 2 == 0) {
                gp.playSFX(3);
            }
            secondLetCount++;
            frameRefresh = 0;
        }

        g2.drawString(line, x, y);
        g2.drawString(newLine, x, y + 40);

        ++frameRefresh;
    }

    public void draw(Graphics2D g2){

        if(gp.getGameState() == gp.getPlayState()) {
            update();
        }

        if(gp.getGameState() != gp.getStartState() && gp.getGameState() != gp.getBattleState()) {
            g2.drawImage(entity.getCharacterImage(), screenX, screenY, null);

            if (indicatorState == indicatorNeutral) {
                g2.drawImage(dialogueIndicator[0], screenX + 16, screenY - 55, null);
            } else if (indicatorState == indicatorHover) {
                g2.drawImage(dialogueIndicator[1], screenX + 16, screenY - 55, null);
            } else if (indicatorState == indicatorClicked) {
                drawDialogue(g2);
            }
        }
    }

    public void setIndicatorState(int state){
        indicatorState = state;
    }

    public void nextDialogue(){
        ++dialogueText;
        line = "";
        newLine = "";
        letterCount = 1;
        secondLetCount = 0;
    }

    public Entity getEntity(){
        return entity;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public String getDirection(){
        return direction;
    }

    public String getCharacterName(){
        return characterName;
    }

    public int getIndicatorState() {
        return indicatorState;
    }

    public int getIndicatorNeutral() {
        return indicatorNeutral;
    }

    public int getIndicatorHover() {
        return indicatorHover;
    }

    public int getIndicatorClicked() {
        return indicatorClicked;
    }

    public int getIndicatorNull() {
        return indicatorNull;
    }
}
