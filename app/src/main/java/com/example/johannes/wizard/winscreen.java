package com.example.johannes.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Johannes on 10.01.2018.
 */

public class winscreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winscreen);
        TextView t = (TextView) findViewById(R.id.textView4);
        t.setText(GameActivity.getGewinner());
        schreibePunkte();
    }

    public void leave(View view) {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
    public void schreibePunkte(){
       TextView x = (TextView) findViewById(R.id.textView8);
       String [] arr = GameActivity.getSpielernamen();
       int [] arr2 = GameActivity.getPunktezahlen();
       String ausgabe="";
        for(int i=0;i<GameActivity.getMitspieler();i++){
            ausgabe=ausgabe+arr[i]+": "+arr2[i]+"\n";
        }
        x.setText(ausgabe);
    }
}
