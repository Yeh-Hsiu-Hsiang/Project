package com.example.clothes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{

    private Context context;
    private ArrayList<getClothesMember> ClothesList;

    public MemberAdapter(Context context, ArrayList<getClothesMember> ClothesList) {
        this.context = context;
        this.ClothesList = ClothesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.cardviewlayout, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        getClothesMember member = ClothesList.get(position);
        //holder.imageId.setImageResource(member.getImage());
        holder.imageId.setImageBitmap(BitmapFactory.decodeFile(member.getImgPath()));
        holder.textId.setText(member.getId());
        holder.textName.setText(member.getName());

    }

    @Override
    public int getItemCount() {
        return ClothesList.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageId;
        private  TextView textId, textName;
        public ViewHolder(View itemView) {
            super(itemView);
            imageId = (ImageView) itemView.findViewById(R.id.imageId);
            textId = (TextView) itemView.findViewById(R.id.textId);
            textName = (TextView) itemView.findViewById(R.id.textName);
        }
    }
}