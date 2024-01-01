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
public class battle extends AppCompatActivity {
    private Button run,btn_scissor,btn_stone,btn_paper;
    private TextView layers,enemy_hp,your_hp;
    private ImageView Image;
    private SQLiteDatabase dbrw;
    int choice=0,temp_enemy_hp=0,temp_your_hp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbrw=new MyDBHelper(this).getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Image =findViewById(R.id.imageView);
        btn_scissor=findViewById(R.id.btn_scissor);
        btn_paper=findViewById(R.id.btn_paper);
        btn_stone=findViewById(R.id.btn_stone);
        run=findViewById(R.id.run);
        layers=findViewById(R.id.layers);
        enemy_hp=findViewById(R.id.enemy_hp);
        your_hp=findViewById(R.id.your_hp);

        int lvl=set_lvl();
        switch (lvl%5){
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
                Image.setImageResource(R.drawable.img);
        }

        int your_now_hp=setYour_hp();
        int enemy_now_hp=set_enemy();
        int enemy_atk=3+lvl;
        int your_atk=getyour_atk();
        temp_enemy_hp=enemy_now_hp;
        temp_your_hp=your_now_hp;

        btn_paper.setOnClickListener(v -> {
            Toast.makeText(this,"你選擇的是【布】",Toast.LENGTH_SHORT).show();
            choice=0;
        });
        btn_stone.setOnClickListener(v -> {
            Toast.makeText(this,"你選擇的是【石頭】",Toast.LENGTH_SHORT).show();
            choice=1;
        });
        btn_scissor.setOnClickListener(v -> {
            Toast.makeText(this,"你選擇的是【剪刀】",Toast.LENGTH_SHORT).show();
            choice=2;
        });
        run.setOnClickListener(v -> {
            try {
                Thread.sleep(2000);
                int rand=(int)(Math.random()*3);
                if((choice==0 && rand==1)||(choice==2 && rand==0)||(choice==1 && rand==2)){
                    result(rand,"你贏了");
                    temp_enemy_hp=temp_enemy_hp-your_atk;
                    enemy_hp.setText(temp_enemy_hp+"HP"+"/"+enemy_now_hp+"HP");
                }
                else if ((choice==0 && rand==2)||(choice==2 && rand==1)||(choice==1 && rand==0)) {
                    result(rand,"你輸了");
                    temp_your_hp=temp_your_hp-enemy_atk;
                    your_hp.setText(temp_your_hp+"HP"+"/"+your_now_hp+"HP");
                }
                else {
                    result(rand,"平手");
                    temp_your_hp=temp_your_hp-lvl;
                    temp_enemy_hp=temp_enemy_hp-lvl;
                    enemy_hp.setText(temp_enemy_hp+"HP"+"/"+enemy_now_hp+"HP");
                    your_hp.setText(temp_your_hp+"HP"+"/"+your_now_hp+"HP");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(temp_enemy_hp<=0){
                Toast.makeText(this,"恭喜你擊敗了敵人", Toast.LENGTH_SHORT).show();
                int next_lvl=lvl+1;
                Cursor cursor;
                cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'EXP'", null);
                cursor.moveToFirst();
                int next_exp=cursor.getInt(1)+(lvl-1)*5+20;
                cursor.close();

                cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'GOLD'", null);
                cursor.moveToFirst();
                int next_gold=cursor.getInt(1)+((lvl/5)+1)*100;
                cursor.close();
                try {
                    dbrw.execSQL("UPDATE myTable SET value = " + next_lvl + " WHERE data LIKE 'LVL'");
                    dbrw.execSQL("UPDATE myTable SET value = " + next_exp + " WHERE data LIKE 'EXP'");
                    dbrw.execSQL("UPDATE myTable SET value = " + next_gold + " WHERE data LIKE 'GOLD'");
                    if(next_exp>=100){
                        next_exp=next_exp-100;
                        dbrw.execSQL("UPDATE myTable SET value = " + next_exp + " WHERE data LIKE 'EXP'");
                        Toast.makeText(this,"恭喜升級了，提升隨機數值", Toast.LENGTH_SHORT).show();
                        Thread.sleep(2000);
                    }
                    int rand=(int)(Math.random()*2);
                    if(rand==0){
                        Toast.makeText(this,"生命提升", Toast.LENGTH_SHORT).show();
                        cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'HP'", null);
                        cursor.moveToFirst();
                        int next_hp=cursor.getInt(1)+(lvl*5);
                        dbrw.execSQL("UPDATE myTable SET value = " + next_hp + " WHERE data LIKE 'HP'");
                        cursor.close();
                    }
                    else {
                        Toast.makeText(this,"攻擊提升", Toast.LENGTH_SHORT).show();
                        cursor = dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'ATK'", null);
                        cursor.moveToFirst();
                        int next_atk=cursor.getInt(1)+lvl;
                        dbrw.execSQL("UPDATE myTable SET value = " + next_atk + " WHERE data LIKE 'ATK'");
                        cursor.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, lobby.class);
                startActivity(intent);
            }
            if(temp_your_hp<=0){
                Toast.makeText(this,"你被打敗了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, lobby.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
    private void result(int rand,String r){
        if(rand==0){
            Toast.makeText(this,"電腦出的是【布】 "+r, Toast.LENGTH_SHORT).show();
        }
        else if(rand==1){
            Toast.makeText(this,"電腦出的是【石頭】"+r,Toast.LENGTH_SHORT).show();
        }
        else if(rand==2){
            Toast.makeText(this,"電腦出的是【剪刀】"+r,Toast.LENGTH_SHORT).show();
        }
        try {
            Thread.sleep(2000);
        }
        catch (Exception e){
        }
    }
    private int set_lvl(){
        Cursor cursor;
        cursor=dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'LVL'",null);
        cursor.moveToFirst();
        int lvl=cursor.getInt(1);
        layers.setText("第"+lvl+"層");
        cursor.close();
        return lvl;
    }
    private int setYour_hp(){
        Cursor cursor_HP;
        cursor_HP=dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'HP'",null);
        cursor_HP.moveToFirst();
        int HP=cursor_HP.getInt(1);
        your_hp.setText(HP+"HP"+"/"+HP+"HP");
        cursor_HP.close();
        return HP;
    }
    private int set_enemy(){
        Cursor cursor;
        cursor=dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'LVL'",null);
        cursor.moveToFirst();
        int lvl=cursor.getInt(1);
        cursor.close();
        int hp=10+((lvl-1)*5);
        enemy_hp.setText(hp+"HP"+"/"+hp+"HP");
        return hp;
    }
    private int getyour_atk(){
        Cursor cursor;
        cursor=dbrw.rawQuery("SELECT * FROM myTable WHERE data LIKE 'ATK'",null);
        cursor.moveToFirst();
        int ATK=cursor.getInt(1);
        cursor.close();
        return ATK;
    }
}