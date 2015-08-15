package com.github.volley;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public final class FaSTHTTPUtils {
    public static void httpGet(String url, final VolleyListener listener) {
        RequestQueue queue = MyVolley.getRequestQueue();
        StringRequest myReq = new UTF8StringRequest(Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        queue.add(myReq);
    }

    private FaSTHTTPUtils() {
    }
}
