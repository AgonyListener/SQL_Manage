package com.example.sql_manage.adapter;

import static com.example.sql_manage.utils.Tools.base64ToBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sql_manage.R;
import com.example.sql_manage.activity.MainActivity;
import com.example.sql_manage.sql_bean.DataBean;
import com.example.sql_manage.utils.MyDialog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyBaseAdapter
 * @Description TODO
 * @Author 乐某
 * @Date 2022/8/4 16:42
 * @Version 1.0
 */
public class MyBaseAdapter extends BaseAdapter {
    private Context mContext;
    private List<DataBean> dataBeans ;


    public MyBaseAdapter(Context mContext,List<DataBean> dataBeans ) {
        this.dataBeans = dataBeans;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return dataBeans.size();
    }
    @Override
    public Object getItem(int position) {
        return dataBeans.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.list_item,null);
        TextView row_name = view.findViewById(R.id.row_name);
        TextView row_value = view.findViewById(R.id.row_value);
        TextView row_type = view.findViewById(R.id.row_type);

        if (dataBeans.get(position).getRow_type().contains("BLOB")){
            row_value.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            row_value.getPaint().setAntiAlias(true);//抗锯齿
            row_value.setTextColor(Color.parseColor("#008EFF"));//设置颜色
            row_value.setText("点击查看");
            row_value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        MyDialog dialog = new MyDialog(mContext, (Bitmap)base64ToBitmap(dataBeans.get(position).getRow_value()) );
                        dialog.show();
                    }catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "正在制作此功能……", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }else {
            row_value.setText(dataBeans.get(position).getRow_value());
        }

        row_name.setText(dataBeans.get(position).getRow_name());

        row_type.setText(dataBeans.get(position).getRow_type());
        return view;
    }
}
