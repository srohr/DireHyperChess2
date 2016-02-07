package com.diremangoes.direhyperchess;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import layout.Chessgame;

public class MainActivity extends FragmentActivity {
    TextView info, infoip, msg;
    String message ="";
    ServerSocket serverSocket;
    int defaultPort = 8080;
    EditText ServerPort;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);

        info.setText(getIpAddress());

        Button serverButton = (Button) findViewById(R.id.buttonServer);
        Button clientButton = (Button) findViewById(R.id.buttonClient);

        final EditText ServerIP = (EditText) findViewById(R.id.editText2);
        EditText ServerPort = (EditText) findViewById(R.id.editText);
        final Thread socketServerThread = new Thread(new SocketServerThread());





        ServerPort.setText(String.valueOf(defaultPort));
        ServerPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    defaultPort = Integer.valueOf(s.toString());
                }
            }
        });

        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socketServerThread.isAlive())
                    socketServerThread.destroy();

                WifiManager wifiMan = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInf = wifiMan.getConnectionInfo();
                int ipAddress = wifiInf.getIpAddress();
                String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

                ServerIP.setText(ip);
                ServerIP.setFocusable(false);

                socketServerThread.start();
                //msg.setText("Handshake from the server.");

            }
        });

       clientButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent client = new Intent(getBaseContext(), Client.class);
               startActivity(client);
           }
       });

        InitBoard();

    }


    Tile lastClickedTile;
    public void InitBoard(){
        ImageView[][] GUIBoard = new ImageView[8][8];
        {
            GUIBoard[0][0] = (ImageView) findViewById(R.id.cellA1);
            GUIBoard[1][0] = (ImageView) findViewById(R.id.cellA2);
            GUIBoard[2][0] = (ImageView) findViewById(R.id.cellA3);
            GUIBoard[3][0] = (ImageView) findViewById(R.id.cellA4);
            GUIBoard[4][0] = (ImageView) findViewById(R.id.cellA5);
            GUIBoard[5][0] = (ImageView) findViewById(R.id.cellA6);
            GUIBoard[6][0] = (ImageView) findViewById(R.id.cellA7);
            GUIBoard[7][0] = (ImageView) findViewById(R.id.cellA8);
            GUIBoard[0][1] = (ImageView) findViewById(R.id.cellB1);
            GUIBoard[1][1] = (ImageView) findViewById(R.id.cellB2);
            GUIBoard[2][1] = (ImageView) findViewById(R.id.cellB3);
            GUIBoard[3][1] = (ImageView) findViewById(R.id.cellB4);
            GUIBoard[4][1] = (ImageView) findViewById(R.id.cellB5);
            GUIBoard[5][1] = (ImageView) findViewById(R.id.cellB6);
            GUIBoard[6][1] = (ImageView) findViewById(R.id.cellB7);
            GUIBoard[7][1] = (ImageView) findViewById(R.id.cellB8);
            GUIBoard[0][2] = (ImageView) findViewById(R.id.cellC1);
            GUIBoard[1][2] = (ImageView) findViewById(R.id.cellC2);
            GUIBoard[2][2] = (ImageView) findViewById(R.id.cellC3);
            GUIBoard[3][2] = (ImageView) findViewById(R.id.cellC4);
            GUIBoard[4][2] = (ImageView) findViewById(R.id.cellC5);
            GUIBoard[5][2] = (ImageView) findViewById(R.id.cellC6);
            GUIBoard[6][2] = (ImageView) findViewById(R.id.cellC7);
            GUIBoard[7][2] = (ImageView) findViewById(R.id.cellC8);
            GUIBoard[0][3] = (ImageView) findViewById(R.id.cellD1);
            GUIBoard[1][3] = (ImageView) findViewById(R.id.cellD2);
            GUIBoard[2][3] = (ImageView) findViewById(R.id.cellD3);
            GUIBoard[3][3] = (ImageView) findViewById(R.id.cellD4);
            GUIBoard[4][3] = (ImageView) findViewById(R.id.cellD5);
            GUIBoard[5][3] = (ImageView) findViewById(R.id.cellD6);
            GUIBoard[6][3] = (ImageView) findViewById(R.id.cellD7);
            GUIBoard[7][3] = (ImageView) findViewById(R.id.cellD8);
            GUIBoard[0][4] = (ImageView) findViewById(R.id.cellE1);
            GUIBoard[1][4] = (ImageView) findViewById(R.id.cellE2);
            GUIBoard[2][4] = (ImageView) findViewById(R.id.cellE3);
            GUIBoard[3][4] = (ImageView) findViewById(R.id.cellE4);
            GUIBoard[4][4] = (ImageView) findViewById(R.id.cellE5);
            GUIBoard[5][4] = (ImageView) findViewById(R.id.cellE6);
            GUIBoard[6][4] = (ImageView) findViewById(R.id.cellE7);
            GUIBoard[7][4] = (ImageView) findViewById(R.id.cellE8);
            GUIBoard[0][5] = (ImageView) findViewById(R.id.cellF1);
            GUIBoard[1][5] = (ImageView) findViewById(R.id.cellF2);
            GUIBoard[2][5] = (ImageView) findViewById(R.id.cellF3);
            GUIBoard[3][5] = (ImageView) findViewById(R.id.cellF4);
            GUIBoard[4][5] = (ImageView) findViewById(R.id.cellF5);
            GUIBoard[5][5] = (ImageView) findViewById(R.id.cellF6);
            GUIBoard[6][5] = (ImageView) findViewById(R.id.cellF7);
            GUIBoard[7][5] = (ImageView) findViewById(R.id.cellF8);
            GUIBoard[0][6] = (ImageView) findViewById(R.id.cellG1);
            GUIBoard[1][6] = (ImageView) findViewById(R.id.cellG2);
            GUIBoard[2][6] = (ImageView) findViewById(R.id.cellG3);
            GUIBoard[3][6] = (ImageView) findViewById(R.id.cellG4);
            GUIBoard[4][6] = (ImageView) findViewById(R.id.cellG5);
            GUIBoard[5][6] = (ImageView) findViewById(R.id.cellG6);
            GUIBoard[6][6] = (ImageView) findViewById(R.id.cellG7);
            GUIBoard[7][6] = (ImageView) findViewById(R.id.cellG8);
            GUIBoard[0][7] = (ImageView) findViewById(R.id.cellH1);
            GUIBoard[1][7] = (ImageView) findViewById(R.id.cellH2);
            GUIBoard[2][7] = (ImageView) findViewById(R.id.cellH3);
            GUIBoard[3][7] = (ImageView) findViewById(R.id.cellH4);
            GUIBoard[4][7] = (ImageView) findViewById(R.id.cellH5);
            GUIBoard[5][7] = (ImageView) findViewById(R.id.cellH6);
            GUIBoard[6][7] = (ImageView) findViewById(R.id.cellH7);
            GUIBoard[7][7] = (ImageView) findViewById(R.id.cellH8);
        }

        lastClickedTile = null;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                GUIBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tile temp = null;// = ChessBoard[i][j];
                        if (lastClickedTile != null && lastClickedTile.getPieceOwner() == 1){
                            if(lastClickedTile.movePieceTo(temp)){
                                //Successful move
                                //Send to opponent
                                SocketServerReplyThread send;
                                String message = "M "
                                        + String.valueOf(lastClickedTile.getxpos()) + String.valueOf(lastClickedTile.getypos())
                                        + " "
                                        + String.valueOf(temp.getxpos()) + String.valueOf(temp.getypos());

                                send = new SocketServerReplyThread(socket, 1, message);
                                send.run();

                            }
                            else{
                                SocketServerReplyThread send;
                                String message = "A";

                                send = new SocketServerReplyThread(socket, 1, message);
                                send.run();
                            }
                        }
                        if (temp.getPieceOwner() == 1)
                            lastClickedTile = temp;
                    }
                });
            }
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        static final int socketServerPort = 8080;
        int count =0;

        @Override
        public void run(){
            try{
                serverSocket = new ServerSocket(socketServerPort);
                MainActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        info.setText("I'm waiting here: "
                                + serverSocket.getLocalPort());
//                        ServerPort.setText(serverSocket.getLocalPort());
                    }
                });

                while (true) {
                    //Socket
                    socket = serverSocket.accept();
                    count++;
                    message += "#" + count + " from " + socket.getInetAddress()
                            + ":" + socket.getPort() + "\n";



                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            msg.setText(message);
                        }
                    });

                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                            socket, count, "Handshake"
                    );
                    socketServerReplyThread.run();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

private class SocketServerReplyThread extends Thread {
    String msgReply = "";
    private Socket hostThreadSocket;
    int cnt;

    SocketServerReplyThread(Socket socket, int c, String mesg) {
        hostThreadSocket = socket;
        cnt = c;
        msgReply = mesg;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        //msgReply = "Hello from Android, you are #" + cnt;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(msgReply);
            printStream.close();

            //message += "replayed: " + msgReply + "\n";

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    msg.setText(message);
                }
            });

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message += "Something wrong! " + e.toString() + "\n";
        }

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                msg.setText(message);
            }
        });
    }

}

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }
    /*public void SyncGame(String msg){

    }

    public void GameLoop(){
        for(;;){


        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
