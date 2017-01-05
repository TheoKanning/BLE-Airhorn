package swag.theokanning.airhorn.bluetooth;

public interface AirhornConnectionListener {
    void onSwagCountChanged(int swagCount);

    void onConnect();

    void onDisconnect();
}