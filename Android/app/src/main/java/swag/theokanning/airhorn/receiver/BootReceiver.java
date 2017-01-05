package swag.theokanning.airhorn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import swag.theokanning.airhorn.service.AirhornService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AirhornService.class));
    }
}
