package Entity;

import main.GamePanel;

import java.util.ArrayList;

public class Sarah extends Entity{

    GamePanel gp;

    public Sarah(GamePanel gp){
        super();

        this.gp = gp;

        characterName = "Sarah";
        direction = "right";
        progressDependent = true;
        worldX = 96;
        worldY = 192 * 3 + 96;
        setStandingImages();
    }

    @Override
    public void setDialogue(){
        String bohName = "Carson";
        String pronoun = "She";

        if(gp.getPlayer().getPlayerName().equals("Carson")){
            bohName = "Steven";
            pronoun = "He";
        }

        dialogue = new ArrayList<>();

        switch(gp.progTracker.getProgress()) {

            case 0, 1:
                if (gp.progTracker.getProgress() == 0 && dialogueSet == 0) {
                    dialogue.add("Hey there!");
                    dialogue.add("Thank you so much for coming in!");
                    dialogue.add("The cafe is a mess and I need your help.");
                    dialogue.add("A few of the machines are malfunctioning\n and the BOH smells like a sewage tank!");
                    dialogue.add("Could you start by cleaning up the back?");
                    dialogue.add("Go talk to " + bohName + " for instructions.\n " + pronoun + " will know what to do.");
                }
                else if(dialogueSet == 0){
                    dialogue.add("Have you talked to " + bohName + "?");
                    dialogueSet = 1;
                }
                else if (dialogueSet == 1) {
                    dialogue.add("Let's get to work! Lots to do!");
                    if(gp.progTracker.getProgress() == 0) {
                        gp.progTracker.setProgress(1);
                    }
                } else if (dialogueSet == 2) {
                    dialogue.add("Have you talked to " + bohName + "?");
                    dialogueSet = 0;
                }
                break;

            case 2:

                if (dialogueSet == 0 && gp.progTracker.getAttempt() == 0) {
                    dialogue.add("Let's get to work! Lots to do!");
                }
                else if (dialogueSet == 1 && gp.progTracker.getAttempt() == 0) {
                    dialogue.add("Have you talked to " + bohName + "?");
                    dialogueSet = -1;
                }
                else if (gp.progTracker.getAttempt() > 0) {

                    String plural = "time";

                    if (gp.progTracker.getAttempt() > 1) {
                        plural = "times";
                    }

                    dialogue.add("Nice try!");
                    dialogue.add("It takes a bit to get used to.");
                    dialogue.add("Wait! You've died " + gp.progTracker.getAttempt() + " " + plural + "?");
                    dialogue.add("That's kind of a lot. . .");
                    dialogue.add("Maybe next time!");
                }
                break;

            case 3:
                if(dialogueSet == 0) {
                    dialogue.add("Great job " + gp.getPlayer().getPlayerName() + "!");
                    dialogue.add("I knew you had it in you!");
                    dialogue.add("Well, that's the end of your shift today.\n You can go home!");
                    dialogue.add(". . .");
                    dialogue.add("Look, I know you weren't here for very\n long but there isn't any more for you to do.");
                    dialogue.add("Truth is the crew here has everything\n handled.");
                    dialogue.add("What can I say? We have a great team!");
                    dialogue.add("Feel free to come back and clean out the\n sink anytime!");
                    dialogue.add("You're always welcome in!");
                    dialogue.add("See you soon!");
                }
                else{
                    gp.setGameState(gp.getCreditsState());
                }
                break;
        }
    }
}
