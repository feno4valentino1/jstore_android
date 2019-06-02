package com.example.jstore_android_feno;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest
{
    private static final String SELESAI_URL = "http://192.168.43.159:8080/finishtransaction";
    private Map<String, String> params;

    public PesananSelesaiRequest(String id, Response.Listener<String> listener)
    {
        super(Method.POST, SELESAI_URL, listener, null);
        params = new HashMap<>();
        params.put("idinvoice",id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
