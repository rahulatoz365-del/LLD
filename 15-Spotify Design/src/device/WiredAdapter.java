package device;
import externals.WiredAPI;
import models.Songs;

public class WiredAdapter implements IAudioOutputDevice{
    WiredAPI headphoneAPI;
    public WiredAdapter(WiredAPI headphoneAPI){
        this.headphoneAPI=headphoneAPI;
    }
    @Override
    public void playAudio(Songs song){
        String payload = song.getTitle()+"by"+song.getArtist();
        headphoneAPI.wiredSound(payload);
    }
}
