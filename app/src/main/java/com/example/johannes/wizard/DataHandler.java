package com.example.johannes.wizard;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Johannes on 05.08.2016.
 */
public class DataHandler {

    public static final String DB_NAME = "chat.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_names = "namen";
    public static final String TABLE_rounds = "rounds";
    public static final String COLUMN_Rounds  = "round";
    public static final String COLUMN_NAME ="name";
    public static final String COLUMN_ID ="id";

    public static final String SQL_CREATE ="create table if not exists namen (name text not null );";
    public static final String SQL_CREATE2 ="create table if not exists rounds (round integer );";
    private DataBaseHelper dbHelper;
    private Context ctx;
    private SQLiteDatabase db;

    public DataHandler(Context ctx) {
        this.ctx = ctx;
        dbHelper = new DataBaseHelper(ctx);
    }

    // Speichert in der Variable db unsere Datenbank mit Schreib-Rechten
    public void openWrite() {
        db = dbHelper.getWritableDatabase();
    }

    // Speichert in der Variable db unsere Datenbank mit Lese - Rechten
    public void openRead() {
        db = dbHelper.getReadableDatabase();
    }

    // Setzt Zugriffsrechte auf Datenbank zurueck
    public void close() {
        dbHelper.close();
    }





    public void insertName(String name)
    {
        //löscht bis jetzt alle Daten
        db.execSQL("DELETE FROM namen");
        db.execSQL("insert into namen (name) VALUES ('" + name + "');");
    }

    public void deleteData ()
    {
        db.execSQL("DELETE FROM messages");
    }

    public String getName()
    {
        Cursor c = db.rawQuery("select name from namen", null);
        c.moveToFirst();
        String name  =c.getString(0);
        return name;
    }

    public int getRunden()
    {
        Cursor c = db.rawQuery("select round from rounds", null);
        c.moveToFirst();
        int name  =c.getInt(0);
        return name;
    }
    public void insertRunde(int name)
    {
        db.execSQL("DELETE FROM rounds");
        db.execSQL("insert into rounds (round) VALUES ('" + name + "');");
    }

    public void setTheme(int ID)
    {
        db.execSQL("DELETE FROM theme");
        db.execSQL("insert into theme (id) VALUES ('" + ID + "');");
    }

    public ArrayList<String> list() {
        ArrayList<String> list = new ArrayList<String>();
        Cursor c = db.rawQuery("select message from messages", null);

        if (c.moveToFirst()) {
            list.add(c.getString(0));

            while (c.moveToNext()) {
                list.add(c.getString(0));
            }
        }
        return list;
    }




    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context ctx) {
            super(ctx, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE);
            db.execSQL(SQL_CREATE2);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }




}
