package com.example.jstore_android_feno;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity
{
    private int currentUserId;
    private int itemId;
    private String itemName;
    private String itemCategory;
    private String itemStatus;
    private double itemPrice;
    private int installmentPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentUserId = extras.getInt("currentUserId");
            itemId = extras.getInt("item_id");
            itemName = extras.getString("item_name");
            itemCategory = extras.getString("item_category");
            itemStatus = extras.getString("item_status");
            itemPrice = extras.getInt("item_price");
        }

        TextView item_name = findViewById(R.id.item_name);
        TextView item_category = findViewById(R.id.item_category);
        TextView item_status = findViewById(R.id.item_status);
        final TextView item_price = findViewById(R.id.item_price);
        final EditText installment_period = findViewById(R.id.installment_period);
        final TextView total_price = findViewById(R.id.total_price);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final Button hitung = findViewById(R.id.hitung);
        final Button pesan = findViewById(R.id.pesan);
        final TextInputLayout period = findViewById(R.id.input_layout_period);

        period.setVisibility(View.GONE);
        hitung.setEnabled(false);
        pesan.setEnabled(false);

        item_name.setText(itemName);
        item_category.setText(itemCategory);
        item_status.setText(itemStatus);
        item_price.setText("Rp "+String.valueOf((int)itemPrice));
        total_price.setText("Rp "+"0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                installment_period.setEnabled(true);
                RadioButton radioButton = findViewById(i);
                String selected = radioButton.getText().toString().trim();
                switch (selected){
                    case "Pay Now":
                        hitung.setEnabled(true);
                        pesan.setEnabled(false);
                        period.setVisibility(View.GONE);
                        break;
                    case "Pay Later":
                        hitung.setEnabled(true);
                        pesan.setEnabled(false);
                        period.setVisibility(View.GONE);
                        break;
                    case "Installment":
                        period.setVisibility(View.VISIBLE);
                        hitung.setEnabled(true);
                        pesan.setEnabled(false);
                        break;
                }
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installment_period.setEnabled(false);
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedRadioId);
                String selected = selectedRadio.getText().toString().trim();
                switch (selected){
                    case "Pay Now":
                        total_price.setText("Rp "+String.valueOf((int)itemPrice));
                        hitung.setEnabled(false);
                        pesan.setEnabled(true);
                        break;
                    case "Pay Later":
                        total_price.setText("Rp "+String.valueOf((int)itemPrice));
                        pesan.setEnabled(true);
                        break;
                    case "Installment":
                        installmentPeriod = Integer.parseInt(installment_period.getText().toString());
                        total_price.setText("Rp "+String.valueOf((int) itemPrice/installmentPeriod));
                        installment_period.setEnabled(false);
                        pesan.setEnabled(true);
                        break;
                }
            }
        });

        pesan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedRadioId);
                String selected = selectedRadio.getText().toString().trim();
                String period = installment_period.getText().toString().trim();
                BuatPesananRequest request = null;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(response!=null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Order Success!").create().show();
                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Order Failed!").create().show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                if(selected.equals("Pay Now")){
                    request = new BuatPesananRequest(String.valueOf(itemId), String.valueOf(currentUserId), "http://192.168.43.229:8080/createinvoicepaid", responseListener);
                }else if(selected.equals("Pay Later")){
                    request = new BuatPesananRequest(String.valueOf(itemId), String.valueOf(currentUserId), "http://192.168.43.229:8080/createinvoiceunpaid", responseListener);
                }else if(selected.equals("Installment")){
                    request = new BuatPesananRequest(String.valueOf(itemId), period, String.valueOf(currentUserId), "http://192.168.43.229:8080/createinvoiceinstallment", responseListener);
                }
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(request);
            }
        });
    }
}
