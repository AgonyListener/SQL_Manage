package com.example.sql_manage.activity;

import static com.example.sql_manage.utils.Tools.getTables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sql_manage.MainData;
import com.example.sql_manage.R;
import com.example.sql_manage.adapter.TablesAdapter;
import com.example.sql_manage.sql_bean.SQLMsgBean;
import com.example.sql_manage.sql_bean.TableBean;
import com.example.sql_manage.utils.MySQLConnections;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ManageActivity extends AppCompatActivity {
    private SQLMsgBean sqlMsgBean;
    private TextView tag;

    private List<TableBean> msgBeans = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView sql_list;
    private TablesAdapter adapter;

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        //获取数据库连接信息
        sqlMsgBean = MainData.sqlMsgBean;
        //数据库名字
        Objects.requireNonNull(getSupportActionBar()).setTitle(sqlMsgBean.getDbname());
        //返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 初始化view
        initViews();
        //加载列表
        information_list_init();
        //初始化下拉刷新
        swip_fresh_init();
        //恢复列表数据
        data_resume();

    }
    private void initViews(){

        tag = findViewById(R.id.notice);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageActivity.this);
        sql_list.setLayoutManager(linearLayoutManager);
        adapter = new TablesAdapter(msgBeans);
        sql_list.setAdapter(adapter);


        adapter.setOnItemClickListener(new TablesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                MainData.tableBean = msgBeans.get(position);
                Intent intent = new Intent(ManageActivity.this, MsgActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new TablesAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                // 删除数据库列表
                final BottomSheetDialog bsd = new BottomSheetDialog(ManageActivity.this, R.style.BottomSheetEdit);
                final View v = View.inflate(ManageActivity.this, R.layout.dialog_table_del, null);
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

    //设置返回键
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    // 更新数据
    class Thread_updata extends Thread{
        @Override
        public void run() {
            try {
                con = MySQLConnections.getConnection(sqlMsgBean.getmainURL(),sqlMsgBean.getUser(),sqlMsgBean.getPassword());
                String search_sql ="SELECT table_name FROM information_schema.tables WHERE table_schema = '"+sqlMsgBean.getDbname()+"' AND table_type = 'base table'";    //要执行的SQ
                if (con!=null) {
                    stmt = con.prepareStatement(search_sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    stmt.close();
                    List<String> a = getTables(con,sqlMsgBean.getDbname());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (a.size()<=0){
                                tag.setText("无表或者连接异常【点击可编辑信息】");
                                tag.setBackgroundResource(R.color.red);
                            }else {
                                tag.setText("连接成功【点击可编辑信息】");
                                tag.setBackgroundResource(R.color.green);
                            }
                            msgBeans.clear();
                            for (int i = 0; i < a.size(); i++) {
                                TableBean tableBean = new TableBean(i+1,a.get(i),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                msgBeans.add(tableBean);
                            }
                            //刷新列表
                            swipeRefreshLayout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                            data_save();
                        }
                    });
                    con.close();
                }
            }catch (Exception e) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ManageActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    /**
     * 恢复列表数据
     * @author 乐某
     * @date 2022/7/26 14:59
     */
    private void data_resume(){
        SharedPreferences sp =getSharedPreferences("list1",MODE_PRIVATE);
        if (sp.getString("lists","").isEmpty()){
            // 如果数据为空，就联网读取
            new Thread_updata().start();
            return;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TableBean>>() {}.getType();
        List<TableBean> list1 = gson.fromJson(sp.getString("lists",""), listType);
        msgBeans.clear();
        msgBeans.addAll(list1);

    }
    /**
     * 保存列表数据
     * @author 乐某
     * @date 2022/7/26 14:59
     */
    private void data_save(){
        SharedPreferences sp =getSharedPreferences("list1",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        Gson gson = new Gson();
        String data1 = gson.toJson(msgBeans);
        editor.putString("lists", data1);
        editor.commit();
    }

}