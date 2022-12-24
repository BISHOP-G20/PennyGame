package Projectiles;

import main.UtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile {

    UtilityTools uTools = new UtilityTools();

    Rectangle hitBox;

    String direction;
    BufferedImage image;

    int screenX;
    int screenY;

    int mouseX;
    int mouseY;

    int speed;
    int damage;
    double fireRate;

    int x;
    int y;
    int width;
    int height;

    Projectile(){}

    Projectile(int mouseX, int mouseY){
        image = null;

    }

    public void setProjectile(int screenX, int screenY){

        this.screenX = screenX;
        this.screenY = screenY;

        setLocation();
        if(this instanceof Fist) {
            setDirection();
        }
        setProjectileImage();
    }

    public void setLocation(){
        hitBox.setRect(screenX + x, screenY + y, width, height);
    }

    public void setDirection() {

        double deltaY = -(mouseY - screenY);
        double deltaX = mouseX - screenX;


        double radians = Math.atan2(deltaY, deltaX);
        double degrees = Math.toDegrees(radians);

        if(degrees >= 0 && degrees <= 30){
            direction = "right";
        }
        else if(degrees > 30 && degrees <= 65){
            direction = "up right";
        }
        else if(degrees > 65 && degrees <= 115){
            direction = "up";
        }
        else if(degrees > 115 && degrees <= 150){
            direction = "up left";
        }
        else if(degrees > 150 && degrees <= 270){
            direction = "left";
        }
        else if(degrees >= -90 && degrees < 0){
            direction = "right";
        }
        else if(degrees < -90){
            direction = "left";
        }

    }

    abstract public void setProjectileImage();

    public void move(){

        if(direction != null) {
            switch (direction) {
                case "up":
                    screenY -= speed;
                    break;

                case "left":
                    screenX -= speed;
                    break;

                case "right":
                    screenX += speed;
                    break;

                case "up left":
                    screenX -= speed / 3;
                    screenY -= speed / 2;
                    break;

                case "up right":
                    screenX += speed / 3;
                    screenY -= speed / 2;
            }
        }

        hitBox.setRect(screenX + x, screenY + y, width, height);
    }


    public void draw(Graphics g2){
        g2.drawImage(image, screenX, screenY, null);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getWidth() {
        return  width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getDamage() {
        return damage;
    }
}
