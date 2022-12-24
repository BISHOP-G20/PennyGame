package main;

import Entity.BossEntity;
import Entity.DrainBoss;
import Entity.Player;
import Projectiles.Projectile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;


public class BossBattle {

    Player player;
    BossEntity boss;
    GamePanel gp;
    UtilityTools uTools = new UtilityTools();

    BufferedImage stage;
    BufferedImage[] winnerGraphics;
    BufferedImage currentGraphic;

    double lastTime;
    double currentTime;
    double totalTime;

    double newInterval;
    private boolean battleStart;


    BossBattle(Player player, GamePanel gp){

        this.player = player;
        this.gp = gp;

        stage = null;
        totalTime = 0;
        newInterval = 0;
        battleStart = false;

        winnerGraphics = new BufferedImage[2];

        try{
            winnerGraphics[0] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Winner_1.png")),
                    672, 192);

            winnerGraphics[1] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Winner_2.png")),
                    672, 192);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        currentGraphic = winnerGraphics[0];

        setStage();
    }

    public void setStage() {

        try{
        if(gp.progTracker.getProgress() == 2){
            stage = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Maps/Boss Stage/Sink.png")),
                    1056, 768);
            boss = new DrainBoss(gp);
        }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        gp.stopMusic();
        gp.playMusic(7);
    }

    public void collisionCheck() {

        for (Iterator<Projectile> itr = player.getProjectiles().iterator(); itr.hasNext(); ) {
            Projectile projectile = itr.next();

            if (projectile.getScreenX() < (0 - projectile.getWidth()) || projectile.getScreenX() > (gp.getGAME_WIDTH() + projectile.getWidth())
                    || projectile.getScreenY() < 0 - projectile.getHeight() || projectile.getScreenY() > (gp.getGAME_HEIGHT() + projectile.getHeight())) {
                itr.remove();
            } else if (projectile.getHitBox().intersects(boss.getHitBox()) && boss.isVulnerable()) {
                boss.takeDamage(projectile.getDamage());
                itr.remove();
            } else {
                projectile.move();
            }
        }

        if (boss instanceof DrainBoss) {
            if (((DrainBoss) boss).getAttackBox() != null) {
                if (((DrainBoss) boss).getAttackBox().intersects(player.getCollisionBox())) {
                    player.takeDamage(boss.getAttack());
                }
            }
        }

        for(Iterator<Projectile> itr = boss.getProjectiles().iterator(); itr.hasNext();){
            Projectile projectile = itr.next();

            if(projectile.getScreenY() > gp.getGAME_HEIGHT() || projectile.getScreenX() > gp.getGAME_WIDTH()){
                itr.remove();
            }
            else if(projectile.getHitBox().intersects(player.getCollisionBox())){
                player.takeDamage(projectile.getDamage());
                itr.remove();
            }
            else{
                projectile.move();
            }
        }
    }

    public void drawHealthBars(Graphics2D g2){
        g2.setColor(Color.BLACK);

        //Player health bar background
        g2.fillRect(50, 100, 20, 160);

        //Boss health bar background
        g2.fillRect(200, 45, 410, 30);

        g2.setColor(Color.RED);

        //Player health
        g2.fillRect(55, 105, 10, player.getHealth() * 3);

        //Boss health
        g2.fillRect(205, 50, boss.getHealth() * 2, 20);
    }

    public void draw(Graphics2D g2){
        currentTime = System.nanoTime();

        if (lastTime != 0) {
            totalTime += (currentTime - lastTime) / 1000000000;
        }

        if(totalTime < 6){

            int countDown = 5;
            int elapsedTime = 0;
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 80));

            if((int) totalTime != 0){
                elapsedTime = (int) totalTime;
            }
            g2.drawImage(stage, -96, -96, null);
            boss.arrive(g2);
            g2.drawString((String.valueOf(countDown - elapsedTime)), (gp.getGAME_WIDTH()/2) - 10, gp.getGAME_HEIGHT()/2);
        }
        else if(totalTime > 6 && !battleStart){
            battleStart = true;
        }

        if(boss.getHealth() > 0 && player.getHealth() > 0 && battleStart) {
            collisionCheck();
            player.move();

            g2.drawImage(stage, -96, -96, null);

            boss.fightAI(g2, totalTime);

            for (Projectile projectile : player.getProjectiles()) {
                projectile.draw(g2);
            }

            drawHealthBars(g2);
        }
        else if(battleStart){
            if(boss.getHealth() <= 0) {
                g2.drawImage(stage, -96, -96, null);

                if((int) newInterval % 2 == 0) {
                    currentGraphic = winnerGraphics[0];
                }
                else{
                    currentGraphic = winnerGraphics[1];
                }

                g2.drawImage(currentGraphic, (gp.getGAME_WIDTH() / 2) - (winnerGraphics[0].getWidth() / 2), 50, null);

                newInterval += (currentTime - lastTime) / 1000000000;

                if (newInterval > 6) {
                    gp.progTracker.setProgress(3);
                    player.setMapDefault((gp.getScaledTile() * 2) + (gp.getScaledTile() / 2),
                            gp.getScaledTile() + (gp.getScaledTile() / 2), "left");
                    gp.getTileManager().setMap("BOH");
                    gp.setGameState(gp.getPlayState());
                    player.resetHealth();
                    boss.resetHealth();
                    gp.stopMusic();
                    gp.playRandomMusic();
                }
            }
            else{
                g2.drawImage(stage, -96, -96, null);

                newInterval += (currentTime - lastTime) / 1000000000;

                if (newInterval > 2) {
                    player.setMapDefault((gp.getScaledTile() * 2) + (gp.getScaledTile() / 2),
                            gp.getScaledTile() + (gp.getScaledTile() / 2), "left");
                    gp.getTileManager().setMap("BOH");
                    gp.setGameState(gp.getPlayState());
                    player.resetHealth();
                    boss.resetHealth();
                    gp.stopMusic();
                    gp.playRandomMusic();
                }
            }
            battleStart = false;
        }

        lastTime = currentTime;
    }

}
