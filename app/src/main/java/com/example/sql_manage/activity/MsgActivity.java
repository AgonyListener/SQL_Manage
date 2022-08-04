package com.example.sql_manage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sql_manage.MainData;
import com.example.sql_manage.R;
import com.example.sql_manage.adapter.MsgAdapter;
import com.example.sql_manage.adapter.TablesAdapter;
import com.example.sql_manage.sql_bean.MsgBean;
import com.example.sql_manage.sql_bean.RowBean;
import com.example.sql_manage.sql_bean.SQLMsgBean;
import com.example.sql_manage.sql_bean.TableBean;
import com.example.sql_manage.utils.MySQLConnections;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MsgActivity extends AppCompatActivity {

    private List<List<MsgBean>> msgBeans =  new ArrayList<>();
    private SQLMsgBean sqlMsgBean;
    private TableBean tableBean;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView sql_list;
    private MsgAdapter adapter;

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private List<RowBean> rows = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        //获取当前状态的数据表
        sqlMsgBean = MainData.sqlMsgBean;
        tableBean = MainData.tableBean;
        //返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //数据库名字
        Objects.requireNonNull(getSupportActionBar()).setTitle(tableBean.getName());
        //加载列表
        information_list_init();
        //初始化下拉刷新
        swip_fresh_init();

        // 更新数据
        new Thread_updata().start();

    }
    //设置返回键
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void swip_fresh_init(){
        //下拉属性
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swip_fresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
    public void information_list_init(){
        sql_list = findViewById(R.id.sql_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MsgActivity.this);
        sql_list.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(msgBeans);
        sql_list.setAdapter(adapter);


        adapter.setOnItemClickListener(new MsgAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                // TODO: 2022/8/4 点击事件
            }
        });

        adapter.setOnItemLongClickListener(new MsgAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                // 删除数据库列表
                final BottomSheetDialog bsd = new BottomSheetDialog(MsgActivity.this, R.style.BottomSheetEdit);
                final View v = View.inflate(MsgActivity.this, R.layout.dialog_table_del, null);
                bsd.setContentView(v);
                bsd.show();
                final MaterialButton b1 = v.findViewById(R.id.button1);
                final MaterialButton b2 = v.findViewById(R.id.button2);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bsd.dismiss();
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2022/8/4 确定删除 【真的删除】
                    }
                });
            }
        });


    }
    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 更新数据
                        new Thread_updata().start();
                    }
                });
            }
        }).start();
    }

    // 更新数据
    class Thread_updata extends Thread{
        @Override
        public void run() {
            try {
                con = MySQLConnections.getConnection(sqlMsgBean.getmainURL()+"/"+sqlMsgBean.getDbname(),sqlMsgBean.getUser(),sqlMsgBean.getPassword());
                String sql ="SELECT * FROM "+tableBean.getName();    //要执行的SQ
                if (con!=null) {
                    stmt = con.prepareStatement(sql);
                    ResultSet rs=stmt.executeQuery(sql);
                    ResultSetMetaData data=rs.getMetaData();


                    int count = 0;
                    rows.clear();
                    msgBeans.clear();
                    // 循环每行
                    while(rs.next()) {
                        List<MsgBean> list1 = new ArrayList<MsgBean>();
                        msgBeans.add(list1);

                        //循环每列
                        for (int i = 1; i <= data.getColumnCount(); i++) {
                            //在数据库中类型的最大字符个数
                            int columnDisplaySize = data.getColumnDisplaySize(i);
                            //获得所有列的数目及实际列数
                            int columnCount = data.getColumnCount();
                            //获得指定列的列名
                            String columnName = data.getColumnName(i);
                            //获得指定列的列值
                            String columnValue = rs.getString(i);
                            //获得指定列的数据类型
                            //  int columnType = data.getColumnType(i);
                            //获得指定列的数据类型名
                            String columnTypeName = data.getColumnTypeName(i);
                            //所在的Catalog名字
                            //String catalogName = data.getCatalogName(i);
                            //对应数据类型的类
                            //String columnClassName = data.getColumnClassName(i);

                            //默认的列的标题
                            String columnLabel = data.getColumnLabel(i);
                            //获得列的模式
                            //  String schemaName = data.getSchemaName(i);
                            //某列类型的精确度(类型的长度)
                            //int precision = data.getPrecision(i);
                            //小数点后的位数
                            //int scale = data.getScale(i);
                            //获取某列对应的表名
                            //String tableName = data.getTableName(i);
                            // 是否自动递增
                            boolean isAutoInctement = data.isAutoIncrement(i);
                            //在数据库中是否为货币型
                            //boolean isCurrency = data.isCurrency(i);
                            //是否为空
                            int isNullable = data.isNullable(i);
                            //是否为只读
                            boolean isReadOnly = data.isReadOnly(i);
                            //能否出现在where中
                            boolean isSearchable = data.isSearchable(i);
                            if(rows.size() != columnCount){
                                RowBean rowBean = new RowBean(i,columnTypeName,columnDisplaySize,columnLabel,isAutoInctement,isNullable,isReadOnly,isSearchable);
                                rows.add(rowBean);
                            }
                            MsgBean row = new MsgBean(columnName,columnValue,columnTypeName,rows.get(i-1));
                            msgBeans.get(count).add(row);


                        }
                        count++;
                        if (count>=50){
                            Toast.makeText(MsgActivity.this, "最多显示50条数据", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //刷新列表
                            swipeRefreshLayout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MsgActivity.this, "连接异常，尝试重启APP", Toast.LENGTH_SHORT).show();
            }
        }
    }

}