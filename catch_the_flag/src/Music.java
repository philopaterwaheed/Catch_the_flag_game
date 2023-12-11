import javax.sound.sampled.*;

 class audioPlayer { // the audio loading class
    private Clip clip;
    boolean loop = false ; // if play that sound on loop
    audioPlayer(boolean loop){this.loop = loop;}
    public void load(String filePath) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

    }
    public void play() { // play music
        if (clip != null) {
            clip.setFramePosition(0);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound
            }
            clip.start();
        }
    }
    public void stop() { // stop music
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
public class Music { // entity called music represents a sound
     private final audioPlayer audioplayer;
     Music (String path , boolean loop){ // string path the path to the sound file and boolean loop to check if the sound needs to be played on non stop
         audioplayer = new audioPlayer(loop);
         try {
             audioplayer.load(path);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    public void playMusic() {
        audioplayer.play();
    }

    public void stopMusic() {
        audioplayer.stop();
    }
}