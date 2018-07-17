package com.example.johannes.wizard;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Johannes on 22.02.2018.
 */

public class testpopup extends AppCompatActivity {
    DataHandler pong;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        pong = new DataHandler(this);
        pong.openWrite();
        pong.openRead();

        int hoehe;
        if(pong.getRunden()>3){

            hoehe=60;
        }
        else{
            hoehe=25;
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int )(50*dm.widthPixels)/100;
        int height = (int) (hoehe*dm.heightPixels)/100;
        getWindow().setLayout(width,height);
        ActionBar ab = getSupportActionBar();
        ab.hide();
    kartenSetzen();


    }
    public void kartenSetzen(){

        int [] karten = GameActivity.getKarten();

        Log.e("**********************+","Füllstand in testpop"+GameActivity.getFuellstand());
        for (int i=0;i<GameActivity.getFuellstand();i++){
            Log.e("**********************+","kartenarray in testpop:"+karten[i]);
            int s =R.id.imageView13+i;
            ImageView vw = (ImageView) findViewById(s);
            vw.setVisibility(View.VISIBLE);
            bildsetzen(vw,karten[i],false);
        }


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
