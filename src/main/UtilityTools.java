package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTools {

    public BufferedImage scaleImage(BufferedImage originalImg, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, originalImg.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(originalImg, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
