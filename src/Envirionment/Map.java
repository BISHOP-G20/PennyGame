package Envirionment;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;

import Entity.NPC;
import Objects.*;
import Objects.Object;
import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;

public class Map {

    GamePanel gp;
    DirectoryStream<Path> tileFolder;

    ArrayList tilePaths;
    ArrayList<Object> objects;
    ArrayList<NPC> NPCs;

    BufferedImage[] battleIndicators;
    Rectangle[] indicatorBoxes;

    String[] npcNames;
    int totalMapNpcs;
    String mapName;
    Coin[] coins;

    int worldRow;
    int worldCol;

    int battleIndicatorState;
    int battleIndicatorNeutral = 1;
    int battleIndicatorHover = 2;
    int battleIndicatorNull = 0;

    UtilityTools utilityTools = new UtilityTools();

    public Map(){

        battleIndicatorState = battleIndicatorNull;
    }

    public void loadCoins(GamePanel gp){

    }

    public void setEventIndicator(int indicator){

    }

    public void setNPCs() {
        int i = 0;

        if(NPCs == null) {
            NPCs = new ArrayList<>();
            while (NPCs.size() < totalMapNpcs) {

                for (NPC npc : gp.getNPCs()) {
                    if (npc.getCharacterName().equals(npcNames[i])) {
                        NPCs.add(npc);
                        ++i;
                        break;
                    }
                }
                ++i;
            }
        }

        for(NPC npc: NPCs){
            if(npc.getEntity().getDialogue().size() > 0){
                npc.setIndicatorState(npc.getIndicatorNeutral());
                npc.getEntity().setDialogue();
            }
        }

    }

    public void setIndicatorImages(){
        battleIndicators = new BufferedImage[2];

        try{
            battleIndicators[0] = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Battle_Indicator.png"))
                    , 48, 48);

            battleIndicators[1] = utilityTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/UI_Images/Battle_Indicator_hover.png")),
                    48, 48);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void drawEventIndicators(Graphics2D g2){

    }

    public void setBattleIndicatorState(int battleIndicatorState) {
        this.battleIndicatorState = battleIndicatorState;
    }

    public Rectangle[] getIndicatorBoxes(){
        return indicatorBoxes;
    }

    public ArrayList<NPC> getNPCs(){
        return NPCs;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public String getMapName() {
        return mapName;
    }

    public int getWorldCol(){
        return worldCol;
    }

    public int getWorldRow(){
        return worldRow;
    }

    public ArrayList getTilePaths() {
        return tilePaths;
    }

    public int getBattleIndicatorState() {
        return battleIndicatorState;
    }

    public int getBattleIndicatorNeutral() {
        return battleIndicatorNeutral;
    }

    public int getBattleIndicatorHover() {
        return battleIndicatorHover;
    }

    public int getBattleIndicatorNull() {
        return battleIndicatorNull;
    }
}
