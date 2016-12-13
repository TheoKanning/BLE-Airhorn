package swag.theokanning.airhorn.bluetooth;

public interface AirhornConnectionListener {
    void onVolumeChanged(float volume);

    void onConnect();

    void onDisconnect();
}