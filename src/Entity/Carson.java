package Entity;

import main.GamePanel;

import java.util.ArrayList;

public class Carson extends Entity{

    GamePanel gp;

    public Carson(GamePanel gp) {
        super();

        this.gp = gp;

        characterName = "Carson";
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

        String managerName = "annie";

        if(gp.getPlayer().getPlayerName().equals("Annie")){
            managerName = "sarah";
        }

        switch (gp.progTracker.getProgress()) {

            case 0:
                dialogue.add("? ? ?");
                dialogueSet = 0;
                break;

            case 1:
            if (dialogueSet == 0) {

                dialogue.add("oh . . .");
                dialogue.add("it's you. . .");
                dialogue.add("well, i guess anybody can clean a sink.");
                dialogue.add(managerName + " told you about the problem, right?");
                dialogue.add("the sink drain is packed up with old-\n rotten- food.");
                dialogue.add("i need you to get your hands dirty and \n clean out the gunk.");
                dialogue.add("a few of the other employees have tried\n and come back covered in nutella.");
                dialogue.add("seems like the drain is putting up\n more of a fight than usual");
                dialogue.add("good luck!");
                dialogue.add("let me know when you're done.");
            } else if ( dialogueSet >= 1 && gp.progTracker.getAttempt() == 0) {
                gp.progTracker.setProgress(2);
                dialogue.add("get to work!");
            }
            break;

            case 2:

                if (gp.progTracker.getAttempt() == 0) {
                    dialogue.add("get to work!");
                }
                else if (gp.progTracker.getAttempt() > 0) {
                    String plural = "time";

                    if (gp.progTracker.getAttempt() > 1) {
                        plural = "times";
                    }

                    dialogue.add("good try.");
                    dialogue.add("its not that hard though. . .");
                    dialogue.add("you've died " + gp.progTracker.getAttempt() + " " + plural + ".");
                    dialogue.add("damn dude.");
                    dialogue.add("rough.");
                }
                break;

            case 3:

                dialogue.add("nice work!");
                dialogue.add("that's all i have for you back here.");
                dialogue.add("you should probably go see " + managerName + "\n up front. She'll tell you whatever is next.");
                dialogue.add("thanks for the help!");
                break;
        }
    }
}
