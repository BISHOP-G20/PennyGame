package main;

import Entity.NPC;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseHandler implements MouseInputListener {

    GamePanel gp;
    UI ui;
    Rectangle mouse;
    NPC npc;

    double time;

    MouseHandler(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;

        mouse = new Rectangle(0,0, 5, 5);

    }

    public void setNPC(NPC npc){
        this.npc = npc;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        mouse.setRect(x, y, 5, 5);

        if(gp.getGameState() == gp.getPlayState()) {

            for (NPC npc: gp.getMap().getNPCs()) {

                if (mouse.intersects(npc.getCollisionBox()) && npc.getIndicatorState() != npc.getIndicatorNull()) {

                    npc.setIndicatorState(npc.getIndicatorHover());

                } else if (npc.getIndicatorState() == npc.getIndicatorHover() && !mouse.intersects(npc.getCollisionBox())
                        && npc.getIndicatorState() != npc.getIndicatorNull()) {

                    npc.setIndicatorState(npc.getIndicatorNeutral());
                }
            }

            for(Rectangle rect: gp.getMap().getIndicatorBoxes()){
                if(rect != null){
                    if(!mouse.intersects(rect)){
                        gp.getMap().setBattleIndicatorState(gp.getMap().getBattleIndicatorNeutral());
                    }
                    else if(mouse.intersects(rect)){
                        gp.getMap().setBattleIndicatorState(gp.getMap().getBattleIndicatorHover());

                    }
                }
            }
        }
        else if(gp.getGameState() == gp.getPauseState() || (gp.getGameState() == gp.getStartState() && ui.isMenuScreenOn()) ||
                (gp.getGameState() == gp.getStartState() && ui.isCharacterSelectScreenOn())){

            for(int i = 0; i < ui.getButtons().length; ++i){
                if(mouse.intersects(ui.getButtons()[i])){
                    ui.setColorGray(i);
                }
                else if(!mouse.intersects(ui.getButtons()[i])){
                    ui.setColorWhite(i);
                }
            }
        }
        else if(gp.getPlayState() == gp.getBattleState()){
            gp.getPlayer().setMouse(e.getX(), e.getY());
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(gp.getGameState() == gp.getPlayState()) {

            for (NPC npc : gp.getMap().getNPCs()) {
                if (mouse.intersects(npc.getCollisionBox()) && npc.getIndicatorState() != npc.getIndicatorNull()) {
                    npc.setIndicatorState(npc.getIndicatorClicked());
                    gp.setGameState(gp.getDialogueState());
                    setNPC(npc);
                }
            }
        for(Rectangle rect: gp.getMap().getIndicatorBoxes()){
            if(rect != null){
                if(mouse.intersects(rect)){
                    gp.newBattle();
                    gp.setGameState(gp.getBattleState());
                    gp.progTracker.newAttempt();
                }
            }
        }
        }
        else if(gp.getGameState() == gp.getDialogueState()){
            npc.nextDialogue();
        }
        else if(gp.getGameState() == gp.getBattleState()){
            gp.getPlayer().setMousePressHeld(true);
            gp.getPlayer().setMouse(e.getX(), e.getY());

        }
        else if(gp.getGameState() == gp.getPauseState()){
            for(int i = 0; ui.getButtons().length > i; ++i){
                if(mouse.intersects(ui.getButtons()[i])){
                    ui.pauseButtonAction(i);
                }
            }
        }
        else if(gp.getGameState() == gp.getStartState() && ui.isMenuScreenOn()){
            if (mouse.intersects(ui.getButtons()[0])) {
                ui.menuButtonAction(0);
            }
            else if (mouse.intersects(ui.getButtons()[1])) {
                ui.menuButtonAction(1);
            }
            else if (mouse.intersects(ui.getButtons()[2])) {
                ui.menuButtonAction(2);
            }
        }
        else if(gp.getGameState() == gp.getStartState() && ui.isCharacterSelectScreenOn()){
            for(int i = 0; ui.getButtons().length > i; ++i) {
                if (mouse.intersects(ui.getButtons()[i])) {
                    ui.characterSelectButtonAction(i);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(gp.getGameState() == gp.getBattleState()){
            gp.getPlayer().setMousePressHeld(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}