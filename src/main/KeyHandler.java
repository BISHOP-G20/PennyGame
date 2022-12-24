package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    UI ui;
    public boolean up, down, right, left, space;

    KeyHandler(GamePanel gp, UI ui){
        this.gp = gp;
        this.ui = ui;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(gp.getGameState() != gp.getBattleState()) {

            if (key == KeyEvent.VK_W) {
                up = true;
            }

            if (key == KeyEvent.VK_S) {
                down = true;
            }

            if (key == KeyEvent.VK_A) {
                left = true;
            }

            if (key == KeyEvent.VK_D) {
                right = true;
            }

            if (key == KeyEvent.VK_ESCAPE && gp.getGameState() == gp.getPlayState()) {
                gp.setGameState(gp.getPauseState());
            } else if (key == KeyEvent.VK_ESCAPE && gp.getGameState() == gp.getPauseState()) {
                gp.setGameState(gp.getPlayState());
            }

            if (key == KeyEvent.VK_ENTER && gp.getGameState() == gp.getStartState() && ui.isStartScreenOn()) {
                ui.setMenuScreenBool(true);
            }
        }
        else if(gp.getGameState() == gp.getBattleState()){

            if(key == KeyEvent.VK_A){
                left = true;
            }
            if(key == KeyEvent.VK_D){
                right = true;
            }
            if(key == KeyEvent.VK_SPACE){
                space = true;
            }
            if (key == KeyEvent.VK_ESCAPE && gp.getGameState() == gp.getBattleState()) {
                gp.setGameState(gp.getPauseState());
            } else if (key == KeyEvent.VK_ESCAPE && gp.getGameState() == gp.getPauseState()) {
                gp.setGameState(gp.getBattleState());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if(gp.getGameState() != gp.getBattleState()) {
            if (key == KeyEvent.VK_W) {
                up = false;
            }

            if (key == KeyEvent.VK_S) {
                down = false;
            }

            if (key == KeyEvent.VK_A) {
                left = false;
            }

            if (key == KeyEvent.VK_D) {
                right = false;
            }
        }else if(gp.getGameState() == gp.getBattleState()){

            if(key == KeyEvent.VK_A){
                left = false;
            }
            if(key == KeyEvent.VK_D){
                right = false;
            }
            if(key == KeyEvent.VK_SPACE){
                space = false;
            }

        }
    }
}
