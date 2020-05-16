package com.example.chat_program;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity
{
    Button login_to_register_button,login_to_main_button;
    EditText AccountInput,PasswardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //button input
        login_to_main_button = (Button) findViewById(R.id.Login_to_main);
        login_to_register_button = (Button) findViewById(R.id.Login_to_register);

        final String FileName = "UserProfile.txt";

        String line_name = "",line_password = "";

        try{
            InputStream Profile_info = openFileInput(FileName);
            InputStreamReader reader = new InputStreamReader(Profile_info);
            BufferedReader input = new BufferedReader(reader);

            line_name = input.readLine();
            line_password = input.readLine();

            if (reader != null)
                reader.close();
            if (Profile_info != null)
                Profile_info.close();
            if (input != null)
                input.close();

            //int size = chat_history.available();
            //byte[] buffer = new byte[size];
            //chat_history.read(buffer);
            //chat_history.close();
            //list_context.add(new String(buffer));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        final String username = line_name;
        final String userpassword = line_password;

        //button action
        login_to_register_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //go to register
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        login_to_main_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //verify the login info
                //if incorrect
                AccountInput = (EditText) findViewById(R.id.Login_account_input);
                String user_name = AccountInput.getText().toString();
                PasswardInput = (EditText) findViewById(R.id.Login_password_input);
                String user_password = PasswardInput.getText().toString();
                if(user_name.equals(username) && user_password.equals(userpassword))
                {
                    Toast.makeText(LoginActivity.this, "welcome "+user_name, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainHubActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "something is wrong\ntry to change something to make it right", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
