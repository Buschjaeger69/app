package com.example.johannes.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by Johannes on 06.02.2018.
 */

public class WaitActivity extends AppCompatActivity{
    static boolean weiter=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int )(70*dm.widthPixels)/100;
        int height = (int) (30*dm.heightPixels)/100;
        getWindow().setLayout(width,height);
        Log.e("**********************+","Wait Activity gestartet");
        abwarten();
    }
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
    public void abbrechen(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void  abwarten(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.e("**********************+","abwarten");
                    String s= GameActivity.spielstarten();

                    Log.e("**********************+","Nachricht: "+s);
                    if(s.equals("start")){
                        weiter=true;

                        Log.e("**********************+","Beenden WaitActivity");
                        finish();
                        break;
                    }
                }
            }
        }).start();
    }

    static boolean getWeiter(){
        return weiter;
    }

    static void setWeiter(boolean wert){
        weiter=wert;
    }
}
