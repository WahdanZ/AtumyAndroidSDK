package sdk.atumy.me.api.atumy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import me.atumy.android.sdk.beta.Atumy;
import me.atumy.android.sdk.beta.AtumyCallback;
import me.atumy.android.sdk.beta.AtumyErrorCallback;
import me.atumy.android.sdk.beta.AtumyGcmListener;
import me.atumy.android.sdk.beta.AtumyMessageReceiver;
import me.atumy.android.sdk.beta.KVStore;
import me.atumy.android.sdk.beta.PushManager;


public class MainActivity extends ActionBarActivity {
    EditText AppId ,AppSecret;

    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppId = (EditText)findViewById(R.id.AppId);
        AppSecret = (EditText)findViewById(R.id.AppSecret);
        Atumy.init(this, "b210b522fb0c4154bd476d804680dad4", "0513eacafc7049439948b534058ef412");
        PushManager pushManager = PushManager.getInstance(this);
        pushManager.MessageReceiver(new AtumyMessageReceiver() {


            @Override
            public void onMessageReceived(String Message) {

            }
        });

        KVStore kvStore = KVStore.init(this,"MyAtumy");
        kvStore.get("Ahmed", new AtumyCallback() {
            @Override
            public void onReceive(JSONObject value) {

            }
        }, new AtumyErrorCallback() {
            @Override
            public void onError(String error) {

            }
        });

        kvStore.get("Ahmed", new AtumyCallback() {
            @Override
            public void onReceive(JSONObject value) {

            }
        }, new AtumyErrorCallback() {
            @Override
            public void onError(String error) {

            }
        });
    }
public  void OnRegister(View view){
try {


    Atumy.init(this, AppId.getText().toString(), AppSecret.getText().toString());
    // PushManager.init(this, "128368061951", "121212");

 }
    catch (Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
    }
}
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.powered_by_google_dark)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
