package Objects;

import main.GamePanel;
import main.UtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Coin extends Object{

    boolean head;

    int worldX;
    int worldY;
    int screenX;
    int screenY;

    final int hitBoxX = 5;
    final int hitBoxY = 20;
    final int hitBoxWidth = 38;
    final int hitBoxHeight = 28;
    int coinFrameCount = 0;
    int i = 0;

    ArrayList<BufferedImage> images;
    BufferedImage currentImage;

    public Coin(GamePanel gp, int screenX, int screenY){
        super();

        this.gp = gp;
        this.screenX = screenX;
        this.screenY = screenY;

        hitBox = new Rectangle();
        hitBox.setRect(screenX + hitBoxX, screenY + hitBoxY, hitBoxWidth, hitBoxHeight);
        collected = false;
        head = true;
        getImages();

    }


    public void getImages(){

        UtilityTools uTools = new UtilityTools();

        try {
            Path dir = Paths.get("src/res/Objects/Coin");
            imageFolder = Files.newDirectoryStream(dir);

            images = new ArrayList<>();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        for(Path tile: imageFolder){

            try {
                images.add(uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Objects/Coin/" + String.valueOf(tile.getFileName()))), 48, 48));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        try {
            imageFolder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkCollision(){

        if (gp.getPlayer().getCollisionBox().intersects(hitBox)) {

            collected = true;
            gp.playSFX(1);
            gp.getPlayer().collectCoin();
        }
    }

    @Override
    public void draw(Graphics2D g2){

        if(!collected){

                if (i < images.size() && head) {
                    if(coinFrameCount > 5) {

                        currentImage = images.get(i);

                        coinFrameCount = 0;
                        ++i;
                    }else {
                        ++coinFrameCount;
                    }

                    if( i == images.size()){
                        head = false;
                        i -= 2;
                    }
                }
                else if(i > 0 && !head){

                    if(coinFrameCount > 5) {
                        currentImage = images.get(i);

                        coinFrameCount = 0;
                        --i;
                    }else{

                        ++coinFrameCount;
                    }

                    if(i == 0){
                        head = true;
                    }
                }

                g2.drawImage(currentImage, screenX, screenY, null);
        }

    }


    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

}
