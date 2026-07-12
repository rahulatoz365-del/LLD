package factory;
import device.*;
import externals.*;
import enums.DeviceType;


public class DeviceFactory {
    public static IAudioOutputDevice createDevice(DeviceType deviceType) {
        switch (deviceType) {
            case BLUETOOTH:
                return new BluetoothAdapter(new BluetoothAPI());
            case WIRED:
                return new WiredAdapter(new WiredAPI());
            case SPEAKER:
            default:
                return new SpeakerAdapter(new SpeakerAPI());
        }
    }
}