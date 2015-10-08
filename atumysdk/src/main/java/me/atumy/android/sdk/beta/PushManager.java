package me.atumy.android.sdk.beta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * The type Push manager.
 */
public class PushManager {
    private static final String TAG = "PushManager";
    private static final String Push = "";
    private static PushManager pushManager;
    private static Context mContext;
    private String mSenderId;
    private String atumyAppToken;
    private AtumyMessageReceiver atumyMessageReceiver;





    private PushManager (){

    }

    private PushManager(Context context) {
        mContext = context.getApplicationContext();
        VolleyLog.DEBUG = false;
        if (!context.getClass().getName().equals("me.atumy.android.sdk.beta.AtumyGcmListener")) {
            getmSenderId();
            Log.d("PushManager", "not GCM");
        }
    }

    protected static PushManager getInstance(){
        pushManager = new PushManager();
        VolleyLog.DEBUG = false;

        return pushManager;
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static PushManager getInstance(Context context)
    {
        if (pushManager == null)
        {
            pushManager = new PushManager(context);
        }
        return pushManager;
    }

    /**
     * Atumy register app.
     */
    public static   void AtumyRegisterApp() {

        GCMChecker.checkDevice(mContext);
        GCMChecker.checkManifest(mContext);

        boolean checkPlayServices =    GCMChecker.checkPlayServices(mContext);

        if (checkPlayServices) {
//           Toast.makeText(mContext, "AtumyRegisterApp",Toast.LENGTH_LONG).show();

            // Start IntentService to register this application with GCM.
            Log.d("PushManager", "CallGCM");
             Intent intent = new Intent(mContext, AtumyGCMRegister.class);
             mContext.startService(intent);
        }



    }


    /**
     * Is push enabled.
     *
     * @return the boolean
     */
    public boolean isPushEnabled()
    {
        return SharedData.getInstance(this.mContext).getPrefBoolean(
                "DefaultNotification", true);
    }

    /**
     * Sets push enabled.
     *
     * @param enabled the enabled
     */
    void setPushEnabled(boolean enabled) {
        SharedData.getInstance().setPrefBoolean("enable_push",
                enabled);
    }

    /**
     * Gets sender id.
     *
     * @return the sender id
     */
  private   String getmSenderId() {
      final String[] senderId = new String[1];
      RequestQueue queue = Volley.newRequestQueue(mContext);
      AtumyVolleyRequest request = new AtumyVolleyRequest(Request.Method.GET, "push/config", null, new Response.Listener<String>() {
          @Override
          public void onResponse(String s) {
              try {
                  senderId[0] = new JSONObject(s).getString("senderId");
                  Log.d("PushManager", senderId[0]);
                  SharedData.getInstance(mContext).setPrefString("senderId",senderId[0]);
                  //noinspection AccessStaticViaInstance
                  AtumyRegisterApp();
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {


          }
      });

      queue.add(request);
      return senderId[0];
  }

    /**
     * Gets atumy app token.
     *
     * @return the atumy app token
     */


    private void setAtumyAppToken(String atumyAppToken) {
        this.atumyAppToken = atumyAppToken;
    }


    /**
     * Sets default notification.
     *
     * @param enabled the enabled
     */
    public void setDefaultNotification(boolean enabled)
    {
        SharedData.getInstance(mContext).setPrefBoolean(
                "DefaultNotification", enabled);
    }

    /**
     * Sets notification icon.
     *
     * @param iconResource the icon resource
     */
    public void setNotificationIcon(int iconResource) {
        SharedData.getInstance(mContext).setPrefInt("NotificationIcon",
                iconResource);
    }

    /**
     * Sets push callback.
     *
     * @param intentName the intent name
     */
    public void setPushCallback(Class<? extends Activity> intentName)
    {
        SharedData.getInstance(mContext).setPrefString("PushCallback", intentName.getName());
    }

    /**
     * Sets title.
     *
     * @param Title the title
     */
    public void setTitle(String Title)
    {
        SharedData.getInstance(mContext).setPrefString("Title", Title);
    }

    /**
     * Is vibration enabled.
     *
     * @return the boolean
     */
    public boolean isVibrationEnabled()
    {
        return SharedData.getInstance(mContext).getPrefBoolean(
                "vibrationEnabled", true);
    }


    /**
     * Message receiver.
     *
     * @param atumyMessageReceiver the atumy message receiver
     */
    public  void MessageReceiver(AtumyMessageReceiver atumyMessageReceiver){
        this.atumyMessageReceiver = atumyMessageReceiver;
    }

    /**
     * Get atumy message receiver.
     *
     * @return the atumy message receiver
     */
    protected AtumyMessageReceiver getAtumyMessageReceiver(){
        return this.atumyMessageReceiver;
    }
}
