package com.github.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 15.4.5.
 */
public class UTF8StringRequest extends StringRequest {
    public UTF8StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = null;
        try {
            parsed = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            try {
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
