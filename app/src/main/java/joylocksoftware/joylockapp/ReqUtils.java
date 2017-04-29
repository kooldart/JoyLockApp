package joylocksoftware.joylockapp;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Map;

public class ReqUtils
{
    public static void jsonRequest(Context act, int reqType, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        JsonObjectRequest request = new JsonObjectRequest(reqType, url, null, listener, errorListener);
        MySingleton.getInstance(act).addToRequestQueue(request);
    }

    public static void jsonRequestWithHeaders(Context act, int reqType, String url, final Map<String, String> header,
                                              Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        JsonObjectRequest request = new JsonObjectRequest(reqType, url, null, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                return header;
            }
        };
        MySingleton.getInstance(act).addToRequestQueue(request);
    }

    public static void stringRequestWithHeaders(Activity act, int reqType, String url, Response.Listener<String> listener,
                                                Response.ErrorListener errorListener, final Map<String, String> headers)
    {
        StringRequest request = new StringRequest(reqType, url, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                return headers;
            }
        };
        MySingleton.getInstance(act).addToRequestQueue(request);
    }

    public static void stringRequestWithParams(Activity act, int reqType, String url, Response.Listener<String> listener,
                                               Response.ErrorListener errorListener)
    {
        StringRequest request = new StringRequest(reqType, url, listener, errorListener);
        MySingleton.getInstance(act).addToRequestQueue(request);
    }

    public static void jsonRequestWithParams(Activity act, int reqType, String url, JSONObject params,
                                             Response.Listener<JSONObject> listener,
                                             Response.ErrorListener errorListener)
    {
        JsonObjectRequest request = new JsonObjectRequest(reqType, url, params, listener, errorListener);
        MySingleton.getInstance(act).addToRequestQueue(request);
    }

    public static void jsonRequestWithHeadersAndParams(Activity act, int reqType, String url,
                                                       final Map<String, String> headers, JSONObject params,
                                                       Response.Listener<JSONObject> listener,
                                                       Response.ErrorListener errorListener)
    {
        JsonObjectRequest request = new JsonObjectRequest(reqType, url, params, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                return headers;
            }
        };
        MySingleton.getInstance(act).addToRequestQueue(request);
    }
}
