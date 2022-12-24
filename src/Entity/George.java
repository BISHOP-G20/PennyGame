package Entity;

import Envirionment.Map;
import main.GamePanel;
import main.KeyHandler;

import java.util.ArrayList;

public class George extends Entity{

    public George() {
        super();

        characterName = "George";
        setStandingImages();
    }

    @Override
    public void setDialogue(){
        dialogue = new ArrayList<>();
    }
}
