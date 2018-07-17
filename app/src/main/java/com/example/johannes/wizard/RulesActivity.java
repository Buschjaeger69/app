package com.example.johannes.wizard;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.net.Inet4Address;
        import java.net.InetAddress;
        import java.net.Socket;

        import static android.view.View.INVISIBLE;
        import static android.view.View.VISIBLE;
        import static java.lang.Integer.parseInt;

/**
 * Created by Johannes on 29.10.2017.
 */

public class RulesActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


    }

    public void vergroessern(View view){
        Intent myIntent = new Intent(this, testpopup.class);
        startActivity(myIntent);
    }


}
