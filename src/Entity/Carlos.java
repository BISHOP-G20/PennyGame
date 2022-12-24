package Entity;

import java.util.ArrayList;

public class Carlos extends Entity{

    public Carlos(){
        super();

        characterName = "Carlos";
        progressDependent = false;
        direction = "down";
        setStandingImages();
    }

    @Override
    public void setDialogue(){
        dialogue = new ArrayList<>();
    }
}
