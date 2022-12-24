package Projectiles;

import javax.imageio.ImageIO;
import java.awt.*;

public class Fist extends Projectile{

    public Fist(int mouseX, int mouseY){
        super(mouseX, mouseY);

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        speed = 8;
        damage = 5;
        fireRate = 0.5;

        x = 8;
        y = 8;
        width = 32;
        height = 32;

        hitBox = new Rectangle(x, y, width, height);

        image = null;

    }

    @Override
    public void setProjectileImage(){

        try {
            switch (direction) {
                case "up":
                    image = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Projectiles/Fist/Fist_up.png")),
                            32, 32);
                    break;

                case "left":
                    image = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Projectiles/Fist/Fist_left.png")),
                            32, 32);
                    break;

                case "right":
                    image = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Projectiles/Fist/Fist_right.png")),
                            32, 32);
                    break;

                case "up left":
                    image = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Projectiles/Fist/Fist_up_left.png")),
                            32, 32);
                    break;

                case "up right":
                    image = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Projectiles/Fist/Fist_up_right.png")),
                            32, 32);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
