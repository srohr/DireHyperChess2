package com.diremangoes.direhyperchess;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NewGame extends AppCompatActivity {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    Handler updateConversationHandler;
    Thread serverThread = null;
    private TextView text;
    int device = 0;
    public int SERVERPORT = 6000;
    public int CLIENTPORT = 6000;
    public String DEVICEIP;
    public String CONNECTIP;
    public boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button server, client, sendmsg;
        EditText clientport, serverport, serverIP, clientIP;

        client = (Button) findViewById(R.id.button);
        server = (Button) findViewById(R.id.button2);
        sendmsg = (Button) findViewById(R.id.SendMessage);

        clientport = (EditText) findViewById(R.id.connectPort);
        serverport = (EditText) findViewById(R.id.hostPort);
        clientIP = (EditText) findViewById(R.id.connectIP);
        serverIP = (EditText) findViewById(R.id.hostIP);

        text = (TextView) findViewById(R.id.textView);

        WifiManager wifiMan = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));

        serverIP.setText(ip);
        serverIP.setEnabled(false);

        serverport.setText("" + SERVERPORT);
        clientport.setText("" + CLIENTPORT);

        connected = false;

        clientport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                    CLIENTPORT = Integer.parseInt(s.toString());
                else
                    CLIENTPORT = 0;
            }
        });

        serverport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.equals(""))
                    SERVERPORT = Integer.parseInt(s.toString());
                else
                    SERVERPORT = 0;
            }
        });

        clientIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CONNECTIP = s.toString();
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Client", "Launching client");

                new Thread(new ClientThread()).start();
            }
        });

        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Server", "Launching server");
                connected = true;
                serverThread = new Thread(new ServerThread());
                serverThread.start();
            }
        });

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    try {
                        EditText et = (EditText) findViewById(R.id.editText);
                        String str = et.getText().toString();
                        //str += "\n";
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                        out.println(str);

                        Log.i("Message to send", str);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Not connected!",
                            Toast.LENGTH_LONG
                    );

                    Log.e("Comms", "Not connected!");
                }
            }
        });




        text = (TextView) findViewById(R.id.text2);
        updateConversationHandler = new Handler();



    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {
        public void run() {
            //Socket socket = null;
            try {
                serverSocket = new ServerSocket(SERVERPORT);


            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {
                Log.d("Server", "Server Running");
                try {
                    socket = serverSocket.accept();
                    connected = true;
                    Log.i("Server", "Client connected!");
                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {
        private Socket clientSocket;
        private BufferedReader input;
        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                //Log.d("Server", "Server loop");
                try {
                    String read = input.readLine();
                    updateConversationHandler.post(new updateUIThread(read));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            text = (TextView) findViewById(R.id.textView);
            if (msg != null)
                Log.d("Server", "Recieving message: " + msg);

            if (msg != null && !msg.equals(""))
                text.setText(msg);
            else if (msg == null)
                text.setText("Client message null?");
            else
                text.setText("Client message empty?");

        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(CONNECTIP);
                socket = new Socket(serverAddr, CLIENTPORT);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            for(;;){
                try {
                    EditText et = (EditText) findViewById(R.id.editText);
                    String str = et.getText().toString();
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);
                    out.println(str);
                    out.flush();
                    BufferedReader  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg = in.readLine();
                    //System.out.println("MSG:" + read);
                    text = (TextView) findViewById(R.id.textView);
                    if (msg != null)
                        Log.d("Client", "Recieving message: " + msg);

                    if (msg != null && !msg.equals(""))
                        text.setText(msg);
                    else if (msg == null)
                        text.setText("Client message null?");
                    else
                        text.setText("Client message empty?");

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
