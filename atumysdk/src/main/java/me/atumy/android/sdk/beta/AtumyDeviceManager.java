package me.atumy.android.sdk.beta;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.Locale;


/**
 * The type Atumy device manager.
 */
public class AtumyDeviceManager {


    /**
     * Gets oS version.
     *
     * @return the oS version
     */
    static int getOSVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Gets app version.
     *
     * @param context the context
     * @return the app version
     */
    static String getAppVersion(Context context)
    {
        try
        {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }return "";
    }

    /**
     * Gets iMEI.
     *
     * @param context the context
     * @return the iMEI
     */
    static String getIMEI(Context context)
    {
        try {
            if (hasReadPhoneStatePermission(context)) {
                TelephonyManager tm = (TelephonyManager)context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = tm.getDeviceId();
                if ((deviceId != null) && (deviceId.length() != 0)) {
                    return deviceId;
                }
                return null;
            }

            return null;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Gets device type.
     *
     * @return the device type
     */
    static String getDeviceType()
    {
        return Build.MANUFACTURER + " " + Build.DEVICE;
    }

    /**
     * Gets android id.
     *
     * @param context the context
     * @return the android id
     */
    static String getAndroidId(Context context)
    {
        try {
            String androidId = Settings.Secure.getString(
                    context.getContentResolver(), "android_id");
            if (androidId != null)
                return androidId;
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Has read phone state permission.
     *
     * @param context the context
     * @return the boolean
     */
    static boolean hasReadPhoneStatePermission(Context context) {
        String permission = "android.permission.READ_PHONE_STATE";
        int res = context.checkCallingOrSelfPermission(permission);
        return res == 0;
    }

    /**
     * Gets email.
     *
     * @param context the context
     * @return the email
     */
    static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    /**
     * Get country.
     *
     * @param context the context
     * @return the string
     */
    static String getCountry(Context context){
        return context.getResources().getConfiguration().locale.getDisplayCountry();

    }

    /**
     * Gets device language.
     *
     * @param context the context
     * @return the device language
     */
    static String getDeviceLanguage(Context context)
    {
       return Locale.getDefault().getDisplayLanguage();

    }

    /**
     * Get iSO country.
     *
     * @param context the context
     * @return the string
     */
    static String getISOCountry(Context context){
    return Locale.getDefault().getISO3Country();

    }

    /**
     * Get package name.
     *
     * @param context the context
     * @return the string
     */
    static  String getPackageName(Context context){
        return  context.getPackageName();
    };


}
