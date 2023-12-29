package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class lobby extends AppCompatActivity {
    private TextView gold, exp, lvl, enemy_status, your_status;
    private Button reset_lvl, start_battle, buy_button;
    private SQLiteDatabase dbrw;
    private ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbrw = new MyDBHelper(this).getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        gold = findViewById(R.id.gold);
        exp = findViewById(R.id.exp);
        lvl = findViewById(R.id.lvl);
        Image = findViewById(R.id.imageView);
        enemy_status = findViewById(R.id.enemy_status);
        your_status = findViewById(R.id.your_status);
        reset_lvl = findViewById(R.id.reset_lvl);
        start_battle = findViewById(R.id.start_battle);
        buy_button = findViewById(R.id.buy_button);
        int gold=set_gold();
        set_exp();
        set_enemy();
        set_user();
        set_lvl();
        reset_lvl.setOnClickListener(v -> {
            try {
                dbrw.execSQL("UPDATE myTable SET value = " + 1 + " WHERE data LIKE 'LVL'");
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this,  e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            set_lvl();
            set_enemy();
        });
        start_battle.setOnClickListener(v -> {
            Intent intent = new Intent(this, battle.class);
            startActivity(intent);
        });
        buy_button.setOnClickListener(v -> {
            if(gold>=100){
                Intent intent = new Intent(this, end.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"你還繳不起學分費喔", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int set_gold() {
        Cursor cursor;
        cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'GOLD'", null);
        cursor.moveToFirst();
        int g=cursor.getInt(1);
        gold.setText("錢包:" + g);
        cursor.close();
        return g;
    }

    private void set_exp() {
        Cursor cursor;
        cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'EXP'", null);
        cursor.moveToFirst();
        exp.setText("經驗值:" + cursor.getInt(1) + "/100");
        cursor.close();
    }

    private void set_lvl() {
        Cursor cursor;
        cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'LVL'", null);
        cursor.moveToFirst();
        int level = cursor.getInt(1);
        lvl.setText("第" + level + "層");
        cursor.close();
        switch (level % 5) {
            case 1:
                Image.setImageResource(R.drawable.img_a);
                break;
            case 2:
                Image.setImageResource(R.drawable.img_b);
                break;
            case 3:
                Image.setImageResource(R.drawable.img_c);
                break;
            case 4:
                Image.setImageResource(R.drawable.img_d);
                break;
            default:
                Image.setImageResource(R.drawable.img_a);
        }
    }

    private void set_enemy() {
        Cursor cursor;
        cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'LVL'", null);
        cursor.moveToFirst();
        int lvl = cursor.getInt(1);
        cursor.close();
        int hp = 20 + lvl * 10;
        int atk = 3 + lvl;
        enemy_status.setText(" HP  :" + hp + "\n\nATK :" + atk);
    }

    private void set_user() {
        Cursor cursor_HP, cursor_ATK;
        cursor_HP = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'HP'", null);
        cursor_HP.moveToFirst();
        int HP = cursor_HP.getInt(1);
        cursor_HP.close();

        cursor_ATK = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'ATK'", null);
        cursor_ATK.moveToFirst();
        int ATK = cursor_ATK.getInt(1);
        cursor_ATK.close();
        your_status.setText(" HP  :" + HP + "\n\nATK :" + ATK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
}