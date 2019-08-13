package com.example.clothes;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
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


public class AddClothes extends AppCompatActivity {

    private static final int takepic = 111;
    private static final int filepic = 222;

    Uri imguri;
    ImageView imv;
    ImageButton mAddGallery;
    Bitmap bmp;
    //在別的activity中關閉自己的方法
    public static AddClothes finishself = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_add_clothes);
        bmp = null;
        init();
        finishself = this;

        //去新增衣服(頁面)
        Button updata = (Button) findViewById( R.id.updata);
        updata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (bmp != null) {
                    editclothes.PicPath = mPublicPhotoPath;
                    Intent intent = new Intent ();
                    intent.setClass( AddClothes.this, editclothes.class);
                    startActivity(intent);

                } else {
                    Toast.makeText( AddClothes.this, "請拍照或選擇圖片", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        //取得權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
        imv = (ImageView) findViewById( R.id.imageView);
        mAddGallery = (ImageButton) findViewById( R.id.camera);

        mAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相機應用
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createPublicImageFile();//创建临时图片文件
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile( AddClothes.this,
                                "com.example.clothes.provider",
                                photoFile);
                        takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, photoURI);
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
        File path = new File ( Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM) + "/clothes");
        if (!path.exists()) {
            //如果沒有就建立一個
            path.mkdir();
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat ("yyyyMMdd_HHmmss").format(new Date ());
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
        Intent mediaScanIntent = new Intent ( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File (mPublicPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public void onPick(View v) {
        Intent it = new Intent ( Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it, filepic);
    }


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
                    String path = getPath( AddClothes.this, imguri);
                    mPublicPhotoPath = path;
                    //=========如何將其他路徑的照片另存新檔至本app的指定路徑??============

                    try {
                        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imguri), null, null);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "無法選取圖片", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            imv.setImageBitmap(bmp);
            Log.e("image", mPublicPhotoPath);
        } else {
            Toast.makeText(this, "取消讀取照片", Toast.LENGTH_LONG).show();
            // Log.d("tag" , mPublicPhotoPath.toString());
        }

    }

    //以下從uri轉path的方法(很長。)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


//    public static WeakReference<AddClothes> weak = null;
//    public static void finishActivity() {
//        //bmp = null;
//        if (weak!= null && weak.get() != null) {
//            Log.e("weak","finish?");
//            weak.get().finish();
//        }
//    }
}
