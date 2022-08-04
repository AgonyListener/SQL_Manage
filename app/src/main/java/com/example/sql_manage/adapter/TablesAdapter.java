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
import com.example.sql_manage.sql_bean.TableBean;
import com.example.sql_manage.utils.ScaleInAnimation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TablesAdapter
 * @Description 数据库表列表适配器
 * @Author 乐某
 * @Date 2022/8/3 16:19
 * @Version 1.0
 */
public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.ViewHolder> {
    private List<TableBean> mInformationList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private ScaleInAnimation mSelectAnimation = new ScaleInAnimation();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView table_name;
        TextView table_id;
        TextView table_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            table_name = itemView.findViewById(R.id.table_name);
            table_id = itemView.findViewById(R.id.numbers);
            table_time = itemView.findViewById(R.id.time);

        }
    }
    public TablesAdapter(List<TableBean> mInformationList){
        this.mInformationList = mInformationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item_tables,parent,false);
        context = view.getContext();
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TablesAdapter.ViewHolder holder, int position) {
        int i = position;
        TableBean tableBean = mInformationList.get(position);
        // 获取当前时间
//        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        holder.table_name.setText(tableBean.getName());
        holder.table_id.setText(""+tableBean.getId());
        holder.table_time.setText(tableBean.getTime());
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
