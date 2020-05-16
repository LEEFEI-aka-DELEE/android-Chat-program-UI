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

import java.io.FileOutputStream;

public class CreateGroupActivity extends AppCompatActivity {

    EditText name_input;
    Button comfirm;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        groupName = "0";

        comfirm = (Button) findViewById(R.id.Create_button);
        comfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name_input = (EditText) findViewById(R.id.Create_input);
                groupName = name_input.getText().toString();
                Intent result = new Intent();
                result.putExtra("name", groupName);
                setResult(1,result);
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        Intent result = new Intent();
        result.putExtra("name", groupName);
        setResult(1,result);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
