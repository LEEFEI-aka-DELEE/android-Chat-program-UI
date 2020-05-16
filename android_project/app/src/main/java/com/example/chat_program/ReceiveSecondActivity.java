package com.example.chat_program;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.LocalSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//import androidx.annotation.MainThread;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NavUtils;

public class ReceiveSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_secoond);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra();
        new Thread(new GetThread()).start();
        Log.e("Thread" , "started?");
        /*WebView myWebView = (WebView) findViewById(R.id.web1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.requestFocus();
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl("http://www.google.com.tw/");//("http://goo.gl/");//+message);*/

    }

    class GetThread implements Runnable {

        @Override
        public void run() {
            Log.e("Thread" , "started?");
            client obj = new client();
            obj.receive();
            //obj.send();
            Thread.interrupted();
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    private class client {

        private Socket s = null;
        private String ip = "35.194.161.33"; //"35.194.161.33";
        private InetAddress host;
        private int port = 8001;
        private String filename;
        // private FileOutputStream writer = new FileOutputStream(file);

        public void set_host(String ip, int port) {
            try {
                this.host = InetAddress.getByName(ip);
                this.port = port;
            } catch (UnknownHostException e) {
                System.out.println(e.getMessage());
            }
        }

        public void send() {
            try {
                this.set_host(this.ip, this.port);
                s = new Socket(this.host, this.port);
                DataOutputStream Doutput = new DataOutputStream(s.getOutputStream());
                long file_size = (long) 0;
                    /*System.out.println("Please input filename ex:a.txt .......>");
                    Scanner scanner = new Scanner(System.in);
                    String filename = scanner.nextLine();
                    System.out.println(filename);*/
                filename = "HELLO.txt";
                Log.e("File" , "start open");
                Context a = getApplicationContext();
                File folder= a.getExternalFilesDir("temp");
                File file = new File(folder , filename);
                FileInputStream reader = new FileInputStream(file);
                Log.e("File" , "open?");
                File f = new File(filename);
                file_size = f.length();
                Doutput.writeLong(file_size);
                Doutput.writeUTF(filename);
                byte[] buffer = new byte[10240];
                int count = 1;
                while (count > 0) {
                    count = reader.read(buffer);
                    System.out.println(count);
                    Doutput.write(buffer, 0, count);
                    System.out.println("send packet\n");
                }
                reader.close();
            } catch (UnknownHostException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public void receive() {
            try {
                this.set_host(this.ip, 8002);
                s = new Socket(this.host, 8002);
                DataInputStream Dinput = new DataInputStream(s.getInputStream());
                long file_size = (long) 0;
                file_size = Dinput.readLong();
                filename = Dinput.readUTF();
                System.out.println(filename);
                    /*File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "download");
                    if (!dir.mkdirs()) {
                        Log.e("FILE::", "Directory not created");
                    }
                    File file = new File(dir , filename);*/
                Context a = getApplicationContext();
                File folder= a.getExternalFilesDir("temp");
                File file = new File(folder , filename);
                FileOutputStream writer = new FileOutputStream(file);
                byte[] buffer = new byte[10240];
                int count = 1;
                System.out.println(count);
                while (count > 0) {
                    Log.e("File" , "write?");
                    count = Dinput.read(buffer);
                    System.out.println(count);
                    writer.write(buffer, 0, count);
                    System.out.println("receive packet\n");
                }
                writer.close();
            } catch (UnknownHostException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}

