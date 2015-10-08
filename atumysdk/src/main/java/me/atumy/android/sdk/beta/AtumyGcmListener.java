package me.atumy.android.sdk.beta;

/**
 * Created by ahmedwahdan on 6/12/15.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;

public class AtumyGcmListener extends GcmListenerService {

    private static final String TAG = "AtumyGcmListener";
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("alert");
        //IPush pushData = new Push(data);
        AtumyMessageReceiver atumyMessageReceiver = PushManager.getInstance(this).getAtumyMessageReceiver();
        if (atumyMessageReceiver != null)
            atumyMessageReceiver.onMessageReceived(data);


        if (SharedData.getInstance(this).getPrefBoolean("DefaultNotification", true)) {
            sendNotification(message);
        }

    }
    // [END receive_message]

    private void sendNotification(String message) {
        Intent intent = new Intent();

            intent.setClassName(this, SharedData.getInstance(this).getPrefString("PushCallback","MainActivity"));

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, (new Random()).nextInt(150) + 1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Drawable icon = null;

        PackageManager pm = getPackageManager();
        String pkg = AtumyDeviceManager.getPackageName(this);
        int iconId = 0;
        String appName = "";
        try {
            ApplicationInfo ai = pm.getApplicationInfo(pkg, 0);
            int stringId = pm.getApplicationInfo(pkg, 0).labelRes;
           appName = this.getString(stringId);
            iconId = ai.icon;
        } catch (PackageManager.NameNotFoundException e) {
            // ...
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(SharedData.getInstance(this).getPrefInt("NotificationIcon", iconId))
                .setContentTitle(SharedData.getInstance(this).getPrefString("Title", appName))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(Uri.parse(SharedData.getInstance(this).getPrefString("Sound", defaultSoundUri.toString())

                ))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((new Random()).nextInt(150) + 1, notificationBuilder.build());
    }
}
