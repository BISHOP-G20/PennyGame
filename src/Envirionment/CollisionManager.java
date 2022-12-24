package Envirionment;

import main.GamePanel;
import Entity.Player;

import java.awt.*;

public class CollisionManager {

    private Player player;
    private GamePanel gp;

    public CollisionManager(Player player, GamePanel gp){
        this.player = player;
        this.gp = gp;

    }

    public void checkCollision() {

        if(gp.getGameState() != gp.getBattleState()){
        int entityLeftCol = player.getPlayerLeft() / gp.getScaledTile();
        int entityRightCol = player.getPlayerRight() / gp.getScaledTile();
        int entityTopRow = player.getPlayerTop() / gp.getScaledTile();
        int entityBottomRow = player.getPlayerBottom() / gp.getScaledTile();

        ImageTile entityTopLeftTile = gp.getTileManager().getImageTile(gp.getTileManager().mapTileLocation[entityLeftCol][entityTopRow]);
        ImageTile entityTopRightTile = gp.getTileManager().getImageTile(gp.getTileManager().mapTileLocation[entityRightCol][entityTopRow]);
        ImageTile entityBottomLeftTile = gp.getTileManager().getImageTile(gp.getTileManager().mapTileLocation[entityLeftCol][entityBottomRow]);
        ImageTile entityBottomRightTile = gp.getTileManager().getImageTile(gp.getTileManager().mapTileLocation[entityRightCol][entityBottomRow]);

        int leftCollCol = (player.getPlayerLeft() - entityTopLeftTile.getWorldX()) / gp.getCOLLISION_TILE();
        int rightCollCol = (player.getPlayerRight() - entityTopRightTile.getWorldX()) / gp.getCOLLISION_TILE();
        int topCollRow = (player.getPlayerTop() - entityTopLeftTile.getWorldY()) / gp.getCOLLISION_TILE();
        int bottomCollRow = (player.getPlayerBottom() - entityBottomLeftTile.getWorldY()) / gp.getCOLLISION_TILE();

        boolean collTile1 = false;
        boolean collTile2 = false;

        //check for map change
        int changeMap1;
        int changeMap2;

        switch (player.getDirection()) {
            case "up":
                collTile1 = entityTopLeftTile.getCollTile(leftCollCol, topCollRow).getColBoolean(); // gets top left collision boolean
                collTile2 = entityTopRightTile.getCollTile(rightCollCol, topCollRow).getColBoolean(); // gets top right collision boolean

                changeMap1 = entityTopLeftTile.getCollTile(leftCollCol, topCollRow).getTelNum();
                changeMap2 = entityTopRightTile.getCollTile(rightCollCol, topCollRow).getTelNum();

                if (collTile1 || collTile2) {
                    player.changeWorldY(player.getSpeed());
                }

                if ((changeMap1 == 2 || changeMap2 == 2) && gp.getTileManager().getMap() instanceof MainFloor) {
                    gp.getTileManager().setMap("Office");
                    player.setMapDefault((gp.getScaledTile() / 2) + gp.getScaledTile() + gp.getCOLLISION_TILE(), (gp.getScaledTile() * 3) - (gp.getScaledTile() / 2), "up");

                } else if ((changeMap1 == 2 || changeMap2 == 2) && gp.getTileManager().getMap() instanceof BOH) {
                    gp.getTileManager().setMap("MainFloor");
                    player.setMapDefault(gp.getScaledTile() * 2 + gp.getScaledPxlTile(), gp.getScaledTile() * 6, "up");
                }

                break;

            case "down":
                collTile1 = entityBottomLeftTile.getCollTile(leftCollCol, bottomCollRow).getColBoolean(); // gets bottom left collision boolean
                collTile2 = entityBottomRightTile.getCollTile(rightCollCol, bottomCollRow).getColBoolean(); // gets bottom right collision boolean

                changeMap1 = entityBottomLeftTile.getCollTile(leftCollCol, topCollRow).getTelNum();
                changeMap2 = entityBottomRightTile.getCollTile(rightCollCol, topCollRow).getTelNum();

                if (collTile1 || collTile2) {
                    player.changeWorldY(-(player.getSpeed()));
                }

                if ((changeMap1 == 2 || changeMap2 == 2) && gp.getTileManager().getMap() instanceof Office) {
                    gp.getTileManager().setMap("MainFloor");
                    player.setMapDefault((gp.getScaledTile() * 4) + gp.getCOLLISION_TILE(), gp.getScaledTile(), "down");
                } else if ((changeMap1 == 2 || changeMap2 == 2) && gp.getTileManager().getMap() instanceof MainFloor) {
                    gp.getTileManager().setMap("BOH");
                    player.setMapDefault(gp.getScaledTile() / 2, gp.getScaledPxlTile() * 2, "down");
                }

                break;

            case "right":
                collTile1 = entityTopRightTile.getCollTile(rightCollCol, topCollRow).getColBoolean(); // gets top right collision boolean
                collTile2 = entityBottomRightTile.getCollTile(rightCollCol, bottomCollRow).getColBoolean(); // gets bottom right collision boolean

                if (collTile1 || collTile2) {
                    player.changeWorldX(-(player.getSpeed()));
                }

                break;

            case "left":
                collTile1 = entityTopLeftTile.getCollTile(leftCollCol, topCollRow).getColBoolean(); // gets top left collision boolean
                collTile2 = entityBottomLeftTile.getCollTile(leftCollCol, bottomCollRow).getColBoolean(); // gets bottom left collision boolean

                if (collTile1 || collTile2) {
                    player.changeWorldX(player.getSpeed());
                }

                break;

        }
    }
        else{

            switch (player.getDirection()){
                case "left":
                    if(player.getScreenX() < 0){
                        player.changexVel(0);
                    }
                    break;

                case "right":
                    if((player.getScreenX() + gp.getScaledPxlTile()) > gp.getGAME_WIDTH()){
                        player.changexVel(0);
                    }
                    break;
            }
        }
    }
    /*

    public void checkCollision2(){


        switch (entity.getDirection()){
            case "up":
                if(entity.getCollisionBox().intersects(gp.getTileManager().getTeleportBox()) && gp.getTileManager().getMap() instanceof MainFloor){
                    gp.getTileManager().setMap("Office");
                    entity.setMapDefault(0,0, "up");
                }
                else if(entity.getCollisionBox().intersects(gp.getTileManager().getTeleportBox()) && gp.getTileManager().getMap() instanceof BOH){
                    gp.getTileManager().setMap("MainFloor");
                    entity.setMapDefault(gp.getScaledTile() * 2 + gp.getScaledPxlTile(), gp.getScaledTile() * 6, "up");

                }

                for(Rectangle collisionBox: gp.getTileManager().getCollisionBoxes()){
                    if(entity.getCollisionBox().intersects(collisionBox)){
                        entity.changeWorldY(entity.getSpeed());
                    }
                }

                break;

            case "down":
                if(entity.getCollisionBox().intersects(gp.getTileManager().getTeleportBox()) && gp.getTileManager().getMap() instanceof MainFloor){
                    gp.getTileManager().setMap("BOH");
                    entity.setMapDefault(gp.getScaledTile() / 2, gp.getScaledPxlTile() * 2, "down");
                }
                else if(entity.getCollisionBox().intersects(gp.getTileManager().getTeleportBox()) && gp.getTileManager().getMap() instanceof Office){
                    gp.getTileManager().setMap("MainFloor");
                    entity.setMapDefault((gp.getScaledTile() * 4) + gp.getCOLLISION_TILE(), gp.getScaledTile(), "down");

                }

                for(Rectangle collisionBox: gp.getTileManager().getCollisionBoxes()){
                    if(entity.getCollisionBox().intersects(collisionBox)){
                        entity.changeWorldY(-entity.getSpeed());
                    }
                }
                break;

            case "right":
                for(Rectangle collisionBox: gp.getTileManager().getCollisionBoxes()){
                    if(entity.getCollisionBox().intersects(collisionBox)){
                        entity.changeWorldX(-entity.getSpeed());
                    }
                }
                break;

            case "left":
                for(Rectangle collisionBox: gp.getTileManager().getCollisionBoxes()){
                    if(entity.getCollisionBox().intersects(collisionBox)){
                        entity.changeWorldX(entity.getSpeed());
                    }
                }
                break;

        }
    }

     */
}
