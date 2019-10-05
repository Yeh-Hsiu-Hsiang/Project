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

public class AddClothes extends AppCompatActivity {
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

                    //存圖到 指定資料夾-----------------------------------------------------------
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

        File file = new File(path +"/"+
                        imageFileName +
                        ".png");

        if (file.exists()) {
            file.delete();
        }

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

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
