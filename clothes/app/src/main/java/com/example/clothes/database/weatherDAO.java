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
    public static final String Day = "Day";
    public static final String T_Day = "T_Day";
    public static final String WD_Day = "WD_Day";
    public static final String PoP_Day = "PoP_Day";
    public static final String Hour = "Hour";
    public static final String T_Hour = "T_Hour";
    public static final String WD_Hour = "WD_Hour";
    public static final String Temperature = "Temperature";
    public static final String PoPh = "PoPh";
    public static final String WeatherDescription = "WeatherDescription";
    public static final String Threehour_Description = "Threehour_Description";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CityName + " TEXT NOT NULL, " +
                    Day + " TEXT NOT NULL, " +
                    T_Day + " TEXT NOT NULL, " +
                    WD_Day + " TEXT NOT NULL, " +
                    PoP_Day + " TEXT NOT NULL, " +
                    Hour + " TEXT NOT NULL, " +
                    T_Hour + " TEXT NOT NULL, " +
                    WD_Hour + " TEXT NOT NULL, " +
                    Temperature + " TEXT NOT NULL, " +
                    PoPh + " TEXT NOT NULL, " +
                    WeatherDescription + " TEXT NOT NULL," +
                    Threehour_Description + " TEXT NOT NULL)";

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
        cv.put(Day, getweather.getDay());
        cv.put(T_Day, getweather.getT_Day());
        cv.put(WD_Day, getweather.getWD_Day());
        cv.put(PoP_Day, getweather.getPoP_Day());
        cv.put(Hour, getweather.getHour());
        cv.put(T_Hour, getweather.getT_Hour());
        cv.put(WD_Hour, getweather.getWD_Hour());
        cv.put(Temperature, getweather.getTemperature());
        cv.put(PoPh, getweather.getPoPh());
        cv.put(WeatherDescription, getweather.getWeatherDescription());
        cv.put(Threehour_Description, getweather.getThreehour_Description());

        long id = db.insert(TABLE_NAME, null, cv);

        getweather.setId(id);

        return getweather;
    }

    // 修改物件
    public boolean update(getWeather getweather) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CityName, getweather.getCityName());
        cv.put(Day, getweather.getDay());
        cv.put(T_Day, getweather.getT_Day());
        cv.put(WD_Day, getweather.getWD_Day());
        cv.put(PoP_Day, getweather.getPoP_Day());
        cv.put(Hour, getweather.getHour());
        cv.put(T_Hour, getweather.getT_Hour());
        cv.put(WD_Hour, getweather.getWD_Hour());
        cv.put(Temperature, getweather.getTemperature());
        cv.put(PoPh, getweather.getPoPh());
        cv.put(WeatherDescription, getweather.getWeatherDescription());
        cv.put(Threehour_Description, getweather.getThreehour_Description());

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

    //取得指定類別的多量資料 --- 取出合適溫度的上衣&連衣裙
    public ArrayList<getWeather> getWDweather(String City, String Today, String Hour){
        ArrayList<getWeather> result = new ArrayList<>();

        String where = CityName + " = \"" + City + "\" and" + WD_Day + " = \"" + Today + "\" and" + WD_Hour + "=\"" + Hour + "\"";
        // 執行查詢
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }
        cursor.close();
        return result;
    }

    // 把Cursor目前的資料包裝為物件
    public getWeather getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        getWeather result = new getWeather();

        result.setId(cursor.getLong(0));
        result.setCityName(cursor.getString(1));
        result.setDay(cursor.getString(2));
        result.setT_Day(cursor.getString(3));
        result.setWD_Day(cursor.getString(4));
        result.setPoP_Day(cursor.getString(5));
        result.setHour(cursor.getString(6));
        result.setT_Hour(cursor.getString(7));
        result.setWD_Hour(cursor.getString(8));
        result.setTemperature(cursor.getString(9));
        result.setPoPh(cursor.getString(10));
        result.setWeatherDescription(cursor.getString(11));
        result.setThreehour_Description(cursor.getString(12));

        // 回傳結果
        return result;
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


