package me.atumy.android.sdk.beta;

import android.content.Context;

import com.android.volley.VolleyLog;


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
            VolleyLog.DEBUG = false;
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

    private void AtumyRegisterApp()
    {


    }
}
