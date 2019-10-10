package com.example.clothes.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clothes.MemberAdapter;
import com.example.clothes.R;
import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getClothesMember;

import java.util.ArrayList;


public class clothes1Fragment extends Fragment {

    // 宣告資料庫功能類別欄位變數
    private clothesDAO dao;

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
        view = inflater.inflate( R.layout.fragment_clothes1, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dao = new clothesDAO(getContext());
        if (clothesList != null)
            clothesList = null;

        clothesList = new ArrayList<>();
        initData();
        initRecyclerView();
        //建立數據

    }

    private void initData(){
        clothesList = dao.getoneType("\"短袖上衣\"");
        dao.close();

    }
    private void initRecyclerView(){
        mCollectRecyclerView = (RecyclerView)view.findViewById(R.id.collect_recyclerView);
        memberAdapter = new MemberAdapter(getActivity(), clothesList);
        mCollectRecyclerView.setAdapter(memberAdapter);
        mCollectRecyclerView.setLayoutManager(new GridLayoutManager (getActivity() ,2, GridLayoutManager.VERTICAL,false));
        //解決留白問題 用分隔線
        mCollectRecyclerView.addItemDecoration(new MyPaddingDecoration());

        //點擊(長按)進入多選刪除
        memberAdapter.setOnItemLongClickListener(new MemberAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view , getClothesMember data) {
                //Toast.makeText(getActivity(),data.id.toString(), Toast.LENGTH_SHORT).show();

                //next----
            }
        });

        //點擊(短按)進入修改
        memberAdapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view , getClothesMember data) {
                //Toast.makeText(getActivity(),data.id.toString(), Toast.LENGTH_SHORT).show();

                //next----
            }
        });
    }

    //RecyclerView的分隔線
    public class MyPaddingDecoration extends RecyclerView.ItemDecoration {
        private int divider;
        public MyPaddingDecoration() {
            //  設置分隔線寬度
            divider = 50;
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