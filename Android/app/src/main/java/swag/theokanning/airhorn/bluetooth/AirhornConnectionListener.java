package swag.theokanning.airhorn.bluetooth;

public interface AirhornConnectionListener {
    void onVolumeChanged(byte volume);

    void onConnect();

    void onDisconnect();
}