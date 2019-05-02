package com.example.jstore_android_feno;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest
{
    private static final String Regis_URL = "http://10.0.2.2/items";

    public MenuRequest(Response.Listener<String> listener)
    {
        super(Request.Method.GET, Regis_URL, listener, null);
    }
}