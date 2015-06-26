package me.atumy.android.sdk.beta;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ahmedwahdan on 6/10/15.
 */
public class GCMChecker  {
    private static final String TAG ="GCMChecker";

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     * @param context the context
     * @return the boolean
     */
    public static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context, resultCode);
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Check manifest.
     *
     * @param context the context
     */
    public static void checkManifest(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        String permissionName = packageName + ".permission.C2D_MESSAGE";
        try {
            packageManager.getPermissionInfo(permissionName, 4096);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(
                    "Application does not define permission " + permissionName);
        }
        PackageInfo receiversInfo;

        try
        {
            receiversInfo = packageManager.getPackageInfo(packageName, 2);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new IllegalStateException(
                    "Could not get receivers for package " + packageName);
        }
        ActivityInfo[] receivers = receiversInfo.receivers;
        if ((receivers == null) || (receivers.length == 0)) {
            throw new IllegalStateException("No receiver for package " +
                    packageName);
        }

        if (Log.isLoggable("GCMRegistrar", 2)) {
            Log.v(TAG, "number of receivers for " + packageName +
                    ": " + receivers.length);
        }

        Set allowedReceivers = new HashSet();
        for (ActivityInfo receiver : receivers)
        {
            if ("com.google.android.c2dm.permission.SEND"
                    .equals(receiver.permission)) {
                //noinspection unchecked
                allowedReceivers.add(receiver.name);
            }
        }
        if (allowedReceivers.isEmpty()) {
            throw new IllegalStateException(
                    "No receiver allowed to receive com.google.android.c2dm.permission.SEND");
        }
    }

    /**
     * Check device.
     *
     * @param context the context
     */
    public static void checkDevice(Context context)
    {
        int version = Build.VERSION.SDK_INT;
        if (version < 8) {
            throw new UnsupportedOperationException(
                    "Device must be at least API Level 8 (instead of " +
                            version + ")");
        }

        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("com.google.android.gsf", 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new UnsupportedOperationException(
                    "Device does not have package com.google.android.gsf");
        }
    }

    }


