package Envirionment;

public class MainFloorCollision extends CollisionMap{

    MainFloor mainFloor;

    MainFloorCollision(MainFloor mainFloor) {
        super();

        this.mainFloor = mainFloor;

        collColTiles = mainFloor.getWorldCol() * 4;
        collRowTiles = mainFloor.getWorldRow() * 4;

        inputStream = getClass().getResourceAsStream("/CollisionMaps/MainFloorCollision.txt");
    }
}
