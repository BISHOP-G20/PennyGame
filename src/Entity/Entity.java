package Entity;

import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {

    int worldX;
    int worldY;

    String up, down, left, right;

    String characterName;
    String direction;
    BufferedImage[] standingImages = new BufferedImage[4];
    BufferedImage directionImage;

    int totalDialogueSets;
    int dialogueSet;
    ArrayList<String> dialogue;

    boolean player = false;
    boolean progressDependent;

    public Entity(){
        dialogueSet = 0;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }

    public void setStandingImages(){

        UtilityTools uTools = new UtilityTools();

        up = "/res/Players/" + characterName + "/" + characterName + "_up_stand.png.png";
        down = "/res/Players/" + characterName + "/" + characterName + "_down_stand.png.png";
        left = "/res/Players/" + characterName + "/" + characterName + "_left_stand.png.png";
        right = "/res/Players/" + characterName + "/" + characterName + "_right_stand.png.png";

        try {
            standingImages[0] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(up)), 58, 58);
            standingImages[1] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(down)), 58, 58);
            standingImages[2] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(left)), 58, 58);
            standingImages[3] = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream(right)), 58, 58);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void setDialogue(){
    }
    public void setDialogueSet(int set){
        dialogueSet = set;
    }

    public void nextDialogueSet(){
        ++dialogueSet;
    }

    public BufferedImage[] getCharacterImages(){
        return standingImages;
    }

    public String getCharacterName(){
        return characterName;
    }

    public ArrayList<String> getDialogue(){
        return dialogue;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY(){
        return worldY;
    }

    public String getDirection(){
        return direction;
    }

    public int getTotalDialogueSets() {
        return totalDialogueSets;
    }

    public boolean isProgressDependent() {
        return progressDependent;
    }


    public BufferedImage getCharacterImage(){

        directionImage = standingImages[1];

        switch(direction){
            case "up":
                directionImage = standingImages[0];
                break;

            case "down":
                directionImage = standingImages[1];
                break;

            case "left":
                directionImage = standingImages[2];
                break;

            case "right":
                directionImage = standingImages[3];
                break;
        }


        return directionImage;
    }

    public BufferedImage getCharDownImage(){
        return standingImages[1];
    }
}
