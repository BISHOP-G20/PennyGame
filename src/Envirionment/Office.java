package Envirionment;

import Entity.NPC;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class Office extends Map{

    Office(GamePanel gp) {
        super();

        this.gp = gp;
        mapName = "Office";
        indicatorBoxes = new Rectangle[1];

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

        worldRow = 4;
        worldCol = 4;

        npcNames = new String[]{"Izzy"};
        totalMapNpcs = 1;

        try {
            Path dir = Paths.get("src/res/Maps/Office");
            tileFolder = Files.newDirectoryStream(dir);

            tilePaths = new ArrayList<>();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        for(Path tile: tileFolder){

            tilePaths.add(tile.getFileName());
        }
        if(!gp.getPlayer().getPlayerName().equals("Izzy")){

            setNPCs();
        }
        else{
            NPCs = new ArrayList<>();
        }

        for(NPC npc: NPCs){
            npc.getEntity().setDialogue();
        }
    }

    @Override
    public void loadCoins(GamePanel gp) {
        objects = new ArrayList<>();
    }
}

