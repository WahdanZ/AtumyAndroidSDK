package me.atumy.android.sdk.beta;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

/**
 * The type Atumy volley request.
 */
class AtumyVolleyRequest  extends StringRequest {
    /**
     * The constant url.
     */
    public static String url = "https://atumy.me/api/";
    //private  Listener<String> listener;
    private String Body = "";
private    String sig = null;


    /**
     * Instantiates a new Atumy volley request.
     *
     * @param method the method
     * @param suburl the suburl
     * @param body the body
     * @param reponseListener the reponse listener
     * @param errorListener the error listener
     */
    public AtumyVolleyRequest(int method, String suburl,String body,
                         Listener<String> reponseListener, ErrorListener errorListener) {
        super(method, url+suburl, reponseListener, errorListener);
        //this.listener = reponseListener;
        VolleyLog.DEBUG  = false;

        try {
			sig = AtumyClient.getAuthHeader(method, url+suburl,body );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.Body = body;
    }
    @Override
	public String getBodyContentType() {
		// TODO Auto-generated method stub
		return "application/json";
	}

   @Override public byte[] getBody() throws com.android.volley.AuthFailureError {
	   return Body.getBytes();
   };
@Override public Map<String,String> getHeaders() throws com.android.volley.AuthFailureError {
	HashMap<String, String> headers = new HashMap<>();

	headers.put("Authorization",sig );
	return headers;
	
};
/*
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new String(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } 
    }

    @Override
    protected void deliverResponse(String response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }*/
}