package com.example.johannes.wizard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.Integer.parseInt;

/**
 * Created by Johannes on 29.10.2017.
 */

public class GameActivity extends AppCompatActivity {
    int zaehler = 0;
    ImageView img;
    TextView rundenanzeige;
    final int rundenzahl = 0;
    boolean wait = true;
    static int stichansage = 0;
    int gemachteStiche = 0;
    DataHandler pong;
    int id = 0;
    static int mitspieler;
    int ausgewaehlt = 0;
    int[] kartenblatt;
    static int stichanzeigenzaehler;
    static boolean erlaubt= false;
    MenuItem item;
    MenuItem anzeige;

    int hoechsteKarte = 0;
    Button b1;
    static int [] gelegteKarten;
    static int fuellstand_gelegteKarten=0;
    Object lock;
    Socket client;
    PrintWriter output;
    String message;
    static int []punktezahlen;
    static int [] kartenstapel;
    String ausgabe = "";
    static int blockzaehler=0;
    static String[] spielernamen;
    static String gewinner;
    int[] array = new int[10];
    static BufferedReader input = null;
    int befehl = 0;
    Button kartenAuswahlBestätigen;
    static int rundenzaehler = 1;
    static int punkteanzeigezaehler = 0;
    static int [] stiche;
    TextView t3;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rundenanzeige = (TextView) findViewById(R.id.textView);
        pong = new DataHandler(this);
        pong.openWrite();
        pong.openRead();
        mitspieler=pong.getRunden();
        kartenblatt = new int[60/mitspieler];
         gelegteKarten= new int[10];
         punktezahlen= new int[10];
         kartenstapel= new int[10];
        spielernamen = new String[mitspieler];
        stiche = new int[mitspieler];
        lock = new Object();
        b1 = (Button) findViewById(R.id.button2);
        t3 = (TextView) findViewById(R.id.textView);
         item = (MenuItem) findViewById(R.id.runde);
         anzeige = (MenuItem) findViewById(R.id.anweisung);
        kartenAuswahlBestätigen = (Button) findViewById(R.id.button);
        Button b1 = (Button) findViewById(R.id.button);
        b1.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        Farbzugabe();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        connect();
        Intent myIntent = new Intent(this, WaitActivity.class);
        startActivity(myIntent);

    }

    public static int[] getKartenstapel(){
        return kartenstapel;
    }
