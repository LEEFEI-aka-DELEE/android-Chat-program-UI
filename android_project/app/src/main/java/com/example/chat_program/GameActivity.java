package com.example.chat_program;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GameActivity extends AppCompatActivity {

    Button rock,paper,scr,again;
    TextView display,scoredis;
    int ans,score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ans = (int)(Math.random()*100);
        score = 0;

        rock = (Button) findViewById(R.id.button);
        paper = (Button) findViewById(R.id.button2);
        scr = (Button) findViewById(R.id.button3);
        again = (Button) findViewById(R.id.button4);

        rock.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                display = (TextView) findViewById(R.id.LED);
                scoredis = (TextView) findViewById(R.id.score);


                if(ans%3 == 1)
                {
                    Toast.makeText(GameActivity.this,"you win",Toast.LENGTH_SHORT).show();
                    display.setText("YOU WIN");
                    score += 1;
                }
                if(ans%3 == 2)
                {
                    Toast.makeText(GameActivity.this,"you lose",Toast.LENGTH_SHORT).show();
                    display.setText("YOU LOSE");
                    score = 0;
                }
                if(ans%3 == 3)
                {
                    Toast.makeText(GameActivity.this,"draw",Toast.LENGTH_SHORT).show();
                    display.setText("DRAW");
                }

                scoredis.setText(String.valueOf(score));

            }

        });
        paper.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                display = (TextView) findViewById(R.id.LED);
                scoredis = (TextView) findViewById(R.id.score);

                if(ans%3 == 2)
                {
                    Toast.makeText(GameActivity.this,"you win",Toast.LENGTH_SHORT).show();
                    display.setText("YOU WIN");
                    score += 1;
                }
                if(ans%3 == 3)
                {
                    Toast.makeText(GameActivity.this,"you lose",Toast.LENGTH_SHORT).show();
                    display.setText("YOU LOSE");
                    score = 0;
                }
                if(ans%3 == 1)
                {
                    Toast.makeText(GameActivity.this,"draw",Toast.LENGTH_SHORT).show();
                    display.setText("DRAW");
                }

                scoredis.setText(String.valueOf(score));

            }

        });
        scr.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                display = (TextView) findViewById(R.id.LED);
                scoredis = (TextView) findViewById(R.id.score);

                if(ans%3 == 3)
                {
                    Toast.makeText(GameActivity.this,"you win",Toast.LENGTH_SHORT).show();
                    display.setText("YOU WIN");
                    score += 1;
                }
                if(ans%3 == 1)
                {
                    Toast.makeText(GameActivity.this,"you lose",Toast.LENGTH_SHORT).show();
                    display.setText("YOU LOSE");
                    score = 0;
                }
                if(ans%3 == 2)
                {
                    Toast.makeText(GameActivity.this,"draw",Toast.LENGTH_SHORT).show();
                    display.setText("DRAW");
                }

                scoredis.setText(String.valueOf(score));

            }

        });
        again.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                ans = (int)(Math.random()*100);
                Toast.makeText(GameActivity.this,"Ready?",Toast.LENGTH_SHORT).show();
                display = (TextView) findViewById(R.id.LED);
                display.setText("Just choose one~");
            }

        });

    }
}
