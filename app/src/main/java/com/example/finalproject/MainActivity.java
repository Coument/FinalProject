package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase dbrw;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbrw=new MyDBHelper(this).getWritableDatabase();
        cursor=dbrw.rawQuery( "SELECT * FROM myTable",  null);
        cursor.moveToFirst();
        if(cursor.getCount()==0){
            reset_game();
        }
        cursor.close();

        Intent intent = new Intent();
        intent.setClass(MainActivity.this,battle.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
    protected void reset_game(){
        try {
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"LVL","1"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"ATK","1"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"HP","10"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"GOLD","0"});
            dbrw.execSQL("INSERT INTO myTable (data, value) values (?,?)",new Object[]{"EXP","0"});
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}