public static int getMitspieler(){return mitspieler;}
    public static int getBis() {
        return blockzaehler;
    }


    public static int [] getKarten(){
        Log.e("**********************+","kartenarray in GameActivity:"+gelegteKarten[0]);
        Log.e("**********************+","kartenarray in GameActivity:"+gelegteKarten[1]);
    return gelegteKarten;
    }

    public static int getFuellstand(){
        return fuellstand_gelegteKarten;
    }

    public void startPopUp(View view){
        Intent myIntent = new Intent(this, testpopup.class);
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wollen sie das Spiel wirklich beenden?");
        builder.setMessage("");
        builder.setPositiveButton("Zurück", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("Ragequit bestätigen", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    public static String spielstarten(){
        String s="";
        Log.e("**********************+","vorher");
    try{ s= input.readLine();}
    catch (Exception e){
        Log.e("**********************+","Exi");
    }
        Log.e("**********************+","fertig");
    return s;
    }

    public static String getGewinner(){
        return spielernamen[parseInt(gewinner)];
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gamemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        item = menu.findItem(R.id.runde);
        anzeige = menu.findItem(R.id.anweisung);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.block:
                StartBlock();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void StartBlock() {
        Block.dauerzaehler=0;
        Intent myIntent = new Intent(this, Block.class);
        startActivity(myIntent);
    }


    public  void setErlaubnis(int z1,int z2, ImageView vw){
        ausgewaehlt = z1;
        datenanzeigen(kartenblatt[z2]);
        kartenAuswahlBestätigen.setEnabled(true);
        Context mContext = getApplicationContext();
        Animation anim = (AnimationUtils.loadAnimation(mContext, R.anim.slide_up));
        vw.startAnimation(anim);
        if(erlaubt){
            Button b1 = (Button) findViewById(R.id.button);
            b1.setVisibility(View.VISIBLE);
            erlaubt=false;
        }

    }

    public void spielkartenAnzeigen(int anzahl, int kartennummer) {

        int imgView = 0;

        switch (anzahl) {
            case 1:
                imgView = R.id.imageView1;
                break;
            case 2:
                imgView = R.id.imageView2;
                break;
            case 3:
                imgView = R.id.imageView3;
                break;
            case 4:
                imgView = R.id.imageView4;
                break;
            case 5:
                imgView = R.id.imageView5;
                break;
            case 6:
                imgView = R.id.imageView6;
                break;
            case 7:
                imgView = R.id.imageView7;
                break;
            case 8:
                imgView = R.id.imageView8;
                break;
            case 9:
                imgView = R.id.imageView9;
                break;
            case 10:
                imgView = R.id.imageView10;
                break;
        }
        ImageView v1 = (ImageView) findViewById(imgView);
        bildsetzen(v1, kartennummer,true);
        v1.setVisibility(View.VISIBLE);
    }


    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String host = "192.168.2.102";

                    int port = LoginActivity.getServerport();
                    Log.e("**********************+","Port: "+port);
                    InetAddress adresse = Inet4Address.getByName(host);

                    client = new Socket(adresse, port);


                    input = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    output = new PrintWriter(client.getOutputStream(), true);


                    Log.e("**********************+","Socket: "+client.toString());
                    Log.e("**********************+","Socket ist: "+client.isBound());
                    //Hiermit funktioniert Benutzerangabe nicht mehr
                    while(true){

                        if(WaitActivity.getWeiter()){
                            Log.e("**********************+","Erlaubnis zum spielstarten erhalten");
                            break;
                        }
                    }

                    anmelden();
                    Log.e("**********************+","am Server angemeldet");
                    while (true) {

                        String s = input.readLine();
                        if(s.equals("start")){
                            Log.e("**********************+","start-String abgefangen");
                            befehl=12;
                        }else {
                            befehl = Integer.parseInt(s);
                        }
                        Log.e("-----------------------","Befehl vom Server: "+befehl);

                        switch (befehl) {
                            case 1:

                                for (int i = 0; i < mitspieler; i++) {

                                    spielernamen[i] = input.readLine();
                                    Block.setNamen(i, spielernamen[i]);

                                }
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        spielerAnzeigen();

                                    }

                                });
                                break;
                            case 2:

                                for(int z=0;z<mitspieler;z++) {
                                    final int id;
                                    switch (z) {
                                        case 0:
                                            id = R.id.textView9;
                                            break;
                                        case 1:
                                            id = R.id.textView19;
                                            break;
                                        case 2:
                                            id = R.id.textView11;
                                            break;
                                        case 3:
                                            id = R.id.textView20;
                                            break;
                                        case 4:
                                            id = R.id.textView12;
                                            break;
                                        case 5:
                                            id = R.id.textView21;
                                            break;
                                        default:
                                            id = -1;
                                    }
                                    final int x = z;

                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            TextView t1 = findViewById(id);
                                            t1.setText(spielernamen[x]);

                                        }

                                    });

                                    for (int c = 0; c < stiche.length; c++) {
                                        stiche[c] = 0;
                                        Log.e("**********************+", "Wert stichzähler: " + c);
                                    }
                                }

                                runOnUiThread(new Runnable() {
                                    public void run() {


                                        t3.setText("Bitte eine Ansage machen");

                                    }

                                });

                                break;
                            case 3:

                                for (int i = 0; i < rundenzaehler; i++) {
                                    Log.e("**********************+","rundenzähler in GameActivity: "+rundenzaehler);
                                    final int z = i + 1;
                                    final int x = i;


                                    try {
                                        kartenblatt[i] = Integer.parseInt(input.readLine());

                                        Log.e("**********************+","Karte von Server erhalten: "+kartenblatt[i]);
                                        runOnUiThread(new Runnable() {
                                            public void run() {

                                                spielkartenAnzeigen(z, kartenblatt[x]);
                                            }

                                        });


                                    } catch (IOException e) {
                                        Log.e("**********************+","Karte von Server erhalten ist fehlgeschlagen") ;
                                    }


                                }


                                break;

                            case 4:
                                for (int i = 0; i < mitspieler; i++) {
                                    String ansage = input.readLine();
                                    Block.setAnsagen(stichanzeigenzaehler, ansage);
                                    stichanzeigenzaehler++;
                                }
                                blockzaehler++;
                                break;
                            case 5:

                                hoechsteKarte = Integer.parseInt(input.readLine());


                                // item.setTitle(input.readLine());


                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        ImageView vw1 = (ImageView) findViewById(R.id.imageView);
                                        bildsetzen(vw1, hoechsteKarte,false);
                                        Card.farbkartenzurueck();
                                        Farbzugabe();
                                    }

                                });

                                // Originale Spielernamen wieder anzeigen




                                break;
                            case 6:

                                int stich = Integer.parseInt(input.readLine());
                                if ( stich== 1) {
                                    gemachteStiche++;
                                }

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        anzeige.setTitle("Stiche: " + gemachteStiche);
                                    } });

                                gelegteKarten=new int [mitspieler];
                                fuellstand_gelegteKarten=0;
                                break;

                            case 7:
                                String punktzahl;
                                for (int i = 0; i < mitspieler; i++) {
                                    punktzahl = input.readLine();
                                    Block.setWert(punkteanzeigezaehler, punktzahl);
                                    punkteanzeigezaehler++;
                                }
                                rundenzaehler++;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        EditText x1 = (EditText) findViewById(R.id.editText);
                                        x1.setVisibility(View.VISIBLE);
                                        Button x2 = (Button) findViewById(R.id.button2);
                                        x2.setVisibility(View.VISIBLE);
                                        Button b1 = (Button) findViewById(R.id.button);
                                        b1.setVisibility(View.INVISIBLE);
                                        item.setTitle(""+rundenzaehler);
                                        anzeige.setTitle("Stiche: 0");
                                        gemachteStiche=0;

                                    }


                                });

                                /*hoechsteKarte=0;
                            ImageView iv2 = (ImageView) findViewById(R.id.imageView);
                            bildsetzen(iv2,hoechsteKarte);
                            */

                                break;

                            case 8:
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        t3.setText("Bitte eine Karte legen");

                                    }

                                });

                                erlaubt=true;
                                break;
                            case 9:
                                /*
                                int x = pong.getRunden();
                                Context c = getApplicationContext();
                                Toast toast = Toast.makeText(c, x, Toast.LENGTH_LONG);
                                toast.show();
                                output.println(pong.getRunden());
                                */
                                break;

                            case 10:

                                final int t = parseInt(input.readLine());
                                            runOnUiThread(new Runnable() {
                                                              public void run() {


                                                                     int aktuellerSpieler = t;
                                                                  TextView t2 = findViewById(R.id.textView20);
                                                                  t2.setVisibility(View.VISIBLE);
                                                                  t2.setText("t: "+t);
                                                                  for(int i=0;i<mitspieler;i++) {


                                                                      switch (i) {
                                                                          case 0:
                                                                              id = R.id.textView9;
                                                                              break;
                                                                          case 1:
                                                                              id = R.id.textView19;
                                                                              break;
                                                                          case 2:
                                                                              id = R.id.textView11;
                                                                              break;
                                                                          case 3:
                                                                              id = R.id.textView20;
                                                                              break;
                                                                          case 4:
                                                                              id = R.id.textView12;
                                                                              break;
                                                                          case 5:
                                                                              id = R.id.textView21;
                                                                              break;

                                                                      }
                                                                      TextView t1 = findViewById(id);

                                                                      if(i==aktuellerSpieler){
                                                                          t1.setTextColor(getResources().getColor(R.color.spielername));
                                                                      }
                                                                      else{
                                                                          t1.setTextColor(getResources().getColor(R.color.black));
                                                                      }
                                                              }
                                                          }
                                            });

                                break;
                            case 11:
                                gewinner= input.readLine();
                                for(int i=0;i<mitspieler;i++){
                                    punktezahlen[i]=parseInt(input.readLine());
                                }
                                resetGame();
                                input.close();
                                output.close();
                                client.close();
                                rundenzaehler=1;
                                WaitActivity.setWeiter(false);
                                test();
                                Block.resetten();
                                Log.e("**********************+","GameActivity beendet");
                                startEnde();
                                Log.e("**********************+","Start ende ausgeführt");

                                break;


                            case 12:
                                Log.e("**********************+","Absturz abgefangen!!!");
                                WaitActivity.setWeiter(true);
                                break;

                            case 13:
                                gelegteKarten[fuellstand_gelegteKarten]= parseInt(input.readLine());
                                Log.e("####################","gespielteKarte: "+gelegteKarten[fuellstand_gelegteKarten]);
                                fuellstand_gelegteKarten++;
                                Log.e("####################","Füllstand: "+fuellstand_gelegteKarten);
                                break;

                            case 14:
                                final int rundengewinner = parseInt(input.readLine());
                                stiche[rundengewinner]++;
                                Log.e("*#*#*#*#*#*#*#*#*#*#*#","rundengewinner:" +rundengewinner);
                               final int id;
                                switch(rundengewinner){
                                    case 0:
                                        id = R.id.textView9;
                                        break;
                                    case 1:
                                        id = R.id.textView19;
                                        break;
                                    case 2:
                                        id = R.id.textView11;
                                        break;
                                    case 3:
                                        id = R.id.textView20;
                                        break;
                                    case 4:
                                        id = R.id.textView12;
                                        break;
                                    case 5:
                                        id = R.id.textView21;
                                        break;
                                    default: id=0;
                                }


                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        TextView t1 = findViewById(id);
                                        String original = t1.getText().toString();
                                        Log.e("*#*#*#*#*#*#*#*#*#*#*#","originaltext:" +original);
                                        String neu= original+": "+stiche[rundengewinner];
                                        Log.e("*#*#*#*#*#*#*#*#*#*#*#","neuer Text:" +neu);
                                        t1.setText(neu);

                                    }

                                });

                                break;
                        }
                       /* runOnUiThread(new Runnable() {
                            public void run(){


                            }

                        });
                        */


                        // if(message.equals("/start game")){
                        //      for(int i=0;i<10;i++) {
                        //        try {
                        //          String msg = input.readLine();
                        //        array[i]= parseInt(msg);


                        //  catch (IOException e) {
                        //              System.out.println(e);
                        //
                        // }
                        //    }
                        //}

                        //    }


                    }
                }

                       /*runOnUiThread(new Runnable() {

                            public void run() {


                            }

                        });
                     */ catch (IOException e) {
                    System.out.println(e);
                }
            }
        }).start();


    }



    public void setAktuellerSpieler(boolean eins, int id){

    }

    public void test(){
        finish();
    }

    public static void resetGame(){
        Block.punkte=new String[100];
        Block.dauerzaehler=0;
    }

    public void spielerAnzeigen(){
        for(int i=0;i<mitspieler;i++) {
            int id = 0;
            switch (i) {
                case 0:
                    id = R.id.textView9;
                    break;
                case 1:
                    id = R.id.textView19;
                    break;
                case 2:
                    id = R.id.textView11;
                    break;
                case 3:
                    id = R.id.textView20;
                    break;
                case 4:
                    id = R.id.textView12;
                    break;
                case 5:
                    id = R.id.textView21;
                    break;

            }
            String text = spielernamen[i];
            if(spielernamen[i].equals(pong.getName())){
                text=text+" (You)";
            }
            TextView t1 = findViewById(id);
            t1.setVisibility(View.VISIBLE);
            t1.setText(text);
        }
    }

    public static String [] getSpielernamen(){
        return spielernamen;
    }

    public static int [] getPunktezahlen(){
        return punktezahlen;
    }

    public void anmelden() {

        String name = pong.getName();
        output.println(name);
        output.flush();
        Log.e("-----------------------","Benutzername gesendet");


    }


    public void stichauswahlBestaetigen(View view) {
        EditText t1 = (EditText) findViewById(R.id.editText);
        String s1 = t1.getText().toString();
        int n1 = 0;
        boolean weiter = true;

        if (s1.isEmpty()) {
            weiter = false;
        } else {
            n1 = Integer.parseInt(s1);
        }
        if (weiter) {
            if (n1 < 0 || n1 > rundenzaehler) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Stichansage");
                builder.setMessage("Sie haben eine ungültige Stichanzahl angegeben");
                builder.setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.create().show();
            } else {
                b1.setVisibility(View.INVISIBLE);
                t1.setVisibility(View.INVISIBLE);
                t1.setText("");
                output.println(n1);
                output.flush();
                t3.setText("Warten auf Mitspieler");

            }


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Stichansage");
            builder.setMessage("Sie haben eine ungültige Stichanzahl angegeben");
            builder.setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                }
            });

            builder.create().show();
        }


    }

    public static int getStichanzahl() {
        return stichansage;
    }

