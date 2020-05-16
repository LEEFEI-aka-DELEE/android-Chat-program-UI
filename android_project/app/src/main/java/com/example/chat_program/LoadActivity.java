package com.example.chat_program;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoadActivity extends AppCompatActivity {

    TextView dot_1,dot_2,dot_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        dot_1 = (TextView) findViewById(R.id.Loading_dot_1);
        dot_2 = (TextView) findViewById(R.id.Loading_dot_2);
        dot_3 = (TextView) findViewById(R.id.Loading_dot_3);

        for(int i = 0;i < 3;i++)
        {
            if(i == 0)
            {
                dot_1.setTextColor(Color.parseColor("#000000"));
                dot_3.setTextColor(Color.parseColor("#BBBBBB"));
            }
            if(i == 1)
            {
                dot_2.setTextColor(Color.parseColor("#000000"));
                dot_1.setTextColor(Color.parseColor("#BBBBBB"));
            }
            if(i == 2)
            {
                dot_3.setTextColor(Color.parseColor("#000000"));
                dot_2.setTextColor(Color.parseColor("#BBBBBB"));
                //i = 0;
            }

        }
    }
}
