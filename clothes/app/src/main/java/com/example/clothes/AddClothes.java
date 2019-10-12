package com.example.clothes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.github.gabrielbb.cutout.CutOut;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddClothes extends AppCompatActivity {
    //傳入衣服id
    public static Long clothesID ;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);
        Button b = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        b.setOnClickListener(view -> {
            CutOut.activity()
                    .bordered()
                    .noCrop()
                    .start(this);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    Uri Uri = CutOut.getUri(data);
                    // Save the image using the returned Uri here
                    //imageView.setImageURI(imageUri);
                    //Log.e("123",imageUri.toString());
                    //Uri>>path

                    //存圖到 指定資料夾-----------------------------------------------------------

                    //0.Uri轉成Bitmap
                    try {
                        Bitmap bitmap = getBitmapFromUri(Uri);
                        imageView.setImageBitmap(bitmap);
                        saveToLocal(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //------------------------------------------------------------------------------------

                    break;
                case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                    Exception ex = CutOut.getError(data);
                    break;
                default:
                    System.out.print("User cancelled the CutOut screen");
            }
        }
    }

<<<<<<< Updated upstream
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相機應用
                    // Create the File where the photo should go
                    File photoFile = null;
=======
                    //0.Uri轉成Bitmap
>>>>>>> Stashed changes
                    try {
                        Bitmap bitmap = getBitmapFromUri(Uri);
                        imageView.setImageBitmap(bitmap);
                        saveToLocal(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //------------------------------------------------------------------------------------

                    break;
                case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                    Exception ex = CutOut.getError(data);
                    break;
                default:
                    System.out.print("User cancelled the CutOut screen");
            }
        }
    }

    //1.Uri轉成Bitmap
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    //2.儲存到指定資料夾
    private void saveToLocal(Bitmap bitmap) throws IOException {
        String timeStamp = new SimpleDateFormat ("yyyyMMdd_HHmmss").format(new Date ());
        String imageFileName = "clothesPNG_" + timeStamp;
        File path = new File ( Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM) + "/clothes");
        if (!path.exists()) {
            path.mkdir();
        }
<<<<<<< Updated upstream
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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

    public void onPick(View v) {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it, filepic);
    }

        File file = new File(path +"/"+
                        imageFileName +
                        ".png");

        if (file.exists()) {
            file.delete();
        }
        filepath = file.toString();
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
                //保存图片后发送广播通知更新数据库
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                this.sendBroadcast(intent);

    //取得相片後返回
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
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
                    imguri = data.getData();

                    //uri轉path 傳送路徑到editclothes整合sqlite
                    String path = getPath(AddClothes.this, imguri);
                    mPublicPhotoPath = path;
                    //=========如何將其他路徑的照片另存新檔至本app的指定路徑??============

                    try {
                        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imguri), null, null);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "無法選取圖片", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
