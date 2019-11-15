package com.example.clothes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

// 資料功能類別
public class WeekWeatherDAO {
    // 表格名稱
    public static final String TABLE_NAME = "Week_Weather";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String CityName = "CityName";
    public static final String Day = "Day";
    public static final String MaxT_Day = "MaxT_Day";
    public static final String MinT_Day = "MinT_Day";
    public static final String WD_Day = "WD_Day";
    public static final String PoP_Day = "PoP_Day";
    public static final String Hour = "Hour";
    public static final String HighTemperature = "HighTemperature";
    public static final String LowTemperature = "LowTemperature";
    public static final String PoPh = "PoPh";
    public static final String WeatherDescription = "WeatherDescription";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CityName + " TEXT NOT NULL, " +
                    Day + " TEXT NOT NULL, " +
                    MaxT_Day + " TEXT NOT NULL, " +
                    MinT_Day + " TEXT NOT NULL, " +
                    WD_Day + " TEXT NOT NULL, " +
                    PoP_Day + " TEXT NOT NULL, " +
                    Hour + " TEXT NOT NULL, " +
                    HighTemperature + " TEXT NOT NULL, " +
                    LowTemperature + " TEXT NOT NULL, " +
                    PoPh + " TEXT NOT NULL, " +
                    WeatherDescription + " TEXT NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public WeekWeatherDAO(Context context) {
        db = DBhelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增物件
    public getWeekWeather insert(getWeekWeather getWeekWeather) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CityName, getWeekWeather.getCityName());
        cv.put(Day, getWeekWeather.getDay());
        cv.put(MaxT_Day, getWeekWeather.getMaxT_Day());
        cv.put(MinT_Day, getWeekWeather.getMinT_Day());
        cv.put(WD_Day, getWeekWeather.getWD_Day());
        cv.put(PoP_Day, getWeekWeather.getPoP_Day());
        cv.put(Hour, getWeekWeather.getHour());
        cv.put(HighTemperature, getWeekWeather.getHighTemperature());
        cv.put(LowTemperature, getWeekWeather.getLowTemperature());
        cv.put(PoPh, getWeekWeather.getPoPh());
        cv.put(WeatherDescription, getWeekWeather.getWeatherDescription());

        long id = db.insert(TABLE_NAME, null, cv);

        getWeekWeather.setId(id);

        return getWeekWeather;
    }

    // 修改物件
    public boolean update(getWeekWeather getWeekWeather) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CityName, getWeekWeather.getCityName());
        cv.put(Day, getWeekWeather.getDay());
        cv.put(MaxT_Day, getWeekWeather.getMaxT_Day());
        cv.put(MinT_Day, getWeekWeather.getMinT_Day());
        cv.put(WD_Day, getWeekWeather.getWD_Day());
        cv.put(PoP_Day, getWeekWeather.getPoP_Day());
        cv.put(Hour, getWeekWeather.getHour());
        cv.put(HighTemperature, getWeekWeather.getHighTemperature());
        cv.put(LowTemperature, getWeekWeather.getLowTemperature());
        cv.put(PoPh, getWeekWeather.getPoPh());
        cv.put(WeatherDescription, getWeekWeather.getWeatherDescription());

        String where = KEY_ID + "=" + getWeekWeather.getId();

        return db.update(TABLE_NAME, cv ,where ,null) > 0;
    }

    // 刪除指定編號的資料
    public boolean delete(long id){
        String where = KEY_ID + "=" + id;

        return db.delete(TABLE_NAME, where ,null) > 0;
    }

    //取得指定ID的單一資料
    public getWeekWeather getoneID(long id){
        getWeekWeather getWeekWeather = null;

        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;

        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果 讀取包裝一筆資料的物件
        if (result.moveToFirst()) {
            getWeekWeather = getRecord(result);
        }

        result.close();
        return getWeekWeather;
    }

    //取得指定類別的多量資料
    public ArrayList<getWeekWeather> getWDweather(String City){
        ArrayList<getWeekWeather> result = new ArrayList<>();

        String where = CityName + " = \"" + City + "\"" ;
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
    public getWeekWeather getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        getWeekWeather result = new getWeekWeather();

        result.setId(cursor.getLong(0));
        result.setCityName(cursor.getString(1));
        result.setDay(cursor.getString(2));
        result.setMaxT_Day(cursor.getString(3));
        result.setMinT_Day(cursor.getString(4));
        result.setWD_Day(cursor.getString(5));
        result.setPoP_Day(cursor.getString(6));
        result.setHour(cursor.getString(7));
        result.setHighTemperature(cursor.getString(8));
        result.setLowTemperature(cursor.getString(9));
        result.setPoPh(cursor.getString(10));
        result.setWeatherDescription(cursor.getString(11));

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


