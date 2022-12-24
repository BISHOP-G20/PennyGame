package Objects;

import main.GamePanel;

import java.awt.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public class Object {
    GamePanel gp;

    DirectoryStream<Path> imageFolder;
    Rectangle hitBox;
    Boolean collected;

    public Object(){}

    public void draw(Graphics2D g2){

    }

    public void checkCollision(){
        if (gp.getPlayer().getCollisionBox().intersects(hitBox)) {
            collected = true;
        }
    }

    public boolean checkCollected(){
        return collected;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public int getHitBoxX(){
        return hitBox.x;
    }

    public int getHitBoxY(){
        return hitBox.y;
    }

    public int getHitBoxWidth(){
        return hitBox.width;
    }

    public int getHitBoxHeight(){
        return hitBox.height;
    }
}
