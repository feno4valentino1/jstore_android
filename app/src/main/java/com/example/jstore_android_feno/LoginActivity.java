package com.example.jstore_android_feno;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.support.v7.app.AlertDialog;

public class LoginActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText emailInput = (EditText) findViewById(R.id.emailInput);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                final String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();

                if(email.equals("test@test.com") && password.equals("test"))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                    builder1.setMessage("Login Success!").create().show();
                }
                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                    builder1.setMessage("Login Success!").create().show();
                }
            }
        });
    }
}
