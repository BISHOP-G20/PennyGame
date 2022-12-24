package Envirionment;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class OfficeCollision extends CollisionMap{

    Office office;

    Rectangle outerWall;
    Rectangle desk;
    Rectangle refrigerator;
    Rectangle chair;
    Rectangle ladder;
    Rectangle rackTopLeft;
    Rectangle rackTopRight;
    Rectangle rackBottomLeft;

    OfficeCollision(Office office, GamePanel gp){
        super();

        this.gp = gp;
        this.office = office;

        playerScreenX = gp.getPlayer().getScreenX();
        playerScreenY = gp.getPlayer().getScreenY();

        outerWall = new Rectangle(0, 96, gp.getScaledTile() * 2, (gp.getScaledTile() * 2) + 96);
        desk = new Rectangle(gp.getScaledTile() + 144, gp.getScaledTile() + 15, 48, gp.getScaledTile() + 177);
        refrigerator = new Rectangle(0, gp.getScaledTile() + 21, 76, 130);
        chair = new Rectangle(gp.getScaledTile() + 76, gp.getScaledTile() + 54, 55, 63);
        ladder = new Rectangle(gp.getScaledTile() + 97, gp.getScaledTile() * 2 + 93, 46, 48);
        rackTopLeft = new Rectangle(0, 126, 110, 117);
        rackTopRight = new Rectangle(gp.getScaledTile() + 118, 60, 75, 132);
        rackBottomLeft = new Rectangle(0, gp.getScaledTile() * 2, 82, 156);

        teleportBox = new Rectangle(118, gp.getScaledTile() * 2 + 168, 54, 24);

        collisionBoxes = new ArrayList<>();

        collisionBoxes.add(outerWall);
        collisionBoxes.add(desk);
        collisionBoxes.add(refrigerator);
        collisionBoxes.add(chair);
        collisionBoxes.add(ladder);
        collisionBoxes.add(rackTopLeft);
        collisionBoxes.add(rackTopRight);
        collisionBoxes.add(rackBottomLeft);


        collColTiles = office.getWorldCol() * 4;
        collRowTiles = office.getWorldRow() * 4;

        inputStream = getClass().getResourceAsStream("/CollisionMaps/OfficeCollision.txt");
    }
}
