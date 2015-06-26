package me.atumy.android.sdk.beta;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.util.Log;

/**
 * The type KV store.
 *
 * @param <T> the type parameter
 */
public class KVStore<T> {
    private static RequestQueue queue;
    /**
     * The Request.
     */
    AtumyVolleyRequest request;
    /**
     * The Request state.
     */
    Boolean RequestState;
    /**
     * The Collection.
     */
    String collection;
    /**
     * The Value.
     */
    JSONObject value;
    private AtumyCallback callBack;
    private String sig;
    private static final String kvStore = "kvstore/";

    private KVStore(Context context, String collection) {
        queue = Volley.newRequestQueue(context);
        this.collection = collection;
        KVStore mkvStore = this;

    }

    /**
     * Init kV store.
     *
     * @param context    the context
     * @param collection the collection
     * @return the kV store
     */
    public static KVStore init(Context context, String collection) {
        return new KVStore(context, collection);
    }

    /**
     * Put void.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, HashMap<String, T> value) {
        JSONObject val = new JSONObject(value);
        request = new AtumyVolleyRequest(Request.Method.POST, kvStore + collection + "/" + key, val.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());

            }
        });
        queue.add(request);


    }

    /**
     * Put void.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, JSONObject value) {
        request = new AtumyVolleyRequest(Request.Method.POST, kvStore + collection + "/" + key, value.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());

            }
        });
        queue.add(request);


    }

    /**
     * Put void.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, HashMap<String, T> value, final AtumyCallback response, final AtumyErrorCallback error) {
        JSONObject val = new JSONObject(value);
        request = new AtumyVolleyRequest(Request.Method.POST, kvStore + collection + "/" + key, val.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);
                try {
                    response.onReceive(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());
                error.onError(volleyError.getMessage());


            }
        });
        queue.add(request);


    }

    /**
     * Put void.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, JSONObject value, final AtumyCallback response, final AtumyErrorCallback error) {

        request = new AtumyVolleyRequest(Request.Method.POST, kvStore + collection + "/" + key, value.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);
                try {
                    response.onReceive(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());
                error.onError(volleyError.getMessage());


            }
        });
        queue.add(request);


    }

    /**
     * Get void.
     *
     * @param key      the key
     * @param response the response
     * @param error    the error
     */
    public void get(String key, final AtumyCallback response, final AtumyErrorCallback error) {
        request = new AtumyVolleyRequest(Request.Method.GET, kvStore + collection + "/" + key, null, new Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    response.onReceive(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                error.onError(volleyError.getMessage());
            }
        });
        queue.add(request);

    }

    /**
     * Delete void.
     *
     * @param key the key
     */
    public void delete(final String key) {
        request = new AtumyVolleyRequest(Request.Method.DELETE, kvStore + collection + "/" + key, null, new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", "key " + key + "delete");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());

            }
        });
        queue.add(request);


    }

    public void delete(final String key, final AtumyCallback response, final AtumyErrorCallback error) {
        request = new AtumyVolleyRequest(Request.Method.DELETE, kvStore + collection + "/" + key, null, new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", "key " + key + "delete");
                try {
                    response.onReceive(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());
                error.onError(volleyError.getMessage());

            }
        });
        queue.add(request);


    }

    public void update(String key, HashMap<String, T> value, final AtumyCallback response, final AtumyErrorCallback error) {
        JSONObject val = new JSONObject(value);

        request = new AtumyVolleyRequest(Request.Method.PUT, kvStore + collection + "/" + key, val.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);
                try {
                    response.onReceive(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());
                error.onError(volleyError.getMessage());


            }
        });
        queue.add(request);


    }

    public void update(String key, JSONObject value, final AtumyCallback response, final AtumyErrorCallback error) {

        request = new AtumyVolleyRequest(Request.Method.PUT, kvStore + collection + "/" + key, value.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);
                try {
                    response.onReceive(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());
                error.onError(volleyError.getMessage());


            }
        });
        queue.add(request);


    }

    /**
     * Update void.
     *
     * @param key   the key
     * @param value the value
     */
    public void update(String key, HashMap<String, T> value) {
        JSONObject val = new JSONObject(value);

        request = new AtumyVolleyRequest(Request.Method.PUT, kvStore + collection + "/" + key, val.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());

            }
        });
        queue.add(request);


    }
    public void update(String key, JSONObject value) {


        request = new AtumyVolleyRequest(Request.Method.PUT, kvStore + collection + "/" + key, value.toString(), new Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("KvStore", s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("KvStore", volleyError.getMessage());

            }
        });
        queue.add(request);


    }


}
