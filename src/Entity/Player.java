package Entity;

import Projectiles.Fist;
import Projectiles.Projectile;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player {

    private int coins;
    private KeyHandler keyH;
    private Entity entity;
    private GamePanel gp;
    UtilityTools uTools = new UtilityTools();

    BufferedImage upStand;
    BufferedImage up1;
    BufferedImage up2;
    BufferedImage downStand;
    BufferedImage down1;
    BufferedImage down2;
    BufferedImage rightStand;
    BufferedImage right1;
    BufferedImage right2;
    BufferedImage leftStand;
    BufferedImage left1;
    BufferedImage left2;

    String u1, u2, d1, d2, r1, r2, l1, l2;

    int worldX;
    int worldY;

    private int screenX;
    private int screenY;
    private final int collisionBoxX = 15;
    private final int collisionBoxY = 30;

    private int health;
    private int speed;
    private int xVel;
    private int yVel;
    private int gravityAccl;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private int rlLoopCount = 0;
    private int jFrameCount;

    private int mouseX;
    private int mouseY;

    private String direction;
    private String playerName;
    private ArrayList<Projectile> projectiles;
    private boolean mousePressHeld;
    private double currentTime;
    private double lastTime;
    private double elapsedTime;


    private boolean collisionOn = false;
    private Rectangle collisionBox = new Rectangle();

    public Player(KeyHandler keyH, Entity entity, GamePanel gp){
        super();

        this.keyH = keyH;
        this.entity = entity;
        this.gp = gp;

        playerName = entity.getCharacterName();
        coins = 0;
        jFrameCount = 0;

        setDefaultValues();
        getPlayerImage();
    }

    public void newProjectile(){

        currentTime = System.nanoTime();

        if(lastTime != 0) {
            elapsedTime = (currentTime - lastTime)/1000000000;
        }

        if(elapsedTime > .3 || lastTime == 0) {
            projectiles.add(new Fist(mouseX + gp.getScaledPxlTile() / 2, mouseY));
            projectiles.get(projectiles.size() - 1).setProjectile(screenX + gp.getScaledPxlTile() / 2, screenY);
            gp.playSFX(2);
            lastTime = currentTime;
            elapsedTime = 0;
        }
    }

    public void setDefaultValues(){
        worldX = 96;
        worldY = 192 * 3 + 96;
        health = 50;
        speed = 5;
        screenX = (gp.getGAME_WIDTH() / 2) - (gp.getScaledPxlTile()/2);
        screenY = (gp.getGAME_HEIGHT() / 2) - (gp.getScaledPxlTile() / 2);
        direction = "down";
    }

    public void setBattleValues(){
        projectiles = new ArrayList<>();

        screenY = gp.getGAME_HEIGHT() - 84;
        screenX = (gp.getGAME_WIDTH() / 2) - (gp.getScaledPxlTile()/2);
        xVel = 0;
        yVel = 0;
        gravityAccl = 1;
        direction = "down";
        mousePressHeld = false;
        lastTime = 0;
        elapsedTime = 0;
    }

    public void setMapDefault(int worldX, int worldY, String direction){

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;

        screenX = (gp.getGAME_WIDTH() / 2) - (gp.getScaledPxlTile()/2);
        screenY = (gp.getGAME_HEIGHT() / 2) - (gp.getScaledPxlTile() / 2);
    }

    public void setMousePressHeld(boolean isHeld){
        mousePressHeld = isHeld;
        elapsedTime = 0;
    }

    public void setMouse(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void getPlayerImage(){

        try{

            d1 = "/res/Players/" + playerName + "/" + playerName + "_down_1.png.png";
            d2 = "/res/Players/" + playerName + "/" + playerName + "_down_2.png.png";

            u1 = "/res/Players/" + playerName + "/" + playerName + "_up_1.png.png";
            u2 = "/res/Players/" + playerName + "/" + playerName + "_up_2.png.png";

            r1 = "/res/Players/" + playerName + "/" + playerName + "_right_1.png.png";
            r2 = "/res/Players/" + playerName + "/" + playerName + "_right_2.png.png";

            l1 = "/res/Players/" + playerName + "/" + playerName + "_left_1.png.png";
            l2 = "/res/Players/" + playerName + "/" + playerName + "_left_2.png.png";

            up1 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(u1)), 58, 58);
            up2 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(u2)), 58, 58);
            upStand = entity.getCharacterImages()[0];
            down1 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(d1)), 58, 58);
            down2 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(d2)), 58, 58);
            downStand = entity.getCharacterImages()[1];
            left1 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(l1)), 58, 58);
            left2 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(l2)), 58, 58);
            leftStand = entity.getCharacterImages()[2];
            right1 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(r1)), 58, 58);
            right2 = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(r2)), 58, 58);
            rightStand = entity.getCharacterImages()[3];

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void collectCoin(){
        ++coins;
    }

    public void jumpHandler(){
        if(screenY < gp.getGAME_HEIGHT() - 104 && jFrameCount > 5){
            gravityAccl += 2;
            yVel += gravityAccl;
            jFrameCount = 0;
        }
        else if(screenY > gp.getGAME_HEIGHT() - 104){
            gravityAccl = 0;
            yVel = 0;
            screenY = gp.getGAME_HEIGHT() - 104;
        }
        ++jFrameCount;
    }

    public void move(){

        if(gp.getGameState() != gp.getBattleState()) {

            if (keyH.down || keyH.up || keyH.right || keyH.left) {

                if (keyH.up) {
                    worldY -= speed;
                    direction = "up";
                } else if (keyH.down) {
                    worldY += speed;
                    direction = "down";
                } else if (keyH.right) {
                    worldX += speed;
                    direction = "right";
                } else if (keyH.left) {
                    worldX -= speed;
                    direction = "left";
                }

                collisionOn = false;
                gp.getCollisionManager().checkCollision();

            /*
            if(gp.getTileManager().getMap() instanceof Office){
                gp.getCollisionManager().checkCollision2();
            }else {
                gp.getCollisionManager().checkCollision();
            }

             */

                //up and down walk cycle
                if (spriteCounter > 7 && (direction == "up" || direction == "down")) {

                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    } else {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;

                    ++rlLoopCount;

                    //maintains SFX foot fall pace for  walk loop
                    if (rlLoopCount > 2) {
                        gp.playSFX(0);

                        rlLoopCount = 0;
                    }
                }

                //right and left walk cycle
                else if (spriteCounter > 5 && (direction.equals("right") || direction.equals("left"))) {
                    if (spriteNum == 2) {
                        spriteNum = 3;
                    } else if (spriteNum == 3) {
                        spriteNum = 1;
                    } else if (spriteNum == 1) {
                        spriteNum = 2;
                    }
                    spriteCounter = 0;

                    ++rlLoopCount;

                    //maintains SFX foot fall pace for walk loop
                    if (rlLoopCount > 3) {
                        gp.playSFX(0);

                        rlLoopCount = 0;
                    }
                }

                ++spriteCounter;
            } else {
                spriteNum = 3;
            }
            collisionBox.setRect(worldX + collisionBoxX, worldY + collisionBoxY, 28, 28);
        }
        else {

            if (keyH.right) {
                xVel = speed;
                direction = "right";
            }
            if (keyH.left) {
                xVel = -speed;
                direction = "left";
            }
            if (keyH.space && yVel == 0) {
                yVel = -15;
            }
            jumpHandler();

            if(!keyH.left && !keyH.right && screenY == gp.getGAME_HEIGHT() - 104){
                xVel = 0;
            }

            if(keyH.left || keyH.right) {
                if (spriteCounter > 5 && (direction.equals("right") || direction.equals("left"))) {
                    if (spriteNum == 2) {
                        spriteNum = 3;
                    } else if (spriteNum == 3) {
                        spriteNum = 1;
                    } else if (spriteNum == 1) {
                        spriteNum = 2;
                    }
                    spriteCounter = 0;

                    ++rlLoopCount;
                }
                ++spriteCounter;
            }
            else{
                spriteNum = 3;
            }
            gp.getCollisionManager().checkCollision();

            screenX += xVel;
            screenY += yVel;

            collisionBox.setRect(screenX + collisionBoxX, screenY + collisionBoxY, 28, 28);

            if(mousePressHeld){
                newProjectile();
            }
        }
    }

    public void draw(Graphics g2){

        BufferedImage image = null;

        switch(direction){

            case "down":

                if(spriteNum == 1) {
                    image = down1;
                }
                else if(spriteNum == 2){
                    image = down2;
                }
                else if(spriteNum == 3){
                    image = downStand;
                }
                break;

            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                else if(spriteNum == 2){
                    image = up2;
                }
                else if(spriteNum == 3){
                    image = upStand;
                }
                break;

            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                else if(spriteNum == 2){
                    image = right2;
                }
                else if(spriteNum == 3){
                    image = rightStand;
                }
                break;

            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                else if(spriteNum == 2){
                    image = left2;
                }
                else if(spriteNum == 3){
                    image = leftStand;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }

    public void resetHealth(){
        health = 50;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getPlayerLeft(){
        return worldX + collisionBoxX;
    }

    public int getPlayerRight(){
        return worldX + collisionBoxX + collisionBox.width;
    }

    public int getPlayerTop(){
        return worldY + collisionBoxY;
    }

    public int getPlayerBottom(){
        return worldY + collisionBoxY + collisionBox.height;
    }

    public String getDirection(){
        return direction;
    }

    public int getSpeed(){
        return speed;
    }

    public int getScreenX(){
        return screenX;
    }

    public int getScreenY(){
        return screenY;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY(){
        return worldY;
    }

    public Rectangle getCollisionBox(){
        return collisionBox;
    }

    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }

    public void changeWorldY(int speed){
        worldY += speed;
    }

    public void changeWorldX(int speed){
        worldX += speed;
    }

    public void changexVel(int xVel){
        this.xVel = xVel;
    }

    public int getCoins() {
        return coins;
    }

    public int getHealth() {
        return health;
    }

    public boolean isMousePressHeld() {
        return mousePressHeld;
    }

    public void takeDamage(int damage){
        health -= damage;
    }
}