public void startEnde(){
    Intent myIntent = new Intent(this, winscreen.class);
    startActivity(myIntent);
}
    public void karteAuswaehlen(View view) {
        switch (ausgewaehlt) {
            case 1:
                id = R.id.imageView1;
                break;
            case 2:
                id = R.id.imageView2;
                break;
            case 3:
                id = R.id.imageView3;
                break;
            case 4:
                id = R.id.imageView4;
                break;
            case 5:
                id = R.id.imageView5;
                break;
            case 6:
                id = R.id.imageView6;
                break;
            case 7:
                id = R.id.imageView7;
                break;
            case 8:
                id = R.id.imageView8;
                break;
            case 9:
                id = R.id.imageView9;
                break;
            case 10:
                id = R.id.imageView10;
                break;
        }

        ImageView vw = (ImageView) findViewById(id);
        vw.setVisibility(View.GONE);
        int index = ausgewaehlt - 1;

        kartenAuswahlBestätigen.setEnabled(false);
        Button b1 = (Button) findViewById(R.id.button);
        b1.setVisibility(View.INVISIBLE);

        output.println(kartenblatt[index]);
        output.flush();
        kartenblatt[index] = 0;
        t3.setText("Warten auf Mitspieler");
        erlaubt=false;

    }


    public void Farbzugabe() {
        for (int i = 0; i < rundenzaehler; i++) {
            switch (i + 1) {
                case 1:
                    id = R.id.imageView1;
                    break;
                case 2:
                    id = R.id.imageView2;
                    break;
                case 3:
                    id = R.id.imageView3;
                    break;
                case 4:
                    id = R.id.imageView4;
                    break;
                case 5:
                    id = R.id.imageView5;
                    break;
                case 6:
                    id = R.id.imageView6;
                    break;
                case 7:
                    id = R.id.imageView7;
                    break;
                case 8:
                    id = R.id.imageView8;
                    break;
                case 9:
                    id = R.id.imageView9;
                    break;
                case 10:
                    id = R.id.imageView10;
                    break;
            }

            try {
                if (Card.Farbzugabe(hoechsteKarte, kartenblatt[i])) {

                    ImageView vw = (ImageView) findViewById(id);
                    vw.setEnabled(true);
                    bildsetzen(vw, kartenblatt[i], false);
                } else {

                    ImageView vw = (ImageView) findViewById(id);
                    vw.setEnabled(false);
                    vw.setImageResource(R.drawable.c00);
                }
            }catch(Exception e){
                t3.setText("Spiel ist zu Ende");
            }
        }

        if (Card.getFarbzugabe() == 0) {
            for (int i = 0; i < 10; i++) {
                switch (i + 1) {
                    case 1:
                        id = R.id.imageView1;
                        break;
                    case 2:
                        id = R.id.imageView2;
                        break;
                    case 3:
                        id = R.id.imageView3;
                        break;
                    case 4:
                        id = R.id.imageView4;
                        break;
                    case 5:
                        id = R.id.imageView5;
                        break;
                    case 6:
                        id = R.id.imageView6;
                        break;
                    case 7:
                        id = R.id.imageView7;
                        break;
                    case 8:
                        id = R.id.imageView8;
                        break;
                    case 9:
                        id = R.id.imageView9;
                        break;
                    case 10:
                        id = R.id.imageView10;
                        break;

                }

                ImageView vw = (ImageView) findViewById(id);
                vw.setEnabled(true);
                bildsetzen(vw, kartenblatt[i],false);

            }
        }
    }

    public void imageone(View view) {

        ImageView vw = (ImageView) findViewById(R.id.imageView1);
        setErlaubnis(1,0,vw);
    }

    public void imagetwo(View view) {
        ImageView vw = (ImageView) findViewById(R.id.imageView2);
        setErlaubnis(2,1,vw);


    }

    public void imagethree(View view) {
        ImageView vw = (ImageView) findViewById(R.id.imageView3);
        setErlaubnis(3,2,vw);

    }

    public void imagefour(View view) {
        ImageView vw = (ImageView) findViewById(R.id.imageView4);
        setErlaubnis(4,3,vw);


    }

    public void imagefive(View view) {


        ImageView vw = (ImageView) findViewById(R.id.imageView5);
        setErlaubnis(5,4,vw);
    }

    public void imagesix(View view) {


        ImageView vw = (ImageView) findViewById(R.id.imageView6);
        setErlaubnis(6,5,vw);
    }

    public void imageseven(View view) {


        ImageView vw = (ImageView) findViewById(R.id.imageView7);
        setErlaubnis(7,6,vw);
    }

    public void imageeight(View view) {


        ImageView vw = (ImageView) findViewById(R.id.imageView8);
        setErlaubnis(8,7,vw);
    }

    public void imagenine(View view) {


        ImageView vw = (ImageView) findViewById(R.id.imageView9);
        setErlaubnis(9,8,vw);
    }

    public void imageten(View view) {


        ImageView vw = (ImageView) findViewById(R.id.imageView10);
        setErlaubnis(10,9,vw);
    }


    public void wait(View view) {


        /*
    Card.vorbereitung();
    int karte1= Card.karteziehen();
    int karte2 = Card.karteziehen();

*/



    /* Meldung zum Stiche eingeben
    final EditText input;
    final TextView stichvorhersage = (TextView) findViewById(R.id.textView3);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Stichansage");
    builder.setMessage("Bitte geben Sie ihre Stichanzahl ein");
     input = new EditText(this);
    builder.setView(input);
        builder.setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                txt = input.getText().toString();
                stichvorhersage.setText("Stichvorhersage: \n"+txt);
            }
        });
        builder.setNeutralButton("Karten anschauen",null);


builder.create().show();


*/
    }

    public void KartenAnzeigen() {
        ImageView vw1 = (ImageView) findViewById(R.id.imageView);
        bildsetzen(vw1, hoechsteKarte,false);
        for (int i = 1; i <= rundenzaehler; i++) {

            switch (i) {
                case 1:
                    id = R.id.imageView1;
                    break;
                case 2:
                    id = R.id.imageView2;
                    break;
                case 3:
                    id = R.id.imageView3;
                    break;
                case 4:
                    id = R.id.imageView4;
                    break;
                case 5:
                    id = R.id.imageView5;
                    break;
                case 6:
                    id = R.id.imageView6;
                    break;
                case 7:
                    id = R.id.imageView7;
                    break;
                case 8:
                    id = R.id.imageView8;
                    break;
                case 9:
                    id = R.id.imageView9;
                    break;
                case 10:
                    id = R.id.imageView10;
                    break;
            }
            ImageView vw = (ImageView) findViewById(id);
            vw.setVisibility(View.VISIBLE);
            bildsetzen(vw, kartenblatt[i],false);
        }
    }


    public void datenanzeigen(int kartennr) {
        int gruppe = 0;
        int farbgruppe = 0;
        int kartenwert = 0;
        Card.kartenzuordnung(kartennr);
        gruppe = Card.getGruppe();
        farbgruppe = Card.getFarbgruppe();
        kartenwert = Card.getKartenwert();

    }


    public void bildsetzen(ImageView id, int kartennr, boolean animation) {

        switch (kartennr) {
            case 0:
                id.setImageResource(R.drawable.c00);
                break;
            case 1:
                id.setImageResource(R.drawable.c01);
                break;
            case 2:
                id.setImageResource(R.drawable.c02);
                break;
            case 3:
                id.setImageResource(R.drawable.c03);
                break;
            case 4:
                id.setImageResource(R.drawable.c04);
                break;
            case 5:
                id.setImageResource(R.drawable.c05);
                break;
            case 6:
                id.setImageResource(R.drawable.c06);
                break;
            case 7:
                id.setImageResource(R.drawable.c07);
                break;
            case 8:
                id.setImageResource(R.drawable.c08);
                break;
            case 9:
                id.setImageResource(R.drawable.c09);
                break;
            case 10:
                id.setImageResource(R.drawable.c10);
                break;
            case 11:
                id.setImageResource(R.drawable.c11);
                break;
            case 12:
                id.setImageResource(R.drawable.c12);
                break;
            case 13:
                id.setImageResource(R.drawable.c13);
                break;
            case 14:
                id.setImageResource(R.drawable.c14);
                break;
            case 15:
                id.setImageResource(R.drawable.c15);
                break;
            case 16:
                id.setImageResource(R.drawable.c16);
                break;
            case 17:
                id.setImageResource(R.drawable.c17);
                break;
            case 18:
                id.setImageResource(R.drawable.c18);
                break;
            case 19:
                id.setImageResource(R.drawable.c19);
                break;
            case 20:
                id.setImageResource(R.drawable.c20);
                break;
            case 21:
                id.setImageResource(R.drawable.c21);
                break;
            case 22:
                id.setImageResource(R.drawable.c22);
                break;
            case 23:
                id.setImageResource(R.drawable.c23);
                break;
            case 24:
                id.setImageResource(R.drawable.c24);
                break;
            case 25:
                id.setImageResource(R.drawable.c25);
                break;
            case 26:
                id.setImageResource(R.drawable.c26);
                break;
            case 27:
                id.setImageResource(R.drawable.c27);
                break;
            case 28:
                id.setImageResource(R.drawable.c28);
                break;
            case 29:
                id.setImageResource(R.drawable.c29);
                break;
            case 30:
                id.setImageResource(R.drawable.c30);
                break;
            case 31:
                id.setImageResource(R.drawable.c31);
                break;
            case 32:
                id.setImageResource(R.drawable.c32);
                break;
            case 33:
                id.setImageResource(R.drawable.c33);
                break;
            case 34:
                id.setImageResource(R.drawable.c34);
                break;
            case 35:
                id.setImageResource(R.drawable.c35);
                break;
            case 36:
                id.setImageResource(R.drawable.c36);
                break;
            case 37:
                id.setImageResource(R.drawable.c37);
                break;
            case 38:
                id.setImageResource(R.drawable.c38);
                break;
            case 39:
                id.setImageResource(R.drawable.c39);
                break;
            case 40:
                id.setImageResource(R.drawable.c40);
                break;
            case 41:
                id.setImageResource(R.drawable.c41);
                break;
            case 42:
                id.setImageResource(R.drawable.c42);
                break;
            case 43:
                id.setImageResource(R.drawable.c43);
                break;
            case 44:
                id.setImageResource(R.drawable.c44);
                break;
            case 45:
                id.setImageResource(R.drawable.c45);
                break;
            case 46:
                id.setImageResource(R.drawable.c46);
                break;
            case 47:
                id.setImageResource(R.drawable.c47);
                break;
            case 48:
                id.setImageResource(R.drawable.c48);
                break;
            case 49:
                id.setImageResource(R.drawable.c49);
                break;
            case 50:
                id.setImageResource(R.drawable.c50);
                break;
            case 51:
                id.setImageResource(R.drawable.c51);
                break;
            case 52:
                id.setImageResource(R.drawable.c52);
                break;
            case 53:
                id.setImageResource(R.drawable.c53);
                break;
            case 54:
                id.setImageResource(R.drawable.c54);
                break;
            case 55:
                id.setImageResource(R.drawable.c55);
                break;
            case 56:
                id.setImageResource(R.drawable.c56);
                break;
            case 57:
                id.setImageResource(R.drawable.c57);
                break;
            case 58:
                id.setImageResource(R.drawable.c58);
                break;
            case 59:
                id.setImageResource(R.drawable.c59);
                break;
            case 60:
                id.setImageResource(R.drawable.c60);
                break;

        }
        if (animation) {
            Context mContext = getApplicationContext();
            Animation anim = (AnimationUtils.loadAnimation(mContext, R.anim.scale_up));
            id.startAnimation(anim);
        }
    }
}


