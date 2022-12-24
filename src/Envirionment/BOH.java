package Envirionment;

import Entity.NPC;
import main.GamePanel;
import main.UtilityTools;

import java.awt.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class BOH extends Map{

    UtilityTools utilityTools;

    BOH(GamePanel gp){
        super();

        this.gp = gp;
        mapName = "BOH";
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

        worldRow = 3;
        worldCol = 3;

        npcNames = new String[]{"Carson", "Steven"};
        totalMapNpcs = 1;

        utilityTools = new UtilityTools();

        try {
            Path dir = Paths.get("src/res/Maps/BOH");
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
        /*
        try {
            eventIndicator = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/SOMETHING.PNG")), 48, 48);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        */

    }
    @Override
    public void setEventIndicator(int indicator){
        indicatorBoxes[indicator] = new Rectangle(0,0, 48, 48);
        setIndicatorImages();
        battleIndicatorState = battleIndicatorNeutral;
    }

    @Override
    public void drawEventIndicators(Graphics2D g2) {
        super.drawEventIndicators(g2);

        if(indicatorBoxes[0] != null){

            int screenX = (gp.getScaledTile() * 3) - 50 - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = (gp.getScaledTile() + 65) - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if(battleIndicatorState == battleIndicatorNeutral){
                g2.drawImage(battleIndicators[0], screenX, screenY, null);
            }
            else if(battleIndicatorState == battleIndicatorHover){
                g2.drawImage(battleIndicators[1], screenX, screenY, null);
            }

            if(indicatorBoxes[0] != null) {
                indicatorBoxes[0].setRect(screenX, screenY, 48, 48);
            }
        }
    }

    @Override
    public void loadCoins(GamePanel gp) {
        objects = new ArrayList<>();
    }
}
