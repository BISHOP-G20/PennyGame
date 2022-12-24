package Entity;

import Projectiles.DrainDroplet;
import Projectiles.Projectile;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrainBoss extends BossEntity{

    private double lastAttackTime;

    private ArrayList<BufferedImage> leftMove, rightMove, arrive, fBend, geyser, wFall;
    private boolean leftBool, rightBool, fBendBool, geyserBool, wFallBool;

    private Rectangle wFallHitBox;
    private BufferedImage[] geysers;

    private int imageX;
    private int imageY;

    private int pImageX;
    private int pImageY;

    private int aLoopCount;
    private int wFallCount;

    public DrainBoss(GamePanel gp){
        super(gp);

        bossName = "Drain Boss";
        folderPath = "/res/Bosses/Drain";
        mainImageName = "/Drain_Boss.png";

        leftBool = false;
        rightBool = false;
        fBendBool = false;
        geyserBool = false;

        attack = 1;
        health = 200;
        speed = 2;
        frameRefresh = 0;
        imageCount = 0;

        mainImageWidth = 300;
        mainImageHeight = 450;
        bossFloorY = gp.getGAME_HEIGHT() - 60;
        screenX = (gp.getGAME_WIDTH() / 2) - (mainImageWidth / 2);

        hitBoxRelativeX = 108;
        hitBoxRelativeY = 48;
        hitBoxWidth = 90;
        hitBoxHeight = 84;

        imageX = screenX;
        imageY = bossFloorY - mainImageHeight;

        aLoopCount = 0;
        lastAttackTime = 0;
        wFallCount = 0;

        wFallHitBox = new Rectangle();

        geysers = new BufferedImage[2];
        projectiles = new ArrayList<>();

        setMainImage();
        setImageArrays();
        setHitBox(screenX + hitBoxRelativeX, imageY + hitBoxRelativeY, hitBoxWidth, hitBoxHeight);
    }

    public void setImageArrays(){

        String[] folderNames = new String[]{"/Lean_left", "/Lean_right", "/Arrive", "/FBend", "/Geyser", "/WFall"};

        leftMove = new ArrayList<>();
        rightMove = new ArrayList<>();
        arrive = new ArrayList<>();
        fBend = new ArrayList<>();
        geyser = new ArrayList<>();
        wFall = new ArrayList<>();

        ArrayList[] lists = {leftMove, rightMove, arrive, fBend, geyser, wFall};

        for(int i = 0; i < lists.length; ++i){
            setImageList(folderNames[i], lists[i]);
        }
    }

    @Override
    public void arrive(Graphics2D g2){
        ++frameRefresh;

        if(frameRefresh > 4 && imageCount < arrive.size()){
            currentImage = arrive.get(imageCount);

            ++imageCount;
            resetFrameRefresh();
        }
        else if(imageCount == arrive.size()){
            currentImage = mainImage;
        }

        imageX = screenX;
        imageY = bossFloorY - mainImageHeight;

        if(currentImage!= null) {
            g2.drawImage(currentImage, imageX, imageY, null);
        }
    }

    public void moveLeft(int x){
        ++frameRefresh;

        if(!leftBool){
            resetBooleans();
            resetImageCount();
        }

        if (imageCount == 0) {
            leftBool = true;
        }

        if (frameRefresh > 2 && imageCount < leftMove.size()) {
            currentImage = leftMove.get(imageCount);

            ++imageCount;
            resetFrameRefresh();
        } else if (imageCount == leftMove.size()) {
            currentImage = leftMove.get(leftMove.size() - 1);
            resetFrameRefresh();
        }


        imageX = x;
        imageY = bossFloorY - mainImageHeight;
    }

    public void moveRight(int x){
        ++frameRefresh;

        if(!rightBool){
            resetBooleans();
            resetImageCount();
        }

        if (imageCount == 0) {
            rightBool = true;
        }

            if (frameRefresh > 2 && imageCount < rightMove.size()) {
                currentImage = rightMove.get(imageCount);

                ++imageCount;
                resetFrameRefresh();
            } else if (imageCount == rightMove.size()) {
                currentImage = rightMove.get(rightMove.size() - 1);
                resetFrameRefresh();
            }

        imageX = x;
        imageY = bossFloorY - mainImageHeight;
    }

    public void waterFallAttack(int x, double time) {
        ++frameRefresh;

        if (!fBendBool) {
            resetImageCount();
        }

        if (imageCount == 0 && (2 < (time - lastAttackTime) || lastAttackTime == 0)) {
            fBendBool = true;
        }

        if (!wFallBool){
            if (frameRefresh > 2 && imageCount < fBend.size()) {
                currentImage = fBend.get(imageCount);

                ++imageCount;
                resetFrameRefresh();
            } else if (imageCount == fBend.size()) {

                currentImage = fBend.get(fBend.size() - 1);
                wFallBool = true;
                resetImageCount();
                resetFrameRefresh();
            }
        }
        else if(wFallBool && aLoopCount < 4){
            currentImage = fBend.get(fBend.size() - 1);
            if(aLoopCount > 0) {
                wFallHitBox.setRect(pImageX, pImageY, wFall.get(0).getWidth(), wFall.get(0).getHeight());
            }

            pImageX = imageX + 105;
            pImageY = imageY + 165;

            if(frameRefresh > 4 && imageCount < wFall.size()){
                projectileImage = wFall.get(imageCount);

                ++imageCount;
                resetFrameRefresh();
            }
            else if(imageCount == wFall.size()){
                resetImageCount();
                ++aLoopCount;
            }
            lastAttackTime = time;
        }
        else if(aLoopCount >= 4){
            resetBooleans();
            resetFrameRefresh();
            resetImageCount();
            aLoopCount = 0;
            ++wFallCount;
            projectileImage = null;
            wFallHitBox.setRect(0,0,0,0);
            lastAttackTime = time;
        }

        imageX = x;
        imageY = bossFloorY - mainImageHeight;
    }

    public void geyserAttack(Graphics2D g2, double time){
        ++frameRefresh;

        if(!geyserBool){
            resetImageCount();
        }

        if(imageCount == 0 && (2 < time - lastAttackTime || lastAttackTime == 0)){
            resetBooleans();
            geyserBool = true;
            vulnerable = false;
            currentImage = mainImage;
        }
        if(wFallCount >= 2){
            wFallCount = 0;
        }

        if(aLoopCount < 10 && geyserBool){
            if(frameRefresh > 4 && imageCount < geyser.size()){
                geysers[0] = geyser.get(imageCount);
                geysers[1] = geyser.get(imageCount);

                ++imageCount;
                resetFrameRefresh();
            }
            else if(imageCount == geyser.size() && aLoopCount < 10){
                for(int i = 0; i < 10; ++i){
                    projectiles.add(new DrainDroplet((i * 90) + (aLoopCount * 5) - 50, -48));
                }
                resetImageCount();
                ++aLoopCount;
            }

            if(geysers[0] != null && geysers[1] != null) {
                g2.drawImage(geysers[0], (gp.getGAME_WIDTH() / 4) - (geysers[0].getWidth()/2),
                        bossFloorY - geysers[0].getHeight(), null);

                g2.drawImage(geysers[1], ((gp.getGAME_WIDTH() / 4) * 3)  - (geysers[1].getWidth()/2),
                        bossFloorY - geysers[1].getHeight(), null);
            }
            lastAttackTime = time;
        }
        else if(aLoopCount == 10 && projectiles.size() == 0){
            resetBooleans();
            resetImageCount();
            resetFrameRefresh();
            aLoopCount = 0;
            vulnerable = true;
            lastAttackTime = time;
        }
    }

    public void fightAI(Graphics2D g2, Double time){

        int playerX = gp.getPlayer().getScreenX();

            if (playerX < (screenX + hitBoxRelativeX - 60) && !fBendBool && !geyserBool) {
                screenX -= speed;
                moveLeft(screenX);
            } else if (playerX > (screenX + hitBoxRelativeX + hitBoxWidth + 30) && !fBendBool && !geyserBool) {
                screenX += speed;
                moveRight(screenX);
            }
            else if(!fBendBool && !geyserBool){
                currentImage = mainImage;
                resetBooleans();
            }

            if ((geyserBool || playerX < screenX + 20 && playerX > screenX + mainImageWidth - 20 || health % 25 <= 3 || wFallCount >= 2)
                    && !wFallBool) {

                geyserAttack(g2, time);
            }else if ((playerX > screenX + 10 && playerX < screenX + mainImageWidth - 10 || fBendBool)
                    && !geyserBool && wFallCount <= 2) {
                waterFallAttack(screenX, time);
            }

        hitBox.setRect(imageX + hitBoxRelativeX, imageY + hitBoxRelativeY, hitBoxWidth, hitBoxHeight);

        g2.drawImage(currentImage, imageX, imageY, null);


        if(projectileImage != null){
            g2.drawImage(projectileImage, pImageX, pImageY, null);
        }

        for(Projectile droplet: projectiles){
            droplet.draw(g2);
        }
    }

    public void resetBooleans(){
        leftBool = false;
        rightBool = false;
        fBendBool = false;
        geyserBool = false;
        wFallBool = false;
    }

    public void resetFrameRefresh(){
        frameRefresh = 0;
    }

    public void resetImageCount(){
        imageCount = 0;
    }

    public Rectangle getAttackBox(){
        return wFallHitBox;
    }
}
