package swag.theokanning.airhorn.bluetooth;

import swag.theokanning.airhorn.model.AirhornCommand;

public interface AirhornConnectionListener {
    void onVolumeChanged(AirhornCommand command);

    void onConnect();

    void onDisconnect();
}