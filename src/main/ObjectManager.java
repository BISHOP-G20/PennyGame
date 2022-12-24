package main;

import Entity.Player;
import Envirionment.TileManager;
import Objects.Coin;
import Objects.Object;

import java.awt.*;
import java.util.ArrayList;

public class ObjectManager {

    GamePanel gp;
    Player player;
    TileManager tileManager;
    ArrayList<Object> mapObjects;

    ObjectManager(TileManager tileManager, GamePanel gp){
        this.gp = gp;
        this.tileManager = tileManager;

        player = gp.getPlayer();
    }

    public void loadCoins(){
        mapObjects = new ArrayList<>();
        for(int i = 0; i < 5; ++i){
            for(int j = 0; j < 7; ++j){
                mapObjects.add(new Coin(gp, ((gp.getGAME_WIDTH()/6) * j - 50), (i * 40) + 40));
            }
        }
    }

    public void draw(Graphics2D g2) {

        if (mapObjects != null){
            for (int i = 0; i < mapObjects.size(); ++i) {
                mapObjects.get(i).checkCollision();

                if (!mapObjects.get(i).checkCollected()) {
                    mapObjects.get(i).draw(g2);
                } else {
                    mapObjects.remove(i);
                }
            }
    }
    }
}
