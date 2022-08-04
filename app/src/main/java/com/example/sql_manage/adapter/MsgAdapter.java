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
import com.example.sql_manage.sql_bean.MsgBean;
import com.example.sql_manage.sql_bean.TableBean;
import com.example.sql_manage.utils.ScaleInAnimation;

import java.util.List;

/**
 * @ClassName MsgAdapter
 * @Description 表内容适配器
 * @Author 乐某
 * @Date 2022/8/3 16:19
 * @Version 1.0
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<List<MsgBean>> mInformationList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private ScaleInAnimation mSelectAnimation = new ScaleInAnimation();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView row_name;
        TextView row_value;
        TextView row_type;
        TextView row_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_name = itemView.findViewById(R.id.row_name);
            row_value = itemView.findViewById(R.id.row_value);
            row_type = itemView.findViewById(R.id.row_type);
            row_num = itemView.findViewById(R.id.numbers);
        }
    }
    public MsgAdapter(List<List<MsgBean>> mInformationList){
        this.mInformationList = mInformationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item_msg,parent,false);
        context = view.getContext();
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        int i = position;
        List<MsgBean> msgBeans = mInformationList.get(position);
        // 获取当前时间
//        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (msgBeans.size()!=0){
            holder.row_num.setText(""+(i+1));
            holder.row_name.setText(msgBeans.get(0).getName());
            holder.row_value.setText(""+msgBeans.get(0).getValue());
            holder.row_type.setText(msgBeans.get(0).getType());
            for (int j = 1; j < msgBeans.size(); j++) {
                holder.row_name.append("\n"+msgBeans.get(j).getName());
                holder.row_value.append("\n"+msgBeans.get(j).getValue());
                holder.row_type.append("\n"+msgBeans.get(j).getType());
            }
        }

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
