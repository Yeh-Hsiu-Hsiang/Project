package com.example.clothes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

// 資料功能類別
public class weatherDAO {

    // 表格名稱
    public static final String TABLE_NAME = "Weather";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String CityName = "CityName";
    public static final String Temperature = "%";
    public static final String HighTemperature = "HighTemperature";
    public static final String LowTemperature = "LowTemperature";
    public static final String PoPh = "PoPh";
    public static final String WeatherDescription = "WeatherDescription";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CityName + " TEXT NOT NULL, " +
                    Temperature + " TEXT NOT NULL, " +
                    HighTemperature + " TEXT NOT NULL, " +
                    LowTemperature + " TEXT NOT NULL, " +
                    PoPh + " TEXT NOT NULL, " +
                    WeatherDescription + " TEXT NOT NULL, ";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public weatherDAO(Context context) {
        db = DBhelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增物件
    public getWeather insert(getWeather getweather) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CityName, getweather.getCityName());
        cv.put(Temperature, getweather.getTemperature());
        cv.put(HighTemperature, getweather.getHighTemperature());
        cv.put(LowTemperature, getweather.getLowTemperature());
        cv.put(PoPh, getweather.getPoPh());
        cv.put(WeatherDescription, getweather.getWeatherDescription());

        long id = db.insert(TABLE_NAME, null, cv);

        getweather.setId(id);

        return getweather;
    }

    // 修改物件
    public boolean update(getWeather getweather) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CityName, getweather.getCityName());
        cv.put(Temperature, getweather.getTemperature());
        cv.put(HighTemperature, getweather.getHighTemperature());
        cv.put(LowTemperature, getweather.getLowTemperature());
        cv.put(PoPh, getweather.getPoPh());
        cv.put(WeatherDescription, getweather.getWeatherDescription());

        String where = KEY_ID + "=" + getweather.getId();

        return db.update(TABLE_NAME, cv ,where ,null) > 0;
    }

    // 刪除指定編號的資料
    public boolean delete(long id){
        String where = KEY_ID + "=" + id;

        return db.delete(TABLE_NAME, where ,null) > 0;
    }

    //取得指定ID的單一資料
    public getWeather getoneID(long id){
        getWeather getweather = null;

        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;

        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果 讀取包裝一筆資料的物件
        if (result.moveToFirst()) {
            getweather = getRecord(result);
        }

        result.close();
        return getweather;
    }


    // 把Cursor目前的資料包裝為物件
    public getWeather getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        getWeather result = new getWeather();

        result.setId(cursor.getLong(0));
        result.setCityName(cursor.getString(1));
        result.setTemperature(cursor.getString(2));
        result.setHighTemperature(cursor.getString(3));
        result.setLowTemperature(cursor.getString(4));
        result.setPoPh(cursor.getString(5));
        result.setWeatherDescription(cursor.getString(6));

        // 回傳結果
        return result;
    }

    // 建立範例資料
    public void sample() {
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }
}


