package com.example.clothes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class clothes1Fragment extends Fragment {
    //資料庫
    static final String db_name = "clothes.db";
    static final String tb_name = "ManageClothes";
    static final String type = "長袖上衣";
    SQLiteDatabase db;
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<getClothesMember> clothesList = new ArrayList<getClothesMember>();
    //自定义recyclerveiw的适配器
    private MemberAdapter memberAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clothes1, container, false);
        //对recycleview进行配置
        initRecyclerView();
        //模拟数据
        initData();

        return view;
    }
    private void initData(){
        //List<getClothesMember> memberList = new ArrayList<>();
        //開啟或建立資料庫
        db = getActivity().openOrCreateDatabase(db_name, Context.MODE_PRIVATE , null);
        String createTable = "CREATE TABLE IF NOT EXISTS "+
                tb_name +
                "(衣服編號  INTEGER primary key autoincrement , " +
                "衣服照片 TEXT NOT NULL , " +
                "衣服名稱 TEXT , "+
                "衣服類型  TEXT NOT NULL , " +
                "穿衣溫度_下限 INTEGER NOT NULL , " +
                "穿衣溫度_上限 INTEGER NOT NULL , " +
                "季節 TEXT , "+
                "建立日期 INTEGER NOT NULL ) " ;
        db.execSQL(createTable);
        Cursor c=db.rawQuery("SELECT * FROM "+tb_name, null);
        if (c.getCount()>0){
            c.moveToFirst();    // 移到第 1 筆資料
            do{        // 逐筆讀出資料
                getClothesMember getclothesmember = new getClothesMember();
                String clothesID = c.getString(0);
                String clothesPIC = c.getString(1);
                String clothesName = c.getString(2);
                String clothesType = c.getString(3);

                if(clothesType.equals("長袖上衣")){//沒有判定成功????
                    Log.e("getData",clothesType);
                    getclothesmember.setId(clothesID) ;
                    getclothesmember.setImgPath(clothesPIC);
                    getclothesmember.setName(clothesName);
                    clothesList.add(new getClothesMember(clothesID,
                            clothesPIC,
                            clothesName));

                }
            } while(c.moveToNext());    // 有一下筆就繼續迴圈
        }

        db.close();
    }
    private void initRecyclerView(){
        mCollectRecyclerView = (RecyclerView)view.findViewById(R.id.collect_recyclerView);
        memberAdapter = new MemberAdapter(getActivity(), clothesList);
        mCollectRecyclerView.setAdapter(memberAdapter);
        mCollectRecyclerView.setLayoutManager(new GridLayoutManager(getActivity() ,2,GridLayoutManager.VERTICAL,false));

    }

}