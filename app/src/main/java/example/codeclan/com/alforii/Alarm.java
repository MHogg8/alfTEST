package example.codeclan.com.alforii;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//this has to be added to the dependencies.
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    PendingIntent pendingIntent;
    PendingIntent pendingWatchIntent;
    AlarmManager alarmManager;
    TimePicker timePicker;
    Calendar calendar;
    Context context;
    TextView reasonText;
    AlarmClock alarmClock;
    NfcAdapter nfcAdapter;
    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context = this;


///////////////////////////////////////////////////////////////////
//THIS ALLOWS ME TO TALK COMMUNICATE WITH THE WEARABLE DEVICE/////
///////////////////////////////////////////////////////////////////
        apiClient = new GoogleApiClient.Builder( this )
                    .addApi( Wearable.API )
                    .build();

        apiClient.connect();
        Log.e("LOGGGING", "YEAHAHAHAHAHAH");

////////////////////////////////////////////////////////////////////
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar  = Calendar.getInstance();
        reasonText = (TextView) findViewById(R.id.reasonText);

//////////////////////////////////////////////////////////////////////////////
        //known as an explicit intent, it connects activities together
        //The intent takes two arguments(where it is and,  where its going)
        final Intent sender = new Intent(Alarm.this, AlarmReceiver.class);
/////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////
       // Time FOR A MEGA ONCLICK FUNCTION TO SET THE ALARM
/////////////////////////////////////////////////////////////////////////////

        Button setAlarm = (Button) findViewById(R.id.setAlarm);


        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                //will need to get abd send the day too as this will be needed at the other end


                int hourTime = timePicker.getCurrentHour();
                int minTime = timePicker.getCurrentMinute();

                String hour = String.valueOf(hourTime);
                String min = String.valueOf(minTime);
                String reason = (String) reasonText.getText().toString();

                alarmClock = new AlarmClock(hourTime, minTime, reason);
                alarmClock.save();

                if(minTime < 10) {
                    min = "0" + min;
                }

///////////////////////////////////////////////////////////////////////
                //This will be in key-value pairs for additional
                // information that should be delivered to the
                // component handling the intent: Alarm Receiver
                // The extras can be set and read using the putExtras()
                // and getExtras() methods. Can use this to kill vibrator


                sender.putExtra("alarm on", true);
///////////////////////////////////////////////////////////////////////

                Toast.makeText(Alarm.this, "Alarm Time: " + hour +  ":"+ min + "\nAlarm Reason: " + reason,
                        Toast.LENGTH_LONG).show();


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    // The pending intent
                //An Object that wraps another intent allows us to do something in the future
                //Creates a token of data here that can be used in this application or other
                //get Broadcast sens it to the place defined in our intent (sender, AlarmReceiver)
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, sender, PendingIntent.FLAG_UPDATE_CURRENT);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    // Set the alarm
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                // RtcWakeup lets the intent wakeup the app if sleeping, gets the time, then intent
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                //probably want to change this to AlarmManager.AlarmClockInfo which will take an intent
                //which can be used to show and edit details of the alarm clock
                //it also takes the time in milliseconds, but not sure it wakes the system up - will check.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                     // Fire an intent to the Watch????
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//                Intent watchIntent = new Intent(Alarm.this, WearActionReceiver.class);
//                pendingWatchIntent = PendingIntent.getBroadcast(Alarm.this, 0, watchIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    // Make the alarm reoccurring
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



                // With setInexactRepeating(), you have to use one of the AlarmManager interval
                // constants--in this case, AlarmManager.INTERVAL_DAY.
                //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                //AlarmManager.INTERVAL_DAY, pendingIntent);
            }


        });

        Button cancelAlarm = (Button) findViewById(R.id.cancelAlarm);

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(Alarm.this, "Alarm Canceled", Toast.LENGTH_LONG).show();
                reasonText.setText("");
                timePicker.setCurrentHour(12);
                timePicker.setCurrentMinute(00);
                sender.putExtra("Alarm Off", false);


            }

        });



    }


}
