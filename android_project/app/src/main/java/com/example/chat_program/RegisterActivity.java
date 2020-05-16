package com.example.chat_program;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity
{
    Button register_to_login_button,register_to_main_button;
    EditText new_name,new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //button input
        register_to_login_button = (Button) findViewById(R.id.Register_to_Login);
        register_to_main_button = (Button) findViewById(R.id.Register_to_main);
        //button action
        register_to_login_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //back to login
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        register_to_main_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //login new account to server
                //unfinish
                new_name = (EditText) findViewById(R.id.Register_account_input);
                String account_store = new_name.getText().toString();
                new_password = (EditText) findViewById(R.id.Register_password_input);
                String password_store = new_password.getText().toString();

                try
                {
                    FileOutputStream chat_new_history = openFileOutput("UserProfile.txt", Context.MODE_PRIVATE);
                    String profile_format = account_store + "\n" + password_store;
                    chat_new_history.write(profile_format.getBytes());
                    chat_new_history.close();
                    Toast.makeText(RegisterActivity.this, "save!", Toast.LENGTH_LONG).show();
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "you fork!", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(RegisterActivity.this, "welcome", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this,MainHubActivity.class));
                finish();
            }
        });
    }
}
