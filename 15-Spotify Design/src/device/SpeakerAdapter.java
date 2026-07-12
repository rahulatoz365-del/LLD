package device;

import externals.SpeakerAPI;
import models.Songs;

public class SpeakerAdapter implements IAudioOutputDevice{
    SpeakerAPI speakerAPI;
    public SpeakerAdapter(SpeakerAPI speakerAPI){
        this.speakerAPI=speakerAPI;
    }
    @Override
    public void playAudio(Songs song){
        String payload = song.getTitle()+"by"+song.getArtist();
        speakerAPI.speakerSound(payload);
    }
}
