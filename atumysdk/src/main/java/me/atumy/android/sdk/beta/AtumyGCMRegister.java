package me.atumy.android.sdk.beta;

/**
 * Created by ahmedwahdan on 6/11/15.
 */

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AtumyGCMRegister extends IntentService {
    Context mContext;
    private  static final String push = "push/register?deviceId=";
    private static final String TAG = "RegIntentService";
    String GCMSenderID;
    public AtumyGCMRegister() {
        super(TAG);
         mContext = this;
        Log.i(TAG, "Contractor");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
      //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                GCMSenderID = SharedData.getInstance(this).getPrefString(
                        "senderId", "");
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(GCMSenderID,
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]

                sendRegistrationToServer(token);


                // You should store a boolean that indicates whether the generated token has been
                // sent to your server. If the boolean is false, send the token to your server,
                // otherwise your server should have already received the token.
                SharedData.getInstance().setPrefBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true);
                // [END register_for_gcm]
            }
        } catch (Exception e) {
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            SharedData.getInstance().setPrefBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) throws AuthFailureError {
      ;

        Map<String,String> params = new HashMap<>();
        params.put("AndroidId", AtumyDeviceManager.getAndroidId(mContext));
        params.put("AppVersion","Android "+ AtumyDeviceManager.getAppVersion(mContext));
        params.put("Country", AtumyDeviceManager.getCountry(mContext));
        params.put("OSVersion", "" + AtumyDeviceManager.getOSVersion());
        params.put("Email", AtumyDeviceManager.getEmail(mContext));
//        Log.d("IMEI",AtumyDeviceManager.getIMEI(context));
        params.put("DeviceType", AtumyDeviceManager.getDeviceType());
        params.put("DeviceLanguage",AtumyDeviceManager.getDeviceLanguage(mContext));
        params.put("ISOCountry",AtumyDeviceManager.getISOCountry(mContext));
        params.put("PackageName",AtumyDeviceManager.getPackageName(mContext));
        Map<String,String> data = new Hashtable<>();
        //data.put("data",new JSONObject(params).toString());
        RequestQueue queue = Volley.newRequestQueue(this);
        AtumyVolleyRequest request = new AtumyVolleyRequest(Request.Method.POST, push+token,new JSONObject(params).toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
             Log.d("Reg",s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error",volleyError.toString());

            }
        });

        queue.add(request);
      //  CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, uri, params, this.createRequestSuccessListener(), this.createRequestErrorListener());
    }


}
