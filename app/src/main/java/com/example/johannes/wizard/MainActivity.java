package com.example.johannes.wizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    DataHandler pong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main);
        layout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background));
        pong = new DataHandler(this);
        pong.openWrite();
        pong.openRead();
    }




    public void StartGameActivity(View view) {

        try{
            String s = pong.getName();
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        }catch(Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kein Benutzername vorhanden");
            builder.setMessage("Du musst zuerst einen Benutzernamen in den Einstellungen eingeben");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            builder.create().show();
        }
    }
    
    public void onBackPressed() {
        this.finishAffinity();
    }

    public void StartRulesActivity(View view) {
        Intent myIntent = new Intent(this, RulesActivity.class);
        startActivity(myIntent);
    }

    public void einstellungen(View view){
        Intent myIntent = new Intent(this, Options.class);
        startActivity(myIntent);
    }
}
