package example.codeclan.com.alforii;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] intentFiltersArray;
    String[][] techList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createAlarm = (Button)findViewById(R.id.newAlarm);
        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Alarm.class));
            }
        });


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "NFC is not enabled", Toast.LENGTH_LONG).show();
        }

        // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
        // will fill in the intent with the details of the discovered tag before delivering to
        // this activity.

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Setup an intent filter for all MIME based dispatches
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {
                ndef,
        };

        // Setup a tech list for all NfcF tags
        techList = new String[][] { new String[] { NfcF.class.getName() } };
    }

//    @Override
//    protected void onPause() {
//        nfcAdapter.disableForegroundDispatch(this);
//        super.onPause();
//    }
//
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
//    }
//
//
//
//    @Override
//    public void onNewIntent(Intent intent) {
//        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
//
//
//    }

}
