package main;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundtrack[] = new URL[8];
    URL soundEffect[] = new URL[4];

    public Sound(){

        soundtrack[0] = getClass().getResource("/res/Music/Can You Really Call This A Hotel I Didn't Receive A Mint On My Pillow.wav");
        soundtrack[1] = getClass().getResource("/res/Music/Inside a House.wav");
        soundtrack[2] = getClass().getResource("/res/Music/Barefoot In The Park.wav");
        soundtrack[3] = getClass().getResource("/res/Music/World Map 3.wav");
        soundtrack[4] = getClass().getResource("/res/Music/Dog.wav");
        soundtrack[5] = getClass().getResource("/res/Music/Staff Roll.wav");
        soundtrack[6] = getClass().getResource("/res/Music/Kokiri Forest.wav");
        soundtrack[7] = getClass().getResource("/res/Music/At Doom's Gate.wav");

        soundEffect[0] = getClass().getResource("/res/SFX/Footsteps.wav");
        soundEffect[1] = getClass().getResource("/res/SFX/CoinCollect.wav");
        soundEffect[2] = getClass().getResource("/res/SFX/Projectile_shoot.wav");
        soundEffect[3] = getClass().getResource("/res/SFX/Sans Voice.wav");
    }

    public void setMusFile(int i){

        try{
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundtrack[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);

            FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-10.0f);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setSFXFile(int i){

        try{
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundEffect[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
