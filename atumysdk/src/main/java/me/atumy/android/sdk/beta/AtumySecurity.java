package me.atumy.android.sdk.beta;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ahmedwahdan on 6/15/15.
 */
public class AtumySecurity {


    private String HMAC(String Message , String Secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC  = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(Secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);


        return String.valueOf(Base64.encode(sha256_HMAC.doFinal(Message.getBytes()), Base64.NO_WRAP));
    }
}
