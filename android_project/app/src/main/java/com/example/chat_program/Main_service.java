package com.example.chat_program;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

import java.util.*;

public class Main_service extends Service {
    public Main_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override //建立時執行
    public void onCreate() {
        Toast.makeText(this, "service is create", Toast.LENGTH_SHORT).show();
        //super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Toast.makeText(this, "Service running", Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                //Toast.makeText(Main_service.this, "3 secs has passed", Toast.LENGTH_SHORT).show();
            }

        };


        new Thread(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                while(true)
                {
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Override //結束時執行
    public void onDestroy() {
        Toast.makeText(this, "service destroy", Toast.LENGTH_SHORT).show();
    }
}

/*
class BroadCastThread extends Thread {
    private Vector _clientVector; // Àx¦s³s½uªº«È¤áºÝ
    private Vector _messageVector; // Àx¦s¼s¼½°T®§

    public BroadCastThread() {
        setDaemon(true);
        _clientVector = new Vector();
        _messageVector = new Vector();
    }

    public void addClientThread(ClientThread client) {
        // ±N«È¤áºÝadd¦Ü³B²z¦î¦C
        _clientVector.addElement(client);
    }

    public void removeClientThread(ClientThread client) {
        // ²¾°£«È¤áºÝ
        _clientVector.removeElement(client);
    }

    public void addMessage(String message) {
        _messageVector.addElement(message);
    }

    public void run() {
        ClientThread client = null;
        String message = null;
        try {
            while(true) {
                // ¨C¨â¬í¼s¼½¤@¦¸
                Thread.sleep(2000);

                // ¨ú¥X­n¼s¼½ªº°T®§
                // ¥Ø«e¨S¦³°T®§´N¤£³B²z±µ¤U¨Óªº¤º®e
                if(_messageVector.isEmpty())
                    continue;

                message = (String) _messageVector.firstElement();
                _messageVector.removeElement(message);

                // ±N°T®§¤@­Ó¤@­Ó¥áµ¹«È¤áºÝ
                for(int i = 0; i < _clientVector.size(); i++) {
                    client = (ClientThread) _clientVector.elementAt(i);
                    client.sendMessage(message);
                }
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}

class ClientThread extends Thread {
    private Socket _skt;
    private BroadCastThread _broadCastThread;  // ¼s¼½°õ¦æºü
    private PrintStream _printStream;  // ¿é¥X¦ê¬y
    private static int _clientNum = 0; // «È¤áºÝ³s½u¼Æ

    public ClientThread(Socket skt, BroadCastThread broad) {
        setDaemon(true);
        _skt = skt;
        _broadCastThread = broad;

        try {
            _printStream = new PrintStream(_skt.getOutputStream());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        _clientNum++;
    }

    public void sendMessage(String message) {
        _printStream.println(message);
    }

    public void run() {
        BufferedReader buf = null;
        String userMessage = null;
        String nickName = null;  // ¨Ï¥ÎªÌ¦WºÙ

        try {
            buf = new BufferedReader(new
                    InputStreamReader(_skt.getInputStream()));
            sendMessage("Connected Success! Please input user name..........");

            nickName = buf.readLine();
            if(nickName == null)
                nickName = "guest";

            sendMessage("Welcome " + nickName + " ! There are " + _clientNum + "users online......");

            _broadCastThread.addMessage(":: " + nickName + " jump into Chatting Room!" );

            // Åª¨ú«È¤áºÝ°T®§
            while((userMessage = buf.readLine()) != null) {
                // Â÷½u«ü¥O¬° /bye
                if(userMessage.equals("/bye"))
                    break;

                // add¦Ü°T®§¦î¦C
                _broadCastThread.addMessage(nickName + ">>" + userMessage);
            }
        }
        catch(IOException e) {
        }
        finally {
            // ³s½u²×¤î
            _clientNum--; // «È¤á³s½u¼Æ´î¤@
            _broadCastThread.addMessage(nickName + " jump out Chatting Room......");
            _broadCastThread.removeClientThread(this);
            try {
                _skt.close();
            }
            catch(IOException e) {
            }
        }
    }
}

class ChatClientSocket extends Thread {
    private Socket skt;        // «È¤áºÝ³s½uSocketª«¥ó
    private InetAddress host;  // «ü©wªº¦øªAºÝIP
    private int port;          // «ü©wªº¦øªAºÝ³s±µ°ð

    private BufferedReader theInputStream;
    private PrintStream theOutputStream;
    private String message;     // ¦øªAºÝ¶Ç¦^ªº¸ê®Æ

    private MultiChatClient chatBox; // ²á¤Ñµ{¦¡¤¶­±

    public ChatClientSocket(String ip, int port) {
        try {
            // ¨ú±o¦øªAºÝªºInetAddressª«¥ó¡B³q°T³s±µ°ð
            host = InetAddress.getByName(ip);
            this.port = port;
        }
        catch (IOException e) {
            //JOptionPane.showMessageDialog(null, e.toString(),
                    //"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    // «ü©w³o­ÓSocketªº°T®§Æ[¹îªÌ
    public void setMessageObserver(MultiChatClient box) {
        chatBox = box;
    }

    // ¨ú±o°T®§
    public String getMessage() {
        return message;
    }

    public void run() {
        try {
            message = "Trying to connect......";
            chatBox.update();

            // «Ø¥ßSocketª«¥ó¨Ã¹Á¸Õ³s½u
            skt = new Socket(host, port);
            message = "Connected Success!\n";
            chatBox.update();

            // ±qInputStream«Ø¥ßÅª¨ú½w½Ä°Ï
            theInputStream = new BufferedReader(
                    new InputStreamReader(skt.getInputStream()));
            // ±qOutputStream¤¤«Ø¥ßPrintStreamª«¥ó
            theOutputStream = new PrintStream(skt.getOutputStream());

            // Åª¨ú¸ê®Æ°j°é
            while((message = theInputStream.readLine()) != null) {
                // ±N¤§Åã¥Ü¦bAppletªºTextArea¤W
                message = ": " + message + "\n";
                chatBox.update();
            }

            if(message == null) {
                skt.close();
                message = "Connect break off\n";
                chatBox.update();
                chatBox.setGUIState(true);
            }
        }
        catch (IOException e) {
            message = e.toString();
            chatBox.update();
        }
    }

    // «È¤áºÝ¥á¥X¸ê®Æªº¤èªk
    public void dataOutput(String data) {
        theOutputStream.println(data);
    }
}

class MultiChatClient extends Frame {
    private Button clientBtn, serverBtn;
    private TextArea textArea;
    private TextField tfAddress, tfPort, tfType;
    private ChatClientSocket clientSkt;  // «È¤áºÝ³s½u³B²z°õ¦æºü

    public MultiChatClient() {
        clientBtn = new Button("Connect");
        textArea = new TextArea("", 10, 50, TextArea.SCROLLBARS_BOTH);
        tfAddress = new TextField("localhost");  // IPÄæ¦ì
        tfPort = new TextField("port");  // ³s±µ°ðÄæ¦ì
        tfType = new TextField(50);   // ¤å¦r¿é¤JÄæ¦ì

        tfType.addKeyListener(new TFListener()); // µù¥U¨Æ¥ó
        textArea.setEditable(false);

        setLayout(new FlowLayout());  // ª©­±°t¸m
        add(tfAddress);
        add(tfPort);
        add(clientBtn);
        add(textArea);
        add(tfType);
        setSize(400, 300);
        setTitle("MultiChatClient");

        // «ö¤U¡uConnect¡v«ö¶sªº¨Æ¥ó³B²z
        clientBtn.addActionListener(
                new ActionListener() {  // °Î¦WÃþ§O
                    public void actionPerformed(ActionEvent e) {
                        setClient();
                    }
                }
        );

        addWindowListener(  // «ö¤UÃö³¬¶s®Éµ²§ô
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        show();
    }

    public void setGUIState(boolean state) {
        tfAddress.setEnabled(state);
        tfPort.setEnabled(state);
        clientBtn.setEnabled(state);
    }

    // ¨ú±o°T®§¨ÃÅã¥Ü¦bGUI¤W
    public void update() {
        textArea.append(clientSkt.getMessage());
    }

    public static void main(String args[]) {
        MultiChatClient frm = new MultiChatClient();
    }

    // ³]©w«È¤áºÝ
    private void setClient() {
        // ¨ú±o«ü©wªºIP»P³s±µ°ð
        int port = Integer.parseInt(tfPort.getText());
        // «Ø¥ß«È¤áºÝ³s½u°õ¦æºü
        clientSkt = new ChatClientSocket(tfAddress.getText(), port);
        clientSkt.setMessageObserver(this);
        // ±Ò°Ê°õ¦æºü¶i¦æ³s½u
        clientSkt.start();
    }

    // ¨Æ¥ó³B²z
    private class TFListener implements KeyListener {
        public void keyPressed(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {
            // ¦pªG«ö¤Uªº¬O¡uEnter¡vÁä
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                // ±N¸ê®Æ³z¹L³s½u°õ¦æºü°e¥X
                clientSkt.dataOutput(tfType.getText());
                // ²M°£¤U¤è¤å¦rÄæ¦ì¤º®e
                tfType.setText("");
            }
        }
    }
}
*/