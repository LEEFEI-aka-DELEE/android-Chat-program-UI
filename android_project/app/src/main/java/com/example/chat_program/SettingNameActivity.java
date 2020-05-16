package com.example.chat_program;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SettingNameActivity extends AppCompatActivity {

    Button NameComfirm;
    EditText new_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_name);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        NameComfirm = (Button) findViewById(R.id.Setting_name_comfirm);

        NameComfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new_name = (EditText) findViewById(R.id.Setting_name_input);
                String inputname = new_name.getText().toString();
                if(inputname == null)
                {
                    Toast.makeText(SettingNameActivity.this,"at least input something",Toast.LENGTH_SHORT).show();
                }
                else
                {
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

                    final String username = inputname;
                    final String userpassword = line_password;

                    try
                    {
                        FileOutputStream chat_new_history = openFileOutput("UserProfile.txt", Context.MODE_PRIVATE);
                        String profile_format = username + "\n" + userpassword;
                        chat_new_history.write(profile_format.getBytes());
                        chat_new_history.close();
                        Toast.makeText(SettingNameActivity.this, "save!", Toast.LENGTH_LONG).show();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(SettingNameActivity.this, "you fork!", Toast.LENGTH_LONG).show();
                    }

                    finish();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
