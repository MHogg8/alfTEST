package example.codeclan.com.alforii;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
//  BroadcastReceiver: register for system or app events
//  Once event occurs system notify all register Receivers
///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Alarm Receiver ", "Receiving: ");

        Intent vibrateService = new Intent(context, VibrateService.class);
        context.startService(vibrateService);
        Log.e("Cunt", "Cunt");
    }
}
