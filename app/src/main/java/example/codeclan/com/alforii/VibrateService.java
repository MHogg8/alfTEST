package example.codeclan.com.alforii;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class VibrateService extends Service {
    Vibrator vibrateCall;

//////////////////////////////////////////////////////////////////////////////
    //WHAT IS A SERVICE?
    //allows me tell the system about something
    //I want to be doing in the background
    //(even when user is not working with app). through onStartCommand

//////////////////////////////////////////////////////////////////////////////
    //SERVICE ALSO ALLOWS:
    //a long-standing connection to be made
    //to the service in order to interact with it. through onBind
//////////////////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//////////////////////////////////////////////////////////////////////////////
       //When The Service starts up it is going to do the following:
//////////////////////////////////////////////////////////////////////////////
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("StartMEUP", "Received start id " + startId + ": " + intent);

//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
                        //Make the phone Vibrate.
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

        //Creates an instance of teh vibrate service
        //Permission must be added to the Manifest
        vibrateCall = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        // Creates a pattern for the vibrator
        // first number is the delay length(0)
        // followed by the pattern
        long[] pattern = {0, 1000, 100, 1000, 100, 1000, 100, 1000};
        // finally calls the vibrate service
        vibrateCall.vibrate(pattern, 0);


//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
                        //Makes a Tone Beep.
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

        //This sets of a defined tone, only one beep on emulator but
        //Is meant to be, and is, a continuous beep on the phone.
        //100 is the volume.
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        //starts the tone
        toneG.startTone(ToneGenerator.TONE_DTMF_0, 30000);

//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
            //Launches the alarm activity that shows the clock
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

        Intent intentImage = new Intent(VibrateService.this, ShowImage.class);

        //without the below line it crashes; because generally activities can only start others.
        intentImage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentImage);


        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "Destroyed", Toast.LENGTH_SHORT).show();
        // USe the getExtras and if else to kill the vibrate service
    }

}
