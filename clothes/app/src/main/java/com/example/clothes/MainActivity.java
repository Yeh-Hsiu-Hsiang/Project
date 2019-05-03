package com.example.clothes;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去新增活動(頁面)
        Button addschedule = (Button)findViewById(R.id.AddSchedule);
        addschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , AddSchedule.class);
                startActivity(intent);
            }
        });
        //去天氣詳細(頁面)
        Button weekweather = (Button)findViewById(R.id.Weekweather);
        weekweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , Weekweather.class);
                startActivity(intent);
            }
        });
        //去管理衣服(頁面)
        Button manageclothes = (Button)findViewById(R.id.Manageclothes);
        manageclothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , Manageclothes.class);
                startActivity(intent);
            }
        });
//        //去新增衣服(頁面)
//        Button addclothes = (Button)findViewById(R.id.Addclothes);
//        addclothes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this  , AddClothes.class);
//                startActivity(intent);
//            }
//        });

        //去個人設定(頁面)
        Button mysetting = (Button)findViewById(R.id.Mysetting);
        mysetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , setting.class);
                startActivity(intent);
            }
        });

    }

    Boolean openlist = true;
    public void dowhat(View view) {
        //出現清單
        final Button button1 = (Button) findViewById(R.id.AddSchedule);
        final Button button2 = (Button) findViewById(R.id.Weekweather);
        final Button button3 = (Button) findViewById(R.id.Manageclothes);
        //final Button button4 = (Button) findViewById(R.id.Addclothes);
        final Button button5 = (Button) findViewById(R.id.View_wearing);
        final Button button6 = (Button) findViewById(R.id.Mysetting);

        if (openlist) {
            //出現(動畫)
            final TranslateAnimation am1 = new TranslateAnimation(0, -450, 0, -500);
            am1.setDuration(1000);
            button1.setAnimation(am1);
            button1.startAnimation(am1);

            final TranslateAnimation am2 = new TranslateAnimation(0, -225, 0, -600);
            am2.setDuration(1000);
            button2.setAnimation(am2);
            button2.startAnimation(am2);

            final TranslateAnimation am3 = new TranslateAnimation(0, 0, 0, -700);
            am3.setDuration(1000);
            button3.setAnimation(am3);
            button3.startAnimation(am3);

//            final TranslateAnimation am4 = new TranslateAnimation(0, 90, 0, -700);
//            am4.setDuration(1000);
//            button4.setAnimation(am4);
//            button4.startAnimation(am4);

            final TranslateAnimation am5 = new TranslateAnimation(0, 225, 0, -600);
            am5.setDuration(1000);
            button5.setAnimation(am5);
            button5.startAnimation(am5);

            final TranslateAnimation am6 = new TranslateAnimation(0, 450, 0, -500);
            am6.setDuration(1000);
            button6.setAnimation(am6);
            button6.startAnimation(am6);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //出現(移動)
                    //過1秒後要做的事情
                    button1.setTranslationX(-450);
                    button1.setTranslationY(-500);
                    am1.cancel();
                    button2.setTranslationX(-225);
                    button2.setTranslationY(-600);
                    am2.cancel();
                    button3.setTranslationX(0);
                    button3.setTranslationY(-700);
                    am3.cancel();
//                    button4.setTranslationX(90);
//                    button4.setTranslationY(-700);
//                    am4.cancel();
                    button5.setTranslationX(225);
                    button5.setTranslationY(-600);
                    am5.cancel();
                    button6.setTranslationX(450);
                    button6.setTranslationY(-500);
                    am6.cancel();
                    //Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                }
            }, 1000);
            openlist = false;
        }else{
            //歸位(動畫)
            final TranslateAnimation am1 = new TranslateAnimation(0, 450, 0, 500);
            am1.setDuration(1000);
            button1.setAnimation(am1);
            button1.startAnimation(am1);

            final TranslateAnimation am2 = new TranslateAnimation(0, 225, 0, 600);
            am2.setDuration(1000);
            button2.setAnimation(am2);
            button2.startAnimation(am2);

            final TranslateAnimation am3 = new TranslateAnimation(0, 0, 0, 700);
            am3.setDuration(1000);
            button3.setAnimation(am3);
            button3.startAnimation(am3);

//            final TranslateAnimation am4 = new TranslateAnimation(0, -90, 0, 700);
//            am4.setDuration(1000);
//            button4.setAnimation(am4);
//            button4.startAnimation(am4);

            final TranslateAnimation am5 = new TranslateAnimation(0, -225, 0, 600);
            am5.setDuration(1000);
            button5.setAnimation(am5);
            button5.startAnimation(am5);

            final TranslateAnimation am6 = new TranslateAnimation(0, -450, 0, 500);
            am6.setDuration(1000);
            button6.setAnimation(am6);
            button6.startAnimation(am6);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {


                @Override
                public void run() {
                    //回到原位
                    button1.setTranslationX(0);
                    button1.setTranslationY(0);
                    am1.cancel();
                    button2.setTranslationX(0);
                    button2.setTranslationY(0);
                    am2.cancel();
                    button3.setTranslationX(0);
                    button3.setTranslationY(0);
                    am3.cancel();
//                    button4.setTranslationX(0);
//                    button4.setTranslationY(0);
//                    am4.cancel();
                    button5.setTranslationX(0);
                    button5.setTranslationY(0);
                    am5.cancel();
                    button6.setTranslationX(0);
                    button6.setTranslationY(0);
                    am6.cancel();
                    //Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                }
            }, 1000);
            openlist = true;
        }
    }
}



