package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class battle extends AppCompatActivity {
    private Button run,btn_scissor,btn_stone,btn_paper;
    private TextView layers,hp;
    private ImageView Image;
    int choice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Image =findViewById(R.id.imageView);
        //Image.setImageResource(R.drawable.IMG_0688);
        btn_scissor=findViewById(R.id.btn_scissor);
        btn_paper=findViewById(R.id.btn_paper);
        btn_stone=findViewById(R.id.btn_stone);
        run=findViewById(R.id.run);
        layers=findViewById(R.id.layers);
        hp=findViewById(R.id.hp);
        btn_paper.setOnClickListener(v -> {
            Toast.makeText(this,"你選擇的是 布",Toast.LENGTH_SHORT).show();
            choice=0;
        });
        btn_stone.setOnClickListener(v -> {
            Toast.makeText(this,"你選擇的是 石頭",Toast.LENGTH_SHORT).show();
            choice=1;
        });
        btn_scissor.setOnClickListener(v -> {
            Toast.makeText(this,"你選擇的是 剪刀",Toast.LENGTH_SHORT).show();
            choice=2;
        });
        run.setOnClickListener(v -> {
            try {
                Thread.sleep(1000);
                Toast.makeText(this,"3",Toast.LENGTH_SHORT).show();
                Thread.sleep(1000);
                Toast.makeText(this,"2",Toast.LENGTH_SHORT).show();
                Thread.sleep(1000);
                Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int rand=(int)(Math.random()*3);
            if(rand==0){
                Toast.makeText(this,"電腦出的是 布",Toast.LENGTH_SHORT).show();
            }
            else if(rand==1){
                Toast.makeText(this,"電腦出的是 石頭",Toast.LENGTH_SHORT).show();
            }
            else if(rand==2){
                Toast.makeText(this,"電腦出的是 剪刀",Toast.LENGTH_SHORT).show();
            }
            if((choice==0 && rand==2)||(choice==2 && rand==0)||(choice==1 && rand==1)){

            }
            else if ((choice==0 && rand==1)||(choice==2 && rand==2)||(choice==1 && rand==0)) {
            }
            else {
            }
        });

    }
}