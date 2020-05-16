package com.example.chat_program;

import android.content.Context;
import android.content.Intent;
import android.service.autofill.CharSequenceTransformation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity
{
    private ListView chatlist;
    private ArrayAdapter ChatAdapter;
    EditText ChatInput;
    Button ChatButton;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatButton = (Button) findViewById(R.id.Chat_send);

        Bundle bundle = getIntent().getExtras();
        final String destination = bundle.getString("key");
        final String user = bundle.getString("user");

        name = (TextView) findViewById(R.id.Chat_title);
        name.setText(destination);

        //final String FileName = "ChatHistory.txt";
        String file = destination + "_ChatHistory.txt";
        final String FileName = file;

        //getting list context
        final ArrayList<String> list_context = new ArrayList<String>();
        try{
            InputStream chat_history = openFileInput(FileName);
            InputStreamReader reader = new InputStreamReader(chat_history);
            BufferedReader input = new BufferedReader(reader);
            String line = "";
            while ((line = input.readLine()) != null) {
                list_context.add(line);
            }
            if (reader != null)
                reader.close();
            if (chat_history != null)
                chat_history.close();
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
        //get list
        chatlist = (ListView) findViewById(R.id.Chat_list);
        ChatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_context);
        chatlist.setAdapter(ChatAdapter);
        //Thread.sleep(1000);

        ChatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChatInput = (EditText) findViewById(R.id.Chat_input);
                String chat = ChatInput.getText().toString();
                list_context.add(chat);
                ChatAdapter.notifyDataSetChanged();
                ChatInput.setText("");

                try
                {
                    FileOutputStream chat_new_history = openFileOutput(FileName, Context.MODE_APPEND);
                    chat = chat + "\n";
                    chat_new_history.write(chat.getBytes());
                    chat_new_history.close();
                    Toast.makeText(ChatActivity.this, "save!", Toast.LENGTH_LONG).show();
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(ChatActivity.this, "you fork!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void game(View view)
    {
        startActivity(new Intent(ChatActivity.this,GameActivity.class));
    }
}
