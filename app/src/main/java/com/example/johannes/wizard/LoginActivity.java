package com.example.johannes.wizard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;



public class LoginActivity extends AppCompatActivity {
    static boolean abbrechen=false;
    static boolean weiter=false;
    static int serverport;
    static Socket client;
    DataHandler pong;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int )(70*dm.widthPixels)/100;
        int height = (int) (30*dm.heightPixels)/100;
        getWindow().setLayout(width,height);
        */
        pong = new DataHandler(this);
        pong.openWrite();
        pong.openRead();

        connect();
      //  hallo();
    }
    public void abbrechen(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        try {
            client.close();
        }catch(Exception e){
            Log.e("---Exception---",e.getMessage());
        }
    }

    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String host = "192.168.2.102";
                    int port=2018;
                    InetAddress adresse = Inet4Address.getByName(host);
                    Log.e("**********************+","Verbindung aufbauen: "+port+"|"+host);

                    client = new Socket(adresse, port);
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                    Log.e("**********************+","Socket: "+client.toString());




                        try {
                            output.println(pong.getRunden());
                            output.println(pong.getName());
                            serverport = Integer.parseInt(input.readLine());

                            startGame();

                            Log.e("**********************+","Login Activity beendet: "+client.isBound());
                            finish();
                        } catch (Exception e) {
                            Log.e("---Exception---",e.getMessage());
                        }

                } catch (Exception e) {
                    Log.e("---Exception---",e.getMessage());
                }

            }
        }).start();
    }




    public void startGame(){
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
        finish();
    }

    public static int getServerport(){
        return serverport;
    }


    static boolean getWeiter(){
        return weiter;
    }



        public void  hallo(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String s= GameActivity.spielstarten();
                        if(s.equals("sap")){
                            weiter=true;
                            finish();
                            break;
                        }
                    }
                }
            }).start();
        }





}
