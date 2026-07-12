package device;
import models.Songs;
import externals.BluetoothAPI;

public class BluetoothAdapter implements IAudioOutputDevice {
    BluetoothAPI bluetoothAPI;
    public BluetoothAdapter(BluetoothAPI bluetoothAPI){
        this.bluetoothAPI=bluetoothAPI;
    }
    @Override
    public void playAudio(Songs song){
        String payload = song.getTitle()+"by"+song.getArtist();
        bluetoothAPI.bluetoothSound(payload);
    }
}
