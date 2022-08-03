package com.example.sql_manage.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sql_manage.MainData;
import com.example.sql_manage.R;
import com.example.sql_manage.sql_bean.SQLMsgBean;
import com.example.sql_manage.utils.MySQLConnections;
import com.google.android.material.card.MaterialCardView;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    private SQLMsgBean sqlMsgBean;
    private TextView textView,tag;
    String string="";

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        //获取数据库连接信息
        sqlMsgBean = MainData.sqlMsgBean;
        //数据库名字
        getSupportActionBar().setTitle(sqlMsgBean.getDbname());
        //返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();




        new Thread_reg().start();

    }
    private void initViews(){
        textView = findViewById(R.id.textView);
        tag = findViewById(R.id.notice);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    class Thread_reg extends Thread{
        @Override
        public void run() {
            try {
                con = MySQLConnections.getConnection(sqlMsgBean.getmainURL(),sqlMsgBean.getUser(),sqlMsgBean.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                String search_sql ="SELECT table_name FROM information_schema.tables WHERE table_schema = '"+sqlMsgBean.getDbname()+"' AND table_type = 'base table'";    //要执行的SQ
                if (con!=null) {
                    stmt = con.prepareStatement(search_sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);


                    ResultSet rs = stmt.executeQuery();//创建数据对象
                    while (rs.next()) {
                        string+="\n"+rs.getString(1);
                    }
                    con.commit();
                    rs.close();
                    stmt.close();
                    List<String> a = getDataBases(con);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (string.length()<=0){
                                tag.setText("无表或者连接异常【点击可编辑信息】");
                                tag.setBackgroundResource(R.color.red);
                            }else {
                                tag.setText("连接成功【点击可编辑信息】");
                                tag.setBackgroundResource(R.color.green);
                            }
                            string +="\n-------------------------\n";
                            for (int i = 0; i < a.size(); i++) {
                                string+="\n"+a.get(i);
                            }
                            textView.setText(string);
                            Toast.makeText(ManageActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }catch (SQLException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getDataBases(Connection connection) throws Exception {

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet catalogs = metaData.getCatalogs();
        ArrayList<String> dbs = new ArrayList<>();
        while (catalogs.next()) {
            String db = catalogs.getString(".TABLE_CAT");
            dbs.add(db);
        }

        return dbs;
    }
}