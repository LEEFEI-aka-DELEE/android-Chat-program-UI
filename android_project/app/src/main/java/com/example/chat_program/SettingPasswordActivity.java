package com.example.chat_program;

import android.content.Context;
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

public class SettingPasswordActivity extends AppCompatActivity {

    Button PasswordComfirm;
    EditText oldPassword,newPassword,newPassword_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.85));

        PasswordComfirm = (Button) findViewById(R.id.Setting_password_comfirm);

        PasswordComfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                oldPassword = (EditText) findViewById(R.id.Setting_password_og_input);
                String old_one = oldPassword.getText().toString();
                newPassword = (EditText) findViewById(R.id.Setting_password_new_input);
                String new_one = newPassword.getText().toString();
                newPassword_re = (EditText) findViewById(R.id.Setting_password_re_input);
                String new_one_re = newPassword_re.getText().toString();

                if(old_one == null || (new_one == null || new_one_re == null))
                {
                    Toast.makeText(SettingPasswordActivity.this,"at least input something",Toast.LENGTH_SHORT).show();
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

                    if(new_one.equals(new_one_re) && old_one.equals(line_password))
                    {


                        final String username = line_name;
                        final String userpassword = new_one;

                        try
                        {
                            FileOutputStream chat_new_history = openFileOutput(FileName, Context.MODE_PRIVATE);
                            String profile_format = username + "\n" + userpassword;
                            chat_new_history.write(profile_format.getBytes());
                            chat_new_history.close();
                            Toast.makeText(SettingPasswordActivity.this, "save!", Toast.LENGTH_LONG).show();
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(SettingPasswordActivity.this, "you fork!", Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SettingPasswordActivity.this, "you might want to check what you type", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
