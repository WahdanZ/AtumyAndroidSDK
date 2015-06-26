package me.atumy.android.sdk.beta;

import org.json.JSONObject;

/**
 * The interface Atumy callback.
 */
@SuppressWarnings("ALL")
public abstract interface AtumyCallback {
    /**
     * On receive.
     *
     * @param value the value
     */
    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public abstract void onReceive(JSONObject value);

}
