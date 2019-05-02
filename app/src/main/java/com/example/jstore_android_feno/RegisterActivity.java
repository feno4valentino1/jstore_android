package com.example.jstore_android_feno;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final EditText emailInput = (EditText) findViewById(R.id.emailInput);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        final Button registerButton = (Button) findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String inputName = nameInput.getText().toString();
                final String inputEmail= emailInput.getText().toString();
                final String inputPassword = passwordInput.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse != null)
                            {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                                builder1.setMessage("Register successful!").create().show();
                            }
                        }
                        catch (JSONException e)
                        {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                            builder1.setMessage("Register failed.").create().show();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(inputName, inputEmail, inputPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}

