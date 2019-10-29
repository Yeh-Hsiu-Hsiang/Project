package com.example.clothes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


// 資料功能類別
public class clothesDAO {
    // 表格名稱
    public static final String TABLE_NAME = "ManageClothes";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String CLOTHESPATH_COLUMN = "clothespath";
    public static final String NAME_COLUMN = "name";
    public static final String TYPE_COLUMN = "type";
    public static final String STYLE_COLUMN = "style";
    public static final String TEMPLOWER_COLUMN = "tempLower";
    public static final String TEMPUPPER_COLUMN = "tempUpper";
    public static final String SEASON_COLUMN = "season";
    public static final String UPDATETIME_COLUMN = "updatetime";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CLOTHESPATH_COLUMN + " TEXT NOT NULL, " +
                    NAME_COLUMN + " TEXT NOT NULL, " +
                    TYPE_COLUMN + " TEXT NOT NULL, " +
                    STYLE_COLUMN + " TEXT NOT NULL, " +
                    TEMPLOWER_COLUMN + " INTEGER NOT NULL, " +
                    TEMPUPPER_COLUMN + " INTEGER  NOT NULL, " +
                    SEASON_COLUMN + " TEXT NOT NULL,  " +
                    UPDATETIME_COLUMN + " TEXT NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public clothesDAO(Context context) {
        db = DBhelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增物件
    public getClothesMember insert(getClothesMember getclothesmember) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CLOTHESPATH_COLUMN, getclothesmember.getImgPath());
        cv.put(NAME_COLUMN, getclothesmember.getName());
        cv.put(TYPE_COLUMN, getclothesmember.getType());
        cv.put(STYLE_COLUMN, getclothesmember.getStyle());
        cv.put(TEMPLOWER_COLUMN, getclothesmember.getTempLower());
        cv.put(TEMPUPPER_COLUMN, getclothesmember.getTempUpper());
        cv.put(SEASON_COLUMN, getclothesmember.getSeason());
        cv.put(UPDATETIME_COLUMN, getclothesmember.getUpdateTime());

        long id = db.insert(TABLE_NAME, null, cv);

        getclothesmember.setId(id);

        return getclothesmember;
    }

    // 修改物件
    public boolean update(getClothesMember getclothesmember) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CLOTHESPATH_COLUMN, getclothesmember.getImgPath());
        cv.put(NAME_COLUMN, getclothesmember.getName());
        cv.put(TYPE_COLUMN, getclothesmember.getType());
        cv.put(STYLE_COLUMN, getclothesmember.getStyle());
        cv.put(TEMPLOWER_COLUMN, getclothesmember.getTempLower());
        cv.put(TEMPUPPER_COLUMN, getclothesmember.getTempUpper());
        cv.put(SEASON_COLUMN, getclothesmember.getSeason());
        cv.put(UPDATETIME_COLUMN, getclothesmember.getUpdateTime());

        String where = KEY_ID + "=" + getclothesmember.getId();

        return db.update(TABLE_NAME, cv ,where ,null) > 0;
    }

    // 刪除指定編號的資料
    public boolean delete(long id){
        String where = KEY_ID + "=" + id;

        return db.delete(TABLE_NAME, where ,null) > 0;
    }

    //取得指定ID的單一資料
    public getClothesMember getoneID(long id){
        getClothesMember getclothesmember = null;

        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;

        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果 讀取包裝一筆資料的物件
        if (result.moveToFirst()) {
            getclothesmember = getRecord(result);
        }

        result.close();
        return getclothesmember;
    }

    //取得指定類別的多量資料
    public ArrayList<getClothesMember> getoneType(String type){
        ArrayList<getClothesMember> result = new ArrayList<>();

        String where = TYPE_COLUMN + "=" + type;
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
    public getClothesMember getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        getClothesMember result = new getClothesMember();

        result.setId(cursor.getLong(0));
        result.setImgPath(cursor.getString(1));
        result.setName(cursor.getString(2));
        result.setType(cursor.getString(3));
        result.setStyle(cursor.getString(4));
        result.setTempLower(cursor.getLong(5));
        result.setTempUpper(cursor.getLong(6));
        result.setSeason(cursor.getString(7));
        result.setUpdateTime(cursor.getString(8));

        // 回傳結果
        return result;
    }

}
