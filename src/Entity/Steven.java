package Entity;

import main.GamePanel;

import java.util.ArrayList;

public class Steven extends Entity{

    GamePanel gp;

    public Steven(GamePanel gp){
        super();

        this.gp = gp;

        characterName = "Steven";
        direction = "up";
        totalDialogueSets = 2;
        progressDependent = true;
        worldX = 80;
        worldY = 192 * 2 + ((192/4) * 2) - 15;
        setStandingImages();
    }

    @Override
    public void setDialogue(){
        dialogue = new ArrayList<>();

        String managerName = "Annie";

        if(gp.getPlayer().getPlayerName().equals("Annie")){
            managerName = "Sarah";
        }

        switch(gp.progTracker.getProgress()) {

            case 0:

                dialogue.add("Whats up?");
                dialogueSet = 0;
                break;

            case 1:
                if (dialogueSet == 0) {

                    dialogue.add("Yo, what's good?");
                    dialogue.add(managerName + " told you about the problem, right?");
                    dialogue.add("The sink drain is packed up with rotten\n shit.");
                    dialogue.add("Can you clean out the gunk?");
                    dialogue.add("A few people have already tried\n and come back covered in sludge.");
                    dialogue.add("Something weird is going on with\n that sink. . .");
                    dialogue.add("But if anybody can handle it\n it'll be you.");
                    dialogue.add("You got it! Probably. . .");
                    dialogue.add("Let me know when you're done.");
                } else if (dialogueSet >= 1) {
                    gp.progTracker.setProgress(2);
                    dialogue.add("You finish the drain?");
                }
                break;

            case 2:
                if (gp.progTracker.getAttempt() == 0) {
                    dialogue.add("You finish the drain?");
                }

                else if ( gp.progTracker.getAttempt() > 0) {
                    String plural = "time";

                    if (gp.progTracker.getAttempt() > 1) {
                        plural = "times";
                    }

                    dialogue.add("You got that.");
                    dialogue.add("Get back in there!");
                    dialogue.add("You've only died " + gp.progTracker.getAttempt() + " " + plural + ".");
                    dialogue.add("You'll win the next one for sure!");
                }
                break;

            case 3:

                dialogue.add("Nice one bro!");
                dialogue.add("That's all for back here I think. . .");
                dialogue.add("You should probably go talk to " + managerName + "\nup front. She'll tell you what's next.");
                dialogue.add("Appreciate all the help man!");
                break;
        }
    }
}
