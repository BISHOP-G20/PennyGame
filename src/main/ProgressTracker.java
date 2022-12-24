package main;

import Entity.NPC;

public class ProgressTracker {

    GamePanel gp;
    private int progress;
    private int attempt;

    private boolean bossSinkEnabled;

    ProgressTracker(GamePanel gp){
        this.gp = gp;

        progress = 0;
        attempt = 0;
        bossSinkEnabled = false;

    }

    public void setProgress(int progress){
        this.progress = progress;

        for(NPC npc: gp.getNPCs()){
            npc.getEntity().setDialogueSet(0);
        }

        if(progress == 2){
            bossSinkEnabled = true;
            gp.getMap().setEventIndicator(0);
        }
    }

    public int getProgress() {
        return progress;
    }

    public void newAttempt(){
        ++attempt;
    }

    public int getAttempt() {
        return attempt;
    }

    public boolean isBossSinkEnabled() {
        return bossSinkEnabled;
    }
}
