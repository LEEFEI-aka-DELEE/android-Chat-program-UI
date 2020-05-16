package com.example.chat_program;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void change_activity_debug(View view)
    {
        int debugID = view.getId();
        switch (debugID)
        {
            case R.id.debug_to_chat:
                startActivity(new Intent(this, ChatActivity.class));
                Toast.makeText(this, "To Chat", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                Toast.makeText(this, "To Profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_load:
                startActivity(new Intent(this, LoadActivity.class));
                Toast.makeText(this, "To Load", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_login:
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "To Login", Toast.LENGTH_LONG).show();
                startService(new Intent(MainActivity.this,Main_service.class));
                break;
            case R.id.debug_to_register:
                startActivity(new Intent(this, RegisterActivity.class));
                Toast.makeText(this, "To Register", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_setting:
                startActivity(new Intent(this, SettingActivity.class));
                Toast.makeText(this, "To Setting", Toast.LENGTH_LONG).show();
                stopService(new Intent(MainActivity.this,Main_service.class));
                break;
            case R.id.debug_to_list:
                startActivity(new Intent(this, ListActivity.class));
                Toast.makeText(this, "To List", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_create:
                startActivity(new Intent(this, CreateGroupActivity.class));
                Toast.makeText(this, "To group", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_mainhub:
                startActivity(new Intent(this, MainHubActivity.class));
                Toast.makeText(this, "To mainhub", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_receive:
                startActivity(new Intent(this, ReceiveActivity.class));
                Toast.makeText(this, "To mainhub", Toast.LENGTH_LONG).show();
                break;
            case R.id.debug_to_send:
                startActivity(new Intent(this, SendActivity.class));
                Toast.makeText(this, "To mainhub", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
