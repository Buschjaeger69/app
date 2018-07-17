package com.example.johannes.wizard;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Johannes on 13.11.2017.
 */

public class Block extends AppCompatActivity {

    TextView t1;
    int weite;
    static int mitspieler = GameActivity.getMitspieler();
    static String[] namen = new String[6];
    static String[] ansage = new String[100];
    static String[] punkte = new String[100];
    static TableLayout t8;
    static TableRow tr;
    static TextView v1;
    static TextView v2;
    DataHandler pong;
    static int dauerzaehler = 0;
    int width;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.table_layout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = (int)(dm.widthPixels)/12;
        weite = GameActivity.getBis();
        pong = new DataHandler(this);
        pong.openWrite();
        pong.openRead();
        t8 = (TableLayout) findViewById(R.id.table);
        TextView t6 = findViewById(R.id.textView6);
        tr = new TableRow(this);
        v1 = new TextView(this);
        v2 = new TextView(this);
        Log.e("**********************+","Block erzeugen...");
        setNameTable();
        setTable();
        Log.e("**********************+","Block erzeugt");
        t6.setText("Zur Erklärung: \nIn der 2. Spalte befindet sich die angesagte Stichzahl jedes Spielers.\nIn der 1.Spalte befindet sich die Punktzahl" +
                " am Ende der jeweiligen Runde\nDas Zeichen / bedeutet, dass diese Runde noch läuft und deshalb keine Punkte eingetragen sind");
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.back);
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }




    public static void setWert(int id, String wert) {
        punkte[id] = wert;
    }

    public static void setNamen(int id, String wert) {
        namen[id] = wert;
    }

    public static void setAnsagen(int id, String wert) {
        ansage[id] = wert;
    }


    public void settable(int i) {
        Log.e("**********************+","settable ausgeführt");


        TableRow tr = new TableRow(this);

        for (int x = 0; x < mitspieler; x++) {
            Log.e("**********************+","Schritt 1");

            TextView v1 = new TextView(this);
            Log.e("**********************+","Wert von dauerzaehler: "+dauerzaehler);
            if (punkte[dauerzaehler] == null) {
                v1.setText("/");
                Log.e("**********************+","Alternativweg");
            } else {

                Log.e("**********************+","Punkte gesetzt: "+punkte[dauerzaehler]);
                v1.setText(punkte[dauerzaehler]);
            }

            v1.setBackgroundResource(R.drawable.umrandung);
            v1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            v1.setWidth(width);
            Log.e("**********************+","Schritt 2");
            tr.addView(v1);

            TextView v2 = new TextView(this);
            v2.setText(ansage[dauerzaehler]);
            v2.setBackgroundResource(R.drawable.umrandung);
            v2.setWidth(width);
            v2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tr.addView(v2);
            Log.e("**********************+","Schritt 3");
            dauerzaehler++;


        }
        Log.e("**********************+","Schritt 4");
        t8.addView(tr);


    }


    public void setTable() {

        int bis = GameActivity.getBis();
        Log.e("**********************+","Wert Variable bis: "+bis);
        for (int i = 0; i < bis; i++) {
            settable(i);
        }
    }

    public void setNameTable() {

        for(int i=0;i<mitspieler;i++){
            int id=0;
            switch(i) {
                case 0:
                    id = R.id.textView13;
                    break;
                case 1:
                    id = R.id.textView14;
                    break;
                case 2:
                    id = R.id.textView15;
                    break;
                case 3:
                    id = R.id.textView16;
                    break;
                case 4:
                    id = R.id.textView17;
                    break;
                case 5:
                    id = R.id.textView18;
                    break;
            }

                TextView t1 =(TextView) findViewById(id);
                t1.setText(namen[i]);
                t1.setBackgroundResource(R.drawable.umrandung);
                String s = pong.getName();
                if (namen[i].equals(s)) {
                    t1.setTextColor(getResources().getColor(R.color.spielername));

                }
                t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                t1.setVisibility(View.VISIBLE);
            }
        }

        public static void resetten(){
            Log.e("**********************+","Tabelle geleert");
            dauerzaehler=0;
            try {
                t8.removeAllViews();
            }catch(Exception e){

            }
        }



        /*
        String s="";
        TableLayout t8 = (TableLayout) findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        for (int i = 0; i < mitspieler; i++) {
            TextView v1 = new TextView(this);
            v1.setText(namen[i]);
            v1.setBackgroundResource(R.drawable.umrandung);
            if(namen[i].equals(Options.getName())){
                v1.setTextColor(getResources().getColor(R.color.spielername));

            }
            v1.setWidth(width*2);
            v1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(v1);
        }
        t8.addView(tr);
        */
    }

