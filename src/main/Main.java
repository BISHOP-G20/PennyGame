package main;

import main.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Penny Path");
        window.setVisible(true);
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
    }
}
