package com.example.clothes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class clothes1Fragment extends Fragment {
    //資料庫
    static final String db_name = "clothes.db";
    static final String tb_name = "ManageClothes";
    static int count = 0;
    SQLiteDatabase db;
    private View view;//定義view用來設置fragment的layout
    public RecyclerView mCollectRecyclerView;//定義RecyclerView
    //定義getClothesMember的集合
    private ArrayList<getClothesMember> clothesList;
    //自定義recyclerveiw的Adapter
    private MemberAdapter memberAdapter;
   // String text = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clothes1, container, false);
        //對recycleview進行重製
//        initData();
//        initRecyclerView();
//        //建立數據


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (clothesList != null)
            clothesList=null;

        clothesList = new ArrayList<>();
        initData();
        initRecyclerView();
        //建立數據

    }

    private void initData(){

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

                if(clothesType.equals("長袖上衣")){
                    Log.e("getData",clothesType);
                    getclothesmember.setId(clothesID) ;
                    getclothesmember.setImgPath(clothesPIC);
                    getclothesmember.setName(clothesName);
                    clothesList.add(new getClothesMember(clothesID,
                            clothesPIC,
                            clothesName));
                    count++;
                }
            } while(c.moveToNext());    // 有一下筆就繼續迴圈
        }

        db.close();
        Log.e("count","fragmant"+Integer.toString(count));
        count = 0;
        //點別的fragment再回來會重新再載入??
    }
    private void initRecyclerView(){
        mCollectRecyclerView = (RecyclerView)view.findViewById(R.id.collect_recyclerView);
        memberAdapter = new MemberAdapter(getActivity(), clothesList);
        mCollectRecyclerView.setAdapter(memberAdapter);
        mCollectRecyclerView.setLayoutManager(new GridLayoutManager(getActivity() ,2,GridLayoutManager.VERTICAL,false));
       //解決留白問題 用分隔線
        mCollectRecyclerView.addItemDecoration(new MyPaddingDecoration());

        //點擊進入修改
        memberAdapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view ,getClothesMember data) {
                //此處進行監聽事件的動作處理
                Toast.makeText(getActivity(),data.id,Toast.LENGTH_SHORT).show();
            }
        });

        //無法完整顯示?


    }

    //RecyclerView的分隔線
    public class MyPaddingDecoration extends RecyclerView.ItemDecoration {
        private int divider;
        public MyPaddingDecoration() {
            //  設置分隔線寬度
            divider = 10;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = divider;  //相對於 設置 left padding
            outRect.top = divider;   //相對於 設置 top padding
            outRect.right = divider; //相對於 設置 right padding
            outRect.bottom = divider;  //相對於 設置 bottom padding
        }
    }
}