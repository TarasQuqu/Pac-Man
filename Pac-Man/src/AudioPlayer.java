import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	private static final int BUFFER_SIZE = 4096;//size of the byte buffer used to read/write the audio stream

public static  void play(String audioFilePath) {
    File audioFile = new File(audioFilePath);
    try {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

        audioLine.open(format);

        audioLine.start();
         
        System.out.println("Playback started.");
         
        byte[] bytesBuffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
            audioLine.write(bytesBuffer, 0, bytesRead);
        }
        //Stop_audio(audioStream,audioLine); 
        //play("C:\\Users\\grene\\eclipse-workspace\\Pac-Man\\res\\audio\\CantinaBand60.wav");
         
         
    } catch (UnsupportedAudioFileException ex) {
        System.out.println("The specified audio file is not supported.");
        ex.printStackTrace();
    } catch (LineUnavailableException ex) {
        System.out.println("Audio line for playing back is unavailable.");
        ex.printStackTrace();
    } catch (IOException ex) {
        System.out.println("Error playing the audio file.");
        ex.printStackTrace();
    }      
}
public static void Stop_audio(AudioInputStream audioStream,SourceDataLine audioLine) throws IOException {
	audioLine.drain();
    audioLine.close();
    audioStream.close();
}

}