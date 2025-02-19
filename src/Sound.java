import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip shootClip;
    private static Clip hitClip;
    private static Clip explosionClip;
    private static Clip hurtClip;

    static {
        shootClip = sound(Sound.class.getResource("sounds/shoot.wav").getFile());
        hitClip = sound(Sound.class.getResource("sounds/hit.wav").getFile());
        explosionClip = sound(Sound.class.getResource("sounds/explosion.wav").getFile());
        hurtClip = sound(Sound.class.getResource("sounds/hurt.wav").getFile());

        setVolume(shootClip, -10.0f);
        setVolume(hitClip, -10.0f);
        setVolume(explosionClip, -10.0f);
        setVolume(hurtClip, -10.0f);
    }

    private static void setVolume(Clip clip, float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

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
        if (shootClip != null) {
            shootClip.setFramePosition(0);
            shootClip.start();
        }
    }

    public void hit() {
        if (hitClip != null) {
            hitClip.setFramePosition(0);
            hitClip.start();
        }
    }

    public void explosion() {
        if (explosionClip != null) {
            explosionClip.setFramePosition(0);
            explosionClip.start();
        }
    }

    public void hurt() {
        if (hurtClip != null) {
            hurtClip.setFramePosition(0);
            hurtClip.start();
        }
    }
}
