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

    public void shoot() {
        Clip som = Sound.sound(Sound.class.getResource("sounds/shoot.wav").getFile());
        if (som != null){
            som.start();
        }
    }

    public void hit() {
        Clip som = Sound.sound(Sound.class.getResource("sounds/hit.wav").getFile());
        if (som != null){
            som.start();
        }
    }

    public void explosion() {
        Clip som = Sound.sound(Sound.class.getResource("sounds/explosion.wav").getFile());
        if (som != null){
            som.start();
        }
    }

    public void hurt() {
        Clip som = Sound.sound(Sound.class.getResource("sounds/hurt.wav").getFile());
        if (som != null){
            som.start();
        }
    }
}
