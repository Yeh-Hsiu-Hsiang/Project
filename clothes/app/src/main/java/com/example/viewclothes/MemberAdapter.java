package com.example.viewclothes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothes.R;
import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getClothesMember;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{

    private Context context;
    private ArrayList<getClothesMember> ClothesList;
    private LayoutInflater inflater;

    // 宣告資料庫功能類別欄位變數
    private clothesDAO dao;

    public MemberAdapter(Context context, ArrayList<getClothesMember> ClothesList) {
        this.context = context;
        this.ClothesList = ClothesList;
        dao = new clothesDAO(context);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View itemView = View.inflate(context, R.layout.cardviewlayout,null);
        View itemView = inflater.inflate( R.layout.cardviewlayout_view,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        getClothesMember member = ClothesList.get(position);
        holder.imageId.setImageBitmap( BitmapFactory.decodeFile(member.getImgPath()));

    }

    @Override
    public int getItemCount() {
        return ClothesList.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageId;
        public ViewHolder(View itemView) {
            super(itemView);
            imageId = (ImageView) itemView.findViewById(R.id.imageId);

            //在adapter中设置点击事件(長按)
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //此处回传点击监听事件
                    if(onItemLongClickListener!=null){
                        onItemLongClickListener.OnItemLongClick(v, ClothesList.get(getLayoutPosition()));
                    }
                    return false;
                }
            });

            //在adapter中设置点击事件(短按)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, ClothesList.get(getLayoutPosition()));
                    }
                    return;
                }
            });
        }
    }

    /**
     * 设置item的监听事件的接口(長)
     */
    public interface OnItemLongClickListener {

        public void OnItemLongClick(View view, getClothesMember data);

    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置item的监听事件的接口(短)
     */
    public interface OnItemClickListener {

        public void OnItemClick(View view, getClothesMember data);

    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}