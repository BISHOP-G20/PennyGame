package Entity;

import Projectiles.Projectile;
import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

abstract public class BossEntity {

    GamePanel gp;
    UtilityTools uTools = new UtilityTools();
    DirectoryStream<Path> actionFolder;

    String bossName;
    String folderPath;
    String mainImageName;
    BufferedImage mainImage;
    BufferedImage currentImage;
    BufferedImage projectileImage;


    Rectangle hitBox;
    int hitBoxWidth;
    int hitBoxHeight;
    int hitBoxRelativeX;
    int hitBoxRelativeY;

    int attack;
    int health;
    int speed;
    int frameRefresh;
    int imageCount;

    int mainImageWidth;
    int mainImageHeight;
    int bossFloorY;

    int screenX;

    ArrayList<Projectile> projectiles;
    boolean vulnerable = true;

    BossEntity(GamePanel gp){

        this.gp = gp;
    }

    public void setMainImage(){
        try{
            mainImage = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(folderPath + mainImageName)),
                    mainImageWidth, mainImageHeight);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setImageList(String folderName, ArrayList<BufferedImage> arrayList){
        try {

            Path dir = Paths.get("src/" + folderPath + folderName);
            actionFolder = Files.newDirectoryStream(dir);

            for(Path path: actionFolder){
                String pName = String.valueOf(path).substring(3);
                pName = pName.replace('\\', '/');
                arrayList.add(ImageIO.read(getClass().getResourceAsStream(pName)));
            }
            actionFolder.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    abstract public void arrive(Graphics2D g2);

    public void setHitBox(int x, int y, int width, int height){
        hitBox = new Rectangle(x, y, width, height);
    }

    public void resetHealth(){
        health = 200;
    }

    abstract public void fightAI(Graphics2D g2, Double time);

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public int getAttack() {
        return attack;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }
}
