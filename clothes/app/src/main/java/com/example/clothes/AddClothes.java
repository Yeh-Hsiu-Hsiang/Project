package com.example.clothes;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddClothes extends AppCompatActivity {

    private static final int takepic = 111;
    private static final int filepic = 222;
    Uri imgurl;
    ImageView imv;
    ImageButton mAddGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        init();

        //去新增衣服(頁面)
        Button updata = (Button)findViewById(R.id.updata);
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddClothes.this  , editclothes.class);
                startActivity(intent);
            }
        });
    }
    private void init() {
        //取得權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
        imv = (ImageView)findViewById(R.id.imageView);
        mAddGallery = (ImageButton) findViewById(R.id.camera);

        mAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createPublicImageFile();//创建临时图片文件
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(AddClothes.this,
                                "com.example.clothes.provider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, takepic);
                    }
                }

                galleryAddPic();
            }
        });



    }

    String mPublicPhotoPath;
    private File createPublicImageFile() throws IOException {
        //自訂資料夾
        File path =  new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM)+"/clothes");
        if(!path.exists()) {
            //如果沒有就建立一個
            path.mkdir();
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss.S",Locale.getDefault()).format(new Date());
        String imageFileName = "clothesJPG_" + timeStamp;
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                path      /* 文件夹 */
        );
        //照片路徑
        mPublicPhotoPath = image.getAbsolutePath();

        return image;

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mPublicPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public void onPick(View v){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it,filepic);
    }


    //取得相片後返回
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK ){
            Bitmap bmp = null;
            switch (requestCode){
                case (takepic):

                    // Get the dimensions of the View
                    int targetW = imv.getWidth();
                    int targetH = imv.getHeight();

                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(mPublicPhotoPath, bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;


                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                    // Decode the image file into a Bitmap sized to fill the View
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    bmOptions.inPurgeable = true;

                    bmp = BitmapFactory.decodeFile(mPublicPhotoPath, bmOptions);

                    break;
                case (filepic):
                    imgurl = data.getData();
                    try {
                        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgurl),null,null);

                        //?
//                        String[] proj = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = managedQuery(imgurl, proj, null, null, null);
//                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        cursor.moveToFirst();
//                        String path = cursor.getString(column_index);
//                        Log.d("tag" , path);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(this,"無法選取圖片",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            imv.setImageBitmap(bmp);
        }else{
            Toast.makeText(this,"取消讀取到照片",Toast.LENGTH_LONG).show();
           // Log.d("tag" , mPublicPhotoPath.toString());
        }

    }

}

