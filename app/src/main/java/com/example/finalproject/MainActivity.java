package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase dbrw;
    private Button new_game,continue_game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbrw=new MyDBHelper(this).getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new_game=findViewById(R.id.new_game);
        continue_game=findViewById(R.id.continue_game);
        Cursor cursor;
        cursor=dbrw.rawQuery( "SELECT * FROM myTable",  null);
        cursor.moveToFirst();
        if(cursor.getCount()==0){
            new_game();
        }
        cursor.close();
        new_game.setOnClickListener(v -> {
            reset_game();
            Intent intent = new Intent(this,lobby.class);
            startActivity(intent);
        });
        continue_game.setOnClickListener(v -> {
            Intent intent = new Intent(this,lobby.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
    protected void new_game(){
        try {
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"LVL","1"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"ATK","5"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"HP","20"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"GOLD","0"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"EXP","0"});
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void reset_game(){
        try {
            dbrw.execSQL("UPDATE myTable SET value = " + 1 + " WHERE data LIKE 'LVL'");
            dbrw.execSQL("UPDATE myTable SET value = " + 5 + " WHERE data LIKE 'ATK'");
            dbrw.execSQL("UPDATE myTable SET value = " + 20 + " WHERE data LIKE 'HP'");
            dbrw.execSQL("UPDATE myTable SET value = " + 0 + " WHERE data LIKE 'GOLD'");
            dbrw.execSQL("UPDATE myTable SET value = " + 0 + " WHERE data LIKE 'EXP'");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}