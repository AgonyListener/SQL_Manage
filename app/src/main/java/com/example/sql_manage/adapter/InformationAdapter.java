package com.example.sql_manage.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sql_manage.R;
import com.example.sql_manage.sql_bean.SQLMsgBean;
import com.example.sql_manage.utils.ScaleInAnimation;

import java.util.List;

/**
 * @ClassName InformationAdapter
 * @Description 数据库列表适配器
 * @Author 乐某
 * @Date 2022/8/3 16:19
 * @Version 1.0
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private List<SQLMsgBean> mInformationList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private ScaleInAnimation mSelectAnimation = new ScaleInAnimation();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView information_title;
        TextView information_stauts;
        TextView information_db;
        TextView information_url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            information_title = itemView.findViewById(R.id.information_title);
            information_stauts = itemView.findViewById(R.id.information_stauts);
            information_db = itemView.findViewById(R.id.information_db);
            information_url = itemView.findViewById(R.id.information_url);

        }
    }
    public InformationAdapter(List<SQLMsgBean> DoctorList){
        mInformationList = DoctorList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item_information,parent,false);
        context = view.getContext();
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InformationAdapter.ViewHolder holder, int position) {
        int i = position;
        SQLMsgBean sqlMsgBean = mInformationList.get(position);
        holder.information_title.setText(sqlMsgBean.getTitle());
        holder.information_stauts.setText(sqlMsgBean.getStauts());
        holder.information_db.setText(sqlMsgBean.getDbname());
        holder.information_url.setText(sqlMsgBean.getmainURL());
        holder.itemView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });
        holder.itemView.findViewById(R.id.button2).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(longClickListener != null) {
                    longClickListener.onClick(i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInformationList.size();
    }
    //定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    public interface OnItemLongClickListener {
        void onClick(int position);
    }
    //自定义点击事件
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimation(holder);
    }


    private void addAnimation(ViewHolder holder) {
        for (Animator anim : mSelectAnimation.getAnimators(holder.itemView)) {
            anim.setDuration(300).start();
            anim.setInterpolator(new LinearInterpolator());
        }
    }

}
