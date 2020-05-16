package com.example.chat_program;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        try {
            PrintWriter writer = new PrintWriter("/steven.txt", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        }catch (IOException e){

        }

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "stevenn.txt");
        if (!file.mkdirs()) {

        }


        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                goToSecondActivity();

            }

        });

    }

    private void goToSecondActivity() {

        Intent intent = new Intent(this, ReceiveSecondActivity.class);

        startActivity(intent);

    }
}
