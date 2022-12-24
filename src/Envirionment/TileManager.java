package Envirionment;

import Entity.*;

import main.GamePanel;
import main.UtilityTools;;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;

public class TileManager {

    ArrayList<Path> tilePaths;
    ArrayList<ImageTile> mapTiles;
    int[][] mapTileLocation;

    //ONLY FOR TESTING
    BufferedImage tempBattleMap;
    UtilityTools utilityTools = new UtilityTools();
    //------------------------

    GamePanel gp;

    Player player;
    ArrayList<NPC> allNPCs;

    Map map;
    MainFloor mainFloor;
    Office office;
    BOH backOfHouse;
    CollisionMap collision;

    private int worldX;
    private int worldY;

    private int screenX;
    private int screenY;

    private int worldCol;
    private int worldRow;

    private int tileSize;

    public TileManager(Player player, GamePanel gp){

        this.gp = gp;
        this.player = player;

        allNPCs = new ArrayList<>();

        mainFloor = new MainFloor(gp);
        office = new Office(gp);
        backOfHouse = new BOH(gp);

        allNPCs = gp.getNPCs();

    }

    public void setMap(String mapName){

        switch(mapName){
            case "MainFloor":
                map = mainFloor;
                collision = new MainFloorCollision((MainFloor) map);
                map.setNPCs();

                break;

            case "Office":
                map = office;
                collision = new OfficeCollision((Office) map, gp);
                map.setNPCs();

                break;

            case "BOH":
                map = backOfHouse;
                collision = new BOHCollision((BOH) map);
                map.setNPCs();
                break;

        }

        tileSize = gp.getScaledTile();
        screenX = player.getScreenX();
        screenY = player.getScreenY();

        int tileNum = 0;
        int col = 0;
        int row = 0;

        int collCol;
        int collRow;

        collision.loadMapTiles();

        tilePaths = map.getTilePaths();
        worldRow = map.getWorldRow();
        worldCol = map.getWorldCol();

        mapTiles = new ArrayList<>();
        mapTileLocation = new int[worldCol][worldRow];

        for(Path path: tilePaths) {

            int[] collTileNums = new int[16];
            int collTileNum = 0;

            for(collRow = 0; collRow < 4; ++collRow){
                for(collCol = 0; collCol < 4; ++collCol){
                    collTileNums[collTileNum] = collision.getCollTileNum((col * 4) + collCol, (row * 4) + collRow);
                    ++collTileNum;
                }
            }

            mapTiles.add(new ImageTile(path, collTileNums, mapName, gp));
            mapTileLocation[col][row] = tileNum;
            mapTiles.get(tileNum).setWorldXY(col * gp.getScaledTile(), row * gp.getScaledTile());
            ++tileNum;

            if(col < (worldCol - 1)){
                ++col;
            }
            else {
                col = 0;
                ++row;
            }

        }
    }
    public void update(){

    }

    public void draw(Graphics2D g2){

        //MAKE DRAW LOCATION RELATIVE TO MAP AND PLAYER

        if(gp.getGameState() != gp.getBattleState()) {
            for (int row = 0; row < worldRow; ++row) {

                for (int col = 0; col < worldCol; ++col) {

                    int tile = mapTileLocation[col][row];
                    CollisionTile[][] collTiles = mapTiles.get(tile).getCollTiles();

                    worldX = player.getWorldX();
                    worldY = player.getWorldY();

                    int tileScreenX = (col * tileSize) - worldX + screenX;
                    int tileScreenY = (row * tileSize) - worldY + screenY;

                    for (int collRow = 0; collRow < 4; ++collRow) {
                        for (int collCol = 0; collCol < 4; ++collCol) {

                            collTiles[collCol][collRow].setXY(tileScreenX + (collCol * gp.getCOLLISION_TILE()),
                                    tileScreenY + (collRow * gp.getCOLLISION_TILE()));
                        }
                    }

                    if (tileScreenX > -192 && tileScreenX < gp.getGAME_WIDTH() + 96
                            && tileScreenY > -192 && tileScreenY < gp.getGAME_HEIGHT() + 96 && !(gp.getGameState() == gp.getStartState())) {

                        g2.drawImage(mapTiles.get(tile).getImage(), tileScreenX, tileScreenY, null);

                    }
                }
            }
            for (NPC npc : map.getNPCs()) {
                npc.draw(g2);
            }
            map.drawEventIndicators(g2);

        /*
        if(map instanceof Office) {
            collision.shiftXY(entity.getWorldX(), entity.getWorldY());
        }

         */
        }
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<NPC> getMapNPCs(){
        return map.getNPCs();
    }
    public ArrayList<Rectangle> getCollisionBoxes(){
        return collision.getCollisionBoxes();
    }

    public Rectangle getTeleportBox(){
        return collision.getTeleportBox();
    }

    public ImageTile getImageTile(int tile){
        return mapTiles.get(tile);
    }
}
