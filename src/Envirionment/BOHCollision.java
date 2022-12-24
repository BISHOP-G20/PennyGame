package Envirionment;

public class BOHCollision extends CollisionMap{

    BOH backOfHouse;

    BOHCollision(BOH backOfHouse){
        super();

        this.backOfHouse = backOfHouse;

        collColTiles = backOfHouse.getWorldCol() * 4;
        collRowTiles = backOfHouse.getWorldRow() * 4;

        inputStream = getClass().getResourceAsStream("/CollisionMaps/BOHCollision.txt");
    }
}
