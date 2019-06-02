package com.example.jstore_android_feno;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;

public class SelesaiPesananActivity extends AppCompatActivity
{
    TextView tvDate, tvId, tvPayment, tvDueDate, tvItem, tvStatusCategory, tvPrice, tvTotalPrice;
    Button btnCancel, btnFinish;
    int currentInvoiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        tvDate = findViewById(R.id.tvDate);
        tvId = findViewById(R.id.tvId);
        tvPayment = findViewById(R.id.tvPayment);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvItem = findViewById(R.id.tvItem);
        tvStatusCategory = findViewById(R.id.tvStatusCategory);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCancel = findViewById(R.id.btnCancel);
        btnFinish = findViewById(R.id.btnFinish);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            currentInvoiceId = extras.getInt("id");
        }
        textInit();

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Invoice Cancelled!").create().show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Operation failed! Please try again.").create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Operation failed! Please try again.").create().show();
                        }
                    }
                };

                PesananBatalRequest request = new PesananBatalRequest(String.valueOf(currentInvoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(request);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Invoice Finished!").create().show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Operation Failed! Please try again.").create().show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Operation failed! Please try again.").create().show();
                        }
                    }
                };
                PesananSelesaiRequest request = new PesananSelesaiRequest(String.valueOf(currentInvoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(request);
            }
        });
    }

    public void textInit()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("jsonobject", "onResponse: " + jsonObject);
                    if (jsonObject == null || jsonObject.getString("isActive").equals("false"))
                    {
                        btnCancel.setEnabled(false);
                        btnFinish.setEnabled(false);

                    }
                    else {
                        String date = jsonObject.getString("date");
                        JSONArray item_json = jsonObject.getJSONArray("item");

                        String invoiceType = jsonObject.getString("invoiceType");
                        String invoiceStatus = jsonObject.getString("invoiceStatus");
                        Integer totalPrice = jsonObject.getInt("totalPrice");
                        switch (invoiceStatus)
                        {
                            case "Unpaid":
                                String dueDate = jsonObject.getString("dueDate");
                                tvDueDate.setText(dueDate);
                                tvTotalPrice.setText("Rp. " + totalPrice);
                                break;
                            case "Installment":
                                String installmentPeriod = jsonObject.getString("installmentPeriod");
                                String installmentPrice = jsonObject.getString("installmentPrice");
                                tvTotalPrice.setText("Rp. " + installmentPrice + "x" + installmentPeriod);
                        }
                        tvDate.setText(date);
                        tvId.setText("Invoice ID: " + currentInvoiceId);
                        tvItem.setText(String.valueOf(item_json));
                        tvPayment.setText(invoiceStatus);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        };
    }
}
