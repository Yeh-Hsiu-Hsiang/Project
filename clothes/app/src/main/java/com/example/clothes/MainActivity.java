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
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);

        TranslateAnimation am1 = new TranslateAnimation(0, -450, 0, -500);
        am1.setDuration(1000);
        button1.setAnimation(am1);
        button1.startAnimation(am1);

        TranslateAnimation am2 = new TranslateAnimation(0, -270, 0, -600);
        am2.setDuration(1000);
        button2.setAnimation(am2);
        button2.startAnimation(am2);

        TranslateAnimation am3 = new TranslateAnimation(0, -90, 0, -700);
        am3.setDuration(1000);
        button3.setAnimation(am3);
        button3.startAnimation(am3);

        TranslateAnimation am4 = new TranslateAnimation(0, 90, 0, -700);
        am4.setDuration(1000);
        button4.setAnimation(am4);
        button4.startAnimation(am4);

        TranslateAnimation am5 = new TranslateAnimation(0, 270, 0, -600);
        am5.setDuration(1000);
        button5.setAnimation(am5);
        button5.startAnimation(am5);

        TranslateAnimation am6 = new TranslateAnimation(0, 450, 0, -500);
        am6.setDuration(1000);
        button6.setAnimation(am6);
        button6.startAnimation(am6);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);
            Button button4 = (Button) findViewById(R.id.button4);
            Button button5 = (Button) findViewById(R.id.button5);
            Button button6 = (Button) findViewById(R.id.button6);
            @Override
            public void run() {
                //過兩秒後要做的事情
                button1.setTranslationX(-450);
                button1.setTranslationY(-500);

                button2.setTranslationX(-270);
                button2.setTranslationY(-600);

                button3.setTranslationX(-90);
                button3.setTranslationY(-700);

                button4.setTranslationX(90);
                button4.setTranslationY(-700);

                button5.setTranslationX(270);
                button5.setTranslationY(-600);

                button6.setTranslationX(450);
                button6.setTranslationY(-500);
                //Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }}, 999);

    }
}



