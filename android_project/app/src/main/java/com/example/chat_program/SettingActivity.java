package com.example.chat_program;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public void change_activity_setting(View view)
    {
        int settingID = view.getId();
        switch (settingID)
        {
            case R.id.Set_personal_image:
                startActivity(new Intent(this, SettingImageActivity.class));
                Toast.makeText(this, "set personal image", Toast.LENGTH_LONG).show();
                break;
            case R.id.Set_personal_background:
                startActivity(new Intent(this, SettingBackgroungActivity.class));
                Toast.makeText(this, "set personal background", Toast.LENGTH_LONG).show();
                break;
            case R.id.Set_user_name:
                startActivity(new Intent(this, SettingNameActivity.class));
                Toast.makeText(this, "set user name", Toast.LENGTH_LONG).show();
                break;
            case R.id.Set_password:
                startActivity(new Intent(this, SettingPasswordActivity.class));
                Toast.makeText(this, "set password", Toast.LENGTH_LONG).show();
                break;
            case R.id.Logout:
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "log out", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.Setting_back:
                startActivity(new Intent(this, MainHubActivity.class));
                Toast.makeText(this, "Back", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
