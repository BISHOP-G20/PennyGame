package Envirionment;

import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CollisionMap {

    GamePanel gp;

    InputStream inputStream;
    BufferedReader bufRead;

    ArrayList<Rectangle> collisionBoxes;
    Rectangle teleportBox;

    int collColTiles;
    int collRowTiles;
    int[][] collTileNum;

    int playerScreenX;
    int playerScreenY;

    CollisionMap(){

    }

    public void loadMapTiles(){

        bufRead = new BufferedReader(new InputStreamReader(inputStream));

        collTileNum = new int[collColTiles][collRowTiles];

        int col = 0;
        int row = 0;

        try {

            while (col < collColTiles && row < collRowTiles) {

                String collLine = bufRead.readLine();

                while (col < collColTiles) {

                    String[] nums = collLine.split(" ");

                    int num = Integer.parseInt(nums[col]);

                    collTileNum[col][row] = num;

                    ++col;
                }
                if(col == collColTiles){
                    col = 0;
                    ++row;
                }
            }
            bufRead.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public int getCollTileNum(int col, int row) {
        return collTileNum[col][row];
    }

    public ArrayList<Rectangle> getCollisionBoxes(){
        return collisionBoxes;
    }

    public Rectangle getTeleportBox(){
        return teleportBox;
    }

    public void shiftXY(int playerWorldX, int playerWorldY){
        for(Rectangle collisionBox: collisionBoxes){

            int worldX = collisionBox.x - playerWorldX + playerScreenX;
            int worldY = collisionBox.y - playerWorldY + playerScreenY;

            System.out.println("X: " + worldX + " Y: " + worldY);

            collisionBox.x = worldX;
            collisionBox.y = worldY;
        }
    }
}
