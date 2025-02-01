import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip sound(String path) {
        Clip clip = null;
        AudioInputStream ais = null;

        try {
            File arquivo = new File(path);
            if (!arquivo.exists()) {
                System.out.println("Aquivo de som não encontrado: " + path);
                return null;
            }
            ais = AudioSystem.getAudioInputStream(arquivo);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Erro ao carregar o som: " + e.getMessage());
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
        return clip;
    }

    static void shoot() {
        Clip som = Sound.sound(Sound.class.getResource("res/sounds/shoot.wav").getFile());
        if (som != null){
            som.start();
        }
    }

    static void hit() {
        Clip som = Sound.sound(Sound.class.getResource("res/sounds/hit.wav").getFile());
        if (som != null){
            som.start();
        }
    }

    static void explosion() {
        Clip som = Sound.sound(Sound.class.getResource("res/sounds/explosion.wav").getFile());
        if (som != null){
            som.start();
        }
    }

    static void hurt() {
        Clip som = Sound.sound(Sound.class.getResource("res/sounds/hurt.wav").getFile());
        if (som != null){
            som.start();
        }
    }
}
