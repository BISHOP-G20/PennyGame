package main;

import Entity.*;
import Envirionment.CollisionManager;
import Envirionment.Map;
import Envirionment.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    //16 pxl 3x --> 48 pxl per tile
    private final int ORIG_PIXEL_SIZE = 16;
    private final int ORIG_TILE_SIZE = 64;
    private final int SCALE = 3;

    private final int SCALED_PXL_TILE = ORIG_PIXEL_SIZE * SCALE;
    private final int SCALED_TILE = ORIG_TILE_SIZE * SCALE;
    private final int COLLISION_TILE = SCALED_TILE / 4;

    private final int maxVertPxlTiles = 14; //672 224
    private final int maxHoriPxlTiles = 18; //864 288

    private final int GAME_HEIGHT = maxVertPxlTiles * SCALED_PXL_TILE;
    private final int GAME_WIDTH = maxHoriPxlTiles * SCALED_PXL_TILE;

    private int gameState;
    private final int startState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int battleState = 4;
    private final int creditsState = 5;

    private Player player;
    private Entity entity;
    private ArrayList<NPC> NPCs;
    public UI ui = new UI(this);

    public ProgressTracker progTracker = new ProgressTracker(this);
    Random rand = new Random();
    TileManager tileManager;
    CollisionManager collisionManager;
    Map map;
    ObjectManager objectManager;
    BossBattle bossBattle;
    Sound music = new Sound();
    Sound SFX = new Sound();
    GameMemory gameMemory = new GameMemory(this);

    KeyHandler keyH = new KeyHandler(this, ui);
    MouseHandler mouseH = new MouseHandler(this, ui);

    Thread thread = new Thread(this);

    public GamePanel() {

        setGameState(startState);

        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.addMouseMotionListener(mouseH);
        this.addMouseListener(mouseH);
        this.setDoubleBuffered(true);
        this.setVisible(true);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        thread.start();
    }

    public void setNPCs() {

        NPCs = new ArrayList<>();

        NPCs.add(new NPC(new Annie(this), this));
        NPCs.add(new NPC(new Carlos(), this));
        NPCs.add(new NPC(new Carson(this), this));
        NPCs.add(new NPC(new George(), this));
        NPCs.add(new NPC(new Izzy(this), this));
        NPCs.add(new NPC(new Sarah(this), this));
        NPCs.add(new NPC(new Steven(this), this));
    }

    public void choosePlayer(int i) {

        switch (i) {
            case 0:
                NPCs.remove(0);
                entity = new Annie(this);
                player = new Player(keyH, entity, this);
                break;

            case 1:

                NPCs.remove(1);
                entity = new Carlos();
                player = new Player(keyH, entity, this);
                break;

            case 2:
                NPCs.remove(2);
                entity = new Carson(this);
                player = new Player(keyH, entity, this);
                break;

            case 3:
                NPCs.remove(3);
                entity = new George();
                player = new Player(keyH, entity, this);
                break;

            case 4:
                NPCs.remove(4);
                entity = new Izzy(this);
                player = new Player(keyH, entity, this);
                break;

            case 5:
                NPCs.remove(5);
                entity = new Sarah(this);
                player = new Player(keyH, entity, this);
                break;

            case 6:
                NPCs.remove(6);
                entity = new Steven(this);
                player = new Player(keyH, entity, this);
                break;

        }

        for(NPC npc:NPCs){
            npc.setDefaultValues(npc.getEntity().getWorldX(), npc.getEntity().getWorldY(), npc.getDirection());
        }
    }

    public void newMap() {

        tileManager = new TileManager(player, this);
        collisionManager = new CollisionManager(player, this);
        map = new Map();
        objectManager = new ObjectManager(tileManager, this);

        tileManager.setMap("MainFloor");
    }

    public void newBattle(){
        bossBattle = new BossBattle(player, this);
        player.setBattleValues();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(gameState == battleState){
            bossBattle.draw(g2);
            objectManager.draw(g2);
        }

        if(gameState != startState && gameState != creditsState) {

            tileManager.draw(g2);

            player.draw(g2);

            for(NPC npc: tileManager.getMapNPCs()){
                npc.draw(g2);
            }
        }

        ui.draw(g2);


            g2.dispose();
        }

        @Override
        public void run() {

            int frameInterval = 1000000000 / 60;
            Double nextTime = (double) System.nanoTime() + frameInterval;


            while (thread != null) {

                double remainingTime = nextTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime > 0) {
                    try {
                        Thread.sleep((long) remainingTime);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                update();
                repaint();
                nextTime += frameInterval;
            }
        }

    public void update () {

        if(gameState == playState) {
            player.move();
        }
    }

    public void playRandomMusic(){
        int num = rand.nextInt(7);

        playMusic(num);
    }

        public void playMusic (int i){
            music.setMusFile(i);
            music.play();
            music.loop();
        }

        public void playSFX (int i){
            SFX.setSFXFile(i);
            SFX.play();
        }

        public void stopMusic () {
            music.stop();
        }

        public void stopSFX () {
            SFX.stop();
        }

    public void setGameState(int gameState) {
        if(gameState == startState){
            ui.setStartScreenBool(true);
        }
        else if(gameState == pauseState){
            ui.resetGameSaved();
            ui.setPauseScreen();
        }
        else if(gameState == creditsState){
            ui.setCredits();
        }

        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }

    public int getStartState() {
        return startState;
    }

    public int getPlayState(){
        return playState;
    }

    public int getPauseState(){
        return pauseState;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public int getBattleState() {
        return battleState;
    }

    public int getCreditsState() {
        return creditsState;
    }

    public int getGAME_HEIGHT () {
            return GAME_HEIGHT;
        }

        public int getGAME_WIDTH () {
            return GAME_WIDTH;
        }

        public int getScaledPxlTile () {
            return SCALED_PXL_TILE;
        }

        public int getScale () {
            return SCALE;
        }

        public int getScaledTile () {
            return SCALED_TILE;
        }

        public int getCOLLISION_TILE () {
            return COLLISION_TILE;
        }

        public CollisionManager getCollisionManager () {
            return collisionManager;
        }

        public TileManager getTileManager () {
            return tileManager;
        }

        public ObjectManager getObjectManager () {
            return objectManager;
        }

        public Player getPlayer () {
            return player;
        }

        public ArrayList<NPC> getNPCs () {
            return NPCs;
        }

    public Map getMap() {
        return tileManager.getMap();
    }

    public GameMemory getGameMemory() {
        return gameMemory;
    }

    public UI getUi(){
        return ui;
    }

}
