package com.example.chat_program;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class ListActivity extends AppCompatActivity {

    private ListView output_list;
    //private ListAdapter listAdapter;
    ArrayAdapter listAdapter;
    ArrayList<String> Recent_chat_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //getting list context
        //String[] list_context = {"Alice", "lambda group", "helloworld"}; -> demo string
        //final String fileinput = bundle.getString("key");
        Bundle bundle = getIntent().getExtras();
        final String user = bundle.getString("user");

        final String FileName = "User_ChatList.txt";

        //getting list context
        Recent_chat_list = new ArrayList<String>();
        try{
            InputStream chat_list = openFileInput(FileName);
            InputStreamReader reader = new InputStreamReader(chat_list);
            BufferedReader input = new BufferedReader(reader);
            String line = "";
            while ((line = input.readLine()) != null) {
                Recent_chat_list.add(line);
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

        //get list
        output_list = (ListView) findViewById(R.id.List_output);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Recent_chat_list);
        output_list.setAdapter(listAdapter);

        //output_list.setOnItemClickListener();

        output_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView A, View B, int C,
                                    long arg3) {
                // TODO Auto-generated method stub
                output_list = (ListView) A;

                Intent intent = new Intent();
                intent.setClass(ListActivity.this,ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key",output_list.getItemAtPosition(C).toString());
                bundle.putString("user",user);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

}
