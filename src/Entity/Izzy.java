package Entity;

import main.GamePanel;

import java.util.ArrayList;

public class Izzy extends Entity{

    GamePanel gp;
    public Izzy(GamePanel gp){
        super();

        this.gp = gp;
        characterName = "Izzy";
        totalDialogueSets = 1;
        progressDependent = false;
        direction = "right";
        worldX = 192 * 2 + 96;
        worldY = 192 + 48;
        setStandingImages();
    }

    @Override
    public void setDialogue(){
        dialogue = new ArrayList<>();

        String managerName = "annie";

        if(gp.getPlayer().getPlayerName().equals("Annie")){
            managerName = "sarah";
        }

        switch (gp.progTracker.getProgress()) {

            case 0,1,2:
                if (dialogueSet == 0) {
                    dialogue.add("Oop!");
                    dialogue.add("Hi!!");
                    dialogue.add("I was just folding to-go boxes!");
                    dialogue.add(". . . ");
                    dialogue.add("Anyway!");
                    dialogue.add("Has " + managerName + " told you about all the problems\n she found this morning?");
                    dialogue.add("CRAAAZY, right?");
                    dialogue.add("I bet Steven has something to do with it. . .\n He's always fucking up something.");
                    dialogue.add("Somebody should really fire him. . .");
                    dialogue.add("Let me know if you need any help!");
                } else if (dialogueSet > 0) {
                    dialogue.add("Sorry! I'm busy right now!");
                    dialogueSet = 1;
                }
                break;

            case 3,4:
                if (dialogueSet == 0) {
                    dialogue.add("I heard you fixed the sink!");
                    dialogue.add("That sounds tough, good for you!");
                    dialogue.add("I'm almost done these boxes, then I think\n I'm cooking with cathy!");
                    dialogue.add("Let me know if you want me to cook you\n anything.");
                    dialogue.add("From one cook to another, my 'Originals'\n are bomb.");
                }
                else{
                    dialogue.add("Can't talk, need to work! ;)");
                    dialogue.add("Nah, I'm just fucking with you\n What's up?");
                }
        }
    }

}
