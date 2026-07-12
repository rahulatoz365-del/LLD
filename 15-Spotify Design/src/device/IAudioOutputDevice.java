package device;
import models.Songs;

public interface IAudioOutputDevice {
    void playAudio(Songs song);
}
