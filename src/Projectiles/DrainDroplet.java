package Projectiles;

import javax.imageio.ImageIO;
import java.awt.*;

public class DrainDroplet extends Projectile{

    public DrainDroplet(int screenX, int screenY){
        super();

        x = 9;
        y = 0;
        width = 30;
        height = 45;

        speed = 3;
        damage = 8;

        hitBox = new Rectangle(x, y, width, height);

        setProjectile(screenX, screenY);
    }

    public void setProjectileImage(){
        try{
            image = uTools.scaleImage(ImageIO.read(getClass().getResourceAsStream("/res/Bosses/Drain/droplet.png"))
                    , 48, 48);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void move(){

        screenX += 1;
        screenY += 5;

        hitBox.setRect(screenX + x, screenY + y, width, height);
    }
}
