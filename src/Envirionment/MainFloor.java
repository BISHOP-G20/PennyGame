package Envirionment;

import Entity.NPC;
import main.GamePanel;
import Objects.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class MainFloor extends Map{

    MainFloor(GamePanel gp) {
        super();

        this.gp = gp;
        mapName = "MainFloor";
        indicatorBoxes = new Rectangle[2];

        tileFolder = new DirectoryStream() {
            @Override
            public Iterator iterator() {
                return null;
            }

            @Override
            public void close() throws IOException {
                close();

            }
        };

        worldRow = 7;
        worldCol = 6;

        npcNames = new String[]{"Annie", "Sarah"};
        totalMapNpcs = 1;

        try {
            Path dir = Paths.get("src/res/Maps/MainFloor");
            tileFolder = Files.newDirectoryStream(dir);

            tilePaths = new ArrayList<>();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        for(Path tile: tileFolder){

            tilePaths.add(tile.getFileName());
        }

        setNPCs();
        for(NPC npc: NPCs){
            npc.getEntity().setDialogue();
        }

    }

    @Override
    public void loadCoins(GamePanel gp){
        objects = new ArrayList<>();
        /*
        coins = new Coin[4];

        for(int i = 0; i < coins.length; ++i){

            coins[i] = new Coin(gp);
            coins[i].setWorldX((gp.getScaledTile() * 2) + (i * gp.getCOLLISION_TILE()));
            coins[i].setWorldY(gp.getScaledTile() * 3);

            objects.add(coins[i]);
        }

         */
    }

    @Override
    public ArrayList<NPC> getNPCs(){
        return NPCs;
    }

}
