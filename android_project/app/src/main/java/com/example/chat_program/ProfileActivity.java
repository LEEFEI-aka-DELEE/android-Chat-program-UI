package com.example.chat_program;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class ProfileActivity extends AppCompatActivity {

    TextView UserName;
    Button go_chat;
    ArrayList<String> Chat_List;
    ArrayAdapter listAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        Bundle bundle = getIntent().getExtras();
        final String fileinput = bundle.getString("key");
        final String user = bundle.getString("user");
        //String fileinput = line;

        UserName = (TextView) findViewById(R.id.textView2);
        UserName.setText(fileinput);

        go_chat = (Button) findViewById(R.id.chat_button);

        go_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String FileName = "User_ChatList.txt";

                //getting list context
                Chat_List = new ArrayList<String>();
                try{
                    InputStream chat_list = openFileInput(FileName);
                    InputStreamReader reader = new InputStreamReader(chat_list);
                    BufferedReader input = new BufferedReader(reader);
                    String line = "";
                    while ((line = input.readLine()) != null) {
                        Chat_List.add(line);
                    }
                    if (reader != null)
                        reader.close();
                    if (chat_list != null)
                        chat_list.close();
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

                if(Chat_List.indexOf(fileinput) == -1)
                {
                    //Chat_List.add(user);
                    Collections.reverse(Chat_List);
                    try
                    {
                        FileOutputStream chat_new_history = openFileOutput(FileName, Context.MODE_APPEND);
                        String user_name = fileinput + "\n";
                        chat_new_history.write(user_name.getBytes());
                        chat_new_history.close();
                        Toast.makeText(ProfileActivity.this, "added", Toast.LENGTH_LONG).show();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "you fork!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Chat_List.remove(Chat_List.indexOf(fileinput));
                    Chat_List.add(fileinput);
                    Collections.reverse(Chat_List);
                    try
                    {
                        FileOutputStream chat_new_history = openFileOutput(FileName, Context.MODE_PRIVATE);
                        String user_name = TextUtils.join("\n", Chat_List) + "\n";
                        chat_new_history.write(user_name.getBytes());
                        chat_new_history.close();
                        Toast.makeText(ProfileActivity.this, "added", Toast.LENGTH_LONG).show();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "you fork!", Toast.LENGTH_LONG).show();
                    }
                }

                //go to chat
                Intent intent = new Intent();
                intent.setClass(ProfileActivity.this,ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key",fileinput);
                bundle.putString("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
                //startActivity(new Intent(ProfileActivity.this,ChatActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


