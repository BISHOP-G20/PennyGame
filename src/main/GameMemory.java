package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameMemory {

    FileOutputStream outputStream;
    BufferedReader bufferedReader;
    GamePanel gp;

    File saveFile;

    private String map;
    private String charName;
    private int[] worldLocation;
    private int progress;

    private boolean fileExists;

    GameMemory(GamePanel gp) {

        this.gp = gp;

        fileExists = Files.exists(Path.of("src/res/SaveFile.txt"));
        saveFile = new File("src/res/SaveFile.txt");
    }

    public void saveGame(){
        worldLocation = new int[2];

        charName = gp.getPlayer().getPlayerName();
        progress = gp.progTracker.getProgress();

        if(gp.getGameState() != gp.getBattleState()) {
            map = gp.getMap().getMapName();
            worldLocation[0] = gp.getPlayer().getWorldX();
            worldLocation[1] = gp.getPlayer().getWorldY();
        }
        else{
            map = "BOH";
            worldLocation[0] = (gp.getScaledTile() * 2) + (gp.getScaledTile() / 2);
            worldLocation[1] = gp.getScaledTile() + (gp.getScaledTile() / 2);
        }

        try {

            outputStream = new FileOutputStream(saveFile);

            outputStream.write((map + "\n").getBytes());
            outputStream.write((charName + "\n").getBytes());
            outputStream.write((worldLocation[0] + "\n").getBytes());
            outputStream.write((worldLocation[1] + "\n").getBytes());
            outputStream.write((progress + "").getBytes());

            outputStream.flush();
            outputStream.close();
            fileExists = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(){

        String[] saveData = new String[5];
        int listNum = 0;
        int ch;

        if(fileExists) {
            try {
                bufferedReader = new BufferedReader(new FileReader(saveFile));

                for (int i = 0; i < saveData.length; ++i) {
                    saveData[i] = bufferedReader.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("load error");
            } catch (Exception e) {
                e.printStackTrace();
            }

            gp.setNPCs();

            switch(saveData[1]){
                case "Annie":
                    gp.choosePlayer(0);
                    break;

                case "Carlos":
                    gp.choosePlayer(1);
                    break;

                case "Carson":
                    gp.choosePlayer(2);
                    break;

                case "George":
                    gp.choosePlayer(3);
                    break;

                case "Izzy":
                    gp.choosePlayer(4);
                    break;

                case "Sarah":
                    gp.choosePlayer(5);
                    break;

                case "Steven":
                    gp.choosePlayer(6);
                    break;
            }

            gp.newMap();
            gp.getPlayer().setMapDefault(Integer.valueOf(saveData[2]), Integer.valueOf(saveData[3]), "down");
            gp.getTileManager().setMap(saveData[0]);
            gp.progTracker.setProgress(Integer.valueOf(saveData[4]));
            gp.playRandomMusic();
            gp.setGameState(gp.getPlayState());
        }
        else{
            gp.getUi().loadError();
        }


    }

    public void newGame(){

        if(fileExists){
            saveFile.delete();
        }
        fileExists = false;
    }
}