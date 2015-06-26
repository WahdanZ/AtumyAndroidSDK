package me.atumy.android.sdk.beta;
// me.atumy.sdk
import android.os.Bundle;

/**
 * Created by ahmedwahdan on 6/14/15.
 */
public abstract interface AtumyMessageReceiver {
    /**
     * On message received.
     *
     * @param Message push from server
     */
    public abstract void onMessageReceived(String Message);
}
