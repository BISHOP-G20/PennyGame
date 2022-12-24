package Envirionment;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImageTile {

    BufferedImage image;
    Path path;
    UtilityTools uTools;

    private CollisionTile[][] collTiles = new CollisionTile[4][4];
    private int worldX;
    private int worldY;

    ImageTile(Path path, int[] collTileNums, String mapName, GamePanel gp){

        uTools = new UtilityTools();
        try{

            image = ImageIO.read(getClass().getResourceAsStream("/res/Maps/" + mapName + "/" + path));
            image = uTools.scaleImage(image, gp.getScaledTile(), gp.getScaledTile());

        }
        catch(IOException e){
            e.printStackTrace();
        }

        int col = 0;
        int row = 0;

        for(int boolNum: collTileNums){

            collTiles[col][row] = new CollisionTile(0, 0, boolNum);

            if(col < 3){
                ++col;
            }
            else{
                col = 0;
                ++row;
            }
        }
    }

    public void setWorldXY(int worldX, int worldY){
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public BufferedImage getImage(){
        return image;
    }

    public CollisionTile[][] getCollTiles(){
        return collTiles;
    }

    public CollisionTile getCollTile(int col, int row){
        return collTiles[col][row];
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void printPath(){
        System.out.println(path);
    }
}
