package me.atumy.android.sdk.beta;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;


/**
 * The type Atumy client.
 */
public class AtumyClient {
	private static String appId ="";
	private static String appSecret ="";


	/**
	 * Get auth header.
	 *
	 * @param url the url
	 * @param data the data
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
     */
	public static String GetAuthHeader(String url, String data) throws UnsupportedEncodingException {

        String requestContentBase64String = "";
		String requestHttpMethod = "GET";

		// Unix Time
		long unixTime = System.currentTimeMillis() / 1000L;
		String requestTimeStamp = String.valueOf(unixTime);

		Log.i("HMAC", requestTimeStamp);

		// create random nonce for each request
		String nonce = UUID.randomUUID().toString().replace("-","");

		Log.i("HMAC", nonce);

		// Creating the raw signature string
		String signatureRawData = String.format("%s\n%s\n%s\n%s\n%s\n%s",
				appId, requestHttpMethod,Uri.encode(url.toLowerCase()).toLowerCase(),
				requestTimeStamp, nonce, requestContentBase64String);

		Log.d("SIG DATA",signatureRawData);
		
		byte[] secretKeyByteArray = appSecret.getBytes("UTF-8");
		byte[] signature = signatureRawData.getBytes("UTF-8");

		Mac mac;

		try {
			mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret = new SecretKeySpec(secretKeyByteArray,
					mac.getAlgorithm());
			mac.init(secret);
			byte[] digest = mac.doFinal(signature);


			String requestSignatureBase64String = Base64.encodeToString(digest,
					Base64.NO_WRAP);

			Log.d("BASE64", new String(requestSignatureBase64String));

            return "atumy "
                    + String.format("%s:%s:%s:%s", appId,
                            requestSignatureBase64String, nonce,
                            requestTimeStamp);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Gets auth header.
	 *
	 * @param reqMethod the req method
	 * @param url the url
	 * @param data the data
	 * @return the auth header
	 * @throws UnsupportedEncodingException the unsupported encoding exception
     */
	public static String getAuthHeader(int reqMethod ,String url,String data) throws UnsupportedEncodingException {
		appId = Atumy.getAppID();
		appSecret = Atumy.getAppSecret();
		String requestContentBase64String = "";
		String requestHttpMethod = "GET" ;
		switch (reqMethod) {
		case 0:
			 requestHttpMethod = "GET";
			break;
		case 1:
			 requestHttpMethod = "POST";
			break;
		case 2:
			 requestHttpMethod = "PUT";
			break;
		case 3:
			 requestHttpMethod = "DELETE";
			break;
		default:
			 requestHttpMethod = "GET";

			break;
		}

		// Unix Time
		long unixTime = System.currentTimeMillis() / 1000L;
		String requestTimeStamp = String.valueOf(unixTime);


		// create random nonce for each request
		String nonce = UUID.randomUUID().toString().replace("-","");

		byte[] secretKeyByteArray = appSecret.getBytes("UTF-8");
		
		
		
		if(data != null){
			byte[] content = data.getBytes();
			MessageDigest digest=null;
			try {
				 digest = MessageDigest.getInstance("SHA-256");
			        digest.update(content);
				
				 requestContentBase64String = Base64.encodeToString(digest.digest(),
						Base64.NO_WRAP);
				 

			}catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		
		// Creating the raw signature string
		String signatureRawData = String.format("%s\n%s\n%s\n%s\n%s\n%s",
				appId, requestHttpMethod,Uri.encode(url.toLowerCase()).toLowerCase(),
				requestTimeStamp, nonce, requestContentBase64String);


		
		byte[] signature = signatureRawData.getBytes("UTF-8");

		Mac mac;

		try {
			mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret = new SecretKeySpec(secretKeyByteArray,
					mac.getAlgorithm());
			mac.init(secret);
			byte[] digest = mac.doFinal(signature);


			String requestSignatureBase64String = Base64.encodeToString(digest,
					Base64.NO_WRAP);


            return "atumy "
                    + String.format("%s:%s:%s:%s", appId,
                            requestSignatureBase64String, nonce,
                            requestTimeStamp);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}


}