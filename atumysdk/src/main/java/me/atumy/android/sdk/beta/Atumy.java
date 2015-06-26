package me.atumy.android.sdk.beta;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * The type Atumy.
 */
public class Atumy {
    private static Atumy mAtumy;
    private static String mAppId;
    private static String mAppSecret;
    private static Context mContext;
    private static String SenderId;

    private Atumy(Context context)
    {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
    }

    /**
     * Init void.
     *
     * @param context the context
     * @param appId the app id
     * @param appSecret the app secret
     */
    public static void init(Context context, String appId , String appSecret)
    {

        Atumy atumy = getInstance(context);
        if (appId == null) {
            throw new RuntimeException("AppId not defined. You must provide ApplicationId ");
        }

        if (appId == null) {
            throw new RuntimeException("AppSecret not defined. You must provide AppSecret ");
        }

        mAppId = appId;
        mAppSecret = appSecret;
       SharedData.getInstance(context).setPrefString("AppId",appId);
       SharedData.getInstance(context).setPrefString("AppSecret",appSecret);


       atumy.AtumyRegisterApp();

    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static Atumy getInstance(Context context)
    {
        if(mAtumy == null)
            mAtumy = new Atumy(context);
        return mAtumy;
    }


    /**
     * Gets app iD.
     *
     * @return the app iD
     */
    protected static String getAppID()
    {
        return mAppId;
    }

    /**
     * Gets app secret.
     *
     * @return the app secret
     */
    protected static String getAppSecret()
    {
       return mAppSecret;
    }

    /**
     * Enable push.
     */
    public static void EnablePush()
    {
        boolean enable = SharedData.getInstance(mContext).getPrefBoolean("enable_push", true);
    }

    private void AtumyRegisterApp()
    {


    }
}
