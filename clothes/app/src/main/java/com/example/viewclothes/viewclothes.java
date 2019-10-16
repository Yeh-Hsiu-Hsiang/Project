package com.example.viewclothes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.clothes.R;
import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getClothesMember;


public class viewclothes extends AppCompatActivity {

    // 宣告資料庫功能類別欄位變數
    private clothesDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclothes);

        dao = new clothesDAO(getApplicationContext());

        getpicture();
    }

    public void getpicture(){
        getClothesMember member = dao.getoneID(21);
        TouchImageView imageView = new TouchImageView(getApplicationContext());
        Bitmap bitmap = BitmapFactory.decodeFile( member.getImgPath());
        imageView.setImageBitmap(bitmap);
        //imageView.setMaxZoom(3);


        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(800,800);
        params.leftMargin = 100;
        params.topMargin = 100;
        relativeLayout.addView(imageView,params);

    }
}
