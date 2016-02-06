package com.diremangoes.direhyperchess;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

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

        final Button server, client, sendmsg;
        EditText clientport, serverport, serverIP, clientIP;

        client = (Button) findViewById(R.id.button);
        server = (Button) findViewById(R.id.button2);
        //sendmsg = (Button) findViewById(R.id.SendMessage);

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
                //Client client1 = new Client(getApplicationContext(), CONNECTIP, CLIENTPORT);
                //String ret = client1.Sync("Client Handshake");
                //Log.d("Client", "Recieved string: \"" + ret + "\"");
                //Launch(ret, "Server Handshake");


            }
        });

        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        text = (TextView) findViewById(R.id.text2);
        updateConversationHandler = new Handler();

    }

    public void Launch(String msg, String desiredValue){
        try {
            if (msg.equals(desiredValue)) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("IP", CONNECTIP);
                intent.putExtra("Port", CLIENTPORT);
                String txt = "Handshake recieved.\nGame starting in ";
                for (int i = 5; i > 0; i--) {
                    text.setText(txt + i);
                    Thread.sleep(1000);
                }

                startActivity(intent);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
