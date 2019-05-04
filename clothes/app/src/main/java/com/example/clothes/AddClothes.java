package com.example.clothes;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddClothes extends AppCompatActivity {

    private static final int takepic = 111;
    private static final int filepic = 222;
    Uri imgurl;
    ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);
        imv = (ImageView)findViewById(R.id.imageView);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

    }

    public void onGet(View v){

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            //未取得權限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
        }else{
            //有取得權限
            savePhoto();
        }
    }
    public void onPick(View v){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it,filepic);
    }

    //user 拒絕
    public void onRequestPermissionResult(int requestCode,String[] permission, int[] grantResults){
        if(requestCode == 200){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                savePhoto();
            }else{
                Toast.makeText(this,"程式需要權限才能運作",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void savePhoto(){
        imgurl = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        it.putExtra(MediaStore.EXTRA_OUTPUT,imgurl);

        startActivityForResult(it,takepic);
    }

    //取得相片後返回
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK ){
            Bitmap bmp = null;
            switch (requestCode){
                case (takepic):
                    try{
                        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgurl),null,null);
                    }catch (IOException e){
                        Toast.makeText(this,"無法讀取圖片",Toast.LENGTH_LONG).show();
                    }
                    break;
                case (filepic):
                    imgurl = data.getData();
                    try {
                        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgurl),null,null);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this,"無法選取圖片",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            imv.setImageBitmap(bmp);
        }else{
            Toast.makeText(this,"沒拍到照片",Toast.LENGTH_LONG).show();
        }

    }

}

