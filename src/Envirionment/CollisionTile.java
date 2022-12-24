package Envirionment;
import java.awt.*;

public class CollisionTile extends Rectangle{

    private boolean collision;
    private int teleportTile;
    private final int COLLISION_TILE_SIZE = 48;

    CollisionTile(int x, int y, int collisionNum){
        super();
        setRect(x, y, COLLISION_TILE_SIZE, COLLISION_TILE_SIZE);

        if(collisionNum == 0){
            collision = false;
            teleportTile = 0;
        }
        else if(collisionNum == 1){
            collision = true;
            teleportTile = 0;
        }
        else if(collisionNum == 2){
            collision = false;
            teleportTile = 2;
        }
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void printXY(){
        System.out.println("X: " + x + " Y: " + y);
    }

    public boolean getColBoolean(){
        return collision;
    }

    public int getTelNum(){
        return teleportTile;
    }
}
