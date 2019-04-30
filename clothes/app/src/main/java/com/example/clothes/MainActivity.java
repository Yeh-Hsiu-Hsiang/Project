package com.example.clothes;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void dowhat(View view) {

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        //動畫路徑設定(x1,x2,y1,y2)
        TranslateAnimation am = new TranslateAnimation(0, -500, 0, -700);
        am.setDuration(1000);
        //am.setRepeatMode(am.REVERSE);
        button1.setAnimation(am);
        //am.setFillAfter(true);
        button1.startAnimation(am);

        TranslateAnimation am2 = new TranslateAnimation(0, -200, 0, -700);
        am2.setDuration(1000);
        button2.setAnimation(am2);
        button2.startAnimation(am2);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            @Override
            public void run() {
                //過兩秒後要做的事情
                button1.setTranslationX(-500);
                button1.setTranslationY(-700);

                button2.setTranslationX(-200);
                button2.setTranslationY(-700);
                //Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }}, 999);

    }
}



