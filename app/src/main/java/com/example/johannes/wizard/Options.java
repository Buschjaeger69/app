package com.example.johannes.wizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by Johannes on 23.11.2017.
 */

public class Options extends AppCompatActivity {
    static String name="";
    TextView t2;
    DataHandler pong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        pong = new DataHandler(this);
        pong.openWrite();
        pong.openRead();


        setName();
        setMitspieler();
}
    public void bestaetigen(View view){


            EditText t1 = (EditText) findViewById(R.id.editText2);
            String eingabe= t1.getText().toString();
        if(eingabe.isEmpty()|| eingabe.length()<4){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ungültiger Benutzername");
            builder.setMessage("Benutzername muss mindestens 4 Zeichen enthalten");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                }
            });
            builder.create().show();
        }else {
            pong.insertName(eingabe);
            t1.setText("");
            setName();
        }



    }
public void setName(){
    t2=findViewById(R.id.textView5);
    try {
        t2.setText(pong.getName());
    }catch (Exception e){

    }
}

public void setRundenzahl(View view){
    EditText t = (EditText) findViewById(R.id.editText10);
    int anzahl=Integer.parseInt(t.getText().toString());
    pong.insertRunde(anzahl);
    t.setText("");
    setMitspieler();

}

public void setMitspieler(){
    t2=findViewById(R.id.textView2);
    try{
        t2.setText(""+pong.getRunden());
    }catch (Exception e){

    }
}



public static String getName(){
    return name;
}
}
