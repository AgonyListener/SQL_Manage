package com.example.sql_manage;

import static com.example.sql_manage.utils.Tools.isNumeric;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.sql_manage.adapter.InformationAdapter;
import com.example.sql_manage.sql_bean.SQLMsgBean;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private List<SQLMsgBean> msgBeans = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView sql_list;
    private InformationAdapter adapter;
    private List<SQLMsgBean> InformationList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置标题：：：：如果getSupportActionBar()对象为空，会报错空指针
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.sql_manage);

        // 恢复本地列表信息
        data_resume();
        //加载列表
        information_list_init();
        //加载下拉刷新
        swip_fresh_init();





    }

    // 绑定右上角的menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return true;
    }

    // 当点击添加按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        ShowAddDialog();


        return false;
    }

    // 添加功能的弹窗
    private void ShowAddDialog() {
        final AlertDialog mDialog = new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.no, null)
                .create();
        mDialog.setTitle(getString(R.string.sql));
        final View contentView = getLayoutInflater().inflate(R.layout.dialog_edit, null);
        mDialog.setView(contentView);
        final TextInputLayout textInputLayout = contentView.findViewById(R.id.textInputLayout);
        final TextInputLayout textInputLayout1 = contentView.findViewById(R.id.textInputLayout1);
        final TextInputLayout textInputLayout2 = contentView.findViewById(R.id.textInputLayout2);
        final TextInputLayout textInputLayout3 = contentView.findViewById(R.id.textInputLayout3);
        final TextInputLayout textInputLayout4 = contentView.findViewById(R.id.textInputLayout4);
        textInputLayout.setHint(R.string.edit_hint_title);
        textInputLayout1.setHint(R.string.edit_hint_url_port);
        textInputLayout2.setHint(R.string.edit_hint_db_name);
        textInputLayout3.setHint(R.string.edit_hint_account);
        textInputLayout4.setHint(R.string.edit_hint_password);
        final TextInputEditText edit1 = contentView.findViewById(R.id.editText);
        final TextInputEditText edit2 = contentView.findViewById(R.id.editText1);
        final TextInputEditText edit3 = contentView.findViewById(R.id.editText2);
        final TextInputEditText edit4 = contentView.findViewById(R.id.editText3);
        final TextInputEditText edit5 = contentView.findViewById(R.id.editText4);
        edit1.setInputType(InputType.TYPE_CLASS_TEXT);
        edit1.setSingleLine(false);
        edit2.setInputType(InputType.TYPE_CLASS_TEXT);
        edit2.setSingleLine(false);
        edit3.setInputType(InputType.TYPE_CLASS_TEXT);
        edit3.setSingleLine(false);
        edit4.setInputType(InputType.TYPE_CLASS_TEXT);
        edit4.setSingleLine(false);
        edit5.setInputType(InputType.TYPE_CLASS_TEXT);
        edit5.setSingleLine(false);
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(edit1.getText().toString())) {
                            textInputLayout.setError(getString(R.string.edit_eror_title));
                            textInputLayout.setErrorEnabled(true);
                        } else if (TextUtils.isEmpty(edit2.getText().toString())) {
                            textInputLayout1.setError(getString(R.string.edit_eror_url_port));
                            textInputLayout1.setErrorEnabled(true);
                        } else if (TextUtils.isEmpty(edit3.getText().toString())) {
                            textInputLayout2.setError(getString(R.string.edit_eror_db_name));
                            textInputLayout2.setErrorEnabled(true);
                        } else if (TextUtils.isEmpty(edit4.getText().toString())) {
                            textInputLayout3.setError(getString(R.string.edit_eror_account));
                            textInputLayout3.setErrorEnabled(true);
                        } else if (TextUtils.isEmpty(edit5.getText().toString())) {
                            textInputLayout4.setError(getString(R.string.edit_eror_password));
                            textInputLayout4.setErrorEnabled(true);
                        }  else {
                            String title= edit1.getText().toString().trim();
                            String url_port= edit2.getText().toString().trim();
                            String db_name= edit3.getText().toString().trim();
                            String account= edit4.getText().toString().trim();
                            String password= edit5.getText().toString().trim();

                            // 判断url_port输入是否正确
                            if (is_url_port_Regal(url_port)){
                                // 创建连接信息的类
                                SQLMsgBean sqlMsgBean = new SQLMsgBean(title,url_port.split(":")[0],url_port.split(":")[1],db_name,account,password);
                                msgBeans.add(sqlMsgBean);
                                data_save();
                                // 刷新列表
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, R.string.finish, Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }else {
                                Toast.makeText(MainActivity.this,
                                        R.string.toast_error_port,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
            }
        });
        mDialog.show();
        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / 5 * 4;
        mDialog.getWindow().setAttributes(layoutParams);
    }
    // 判断url_port输入是否正确
    private boolean is_url_port_Regal(String text) {
        if (text.contains(":")) {
            String[] parts = text.split(":");
            //判断port是否是数字
            if (isNumeric(parts[1])) {
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        sql_list.setLayoutManager(linearLayoutManager);
        adapter = new InformationAdapter(msgBeans);
        sql_list.setAdapter(adapter);


        adapter.setOnItemClickListener(new InformationAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,ManageActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new InformationAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                // 删除数据库列表
                final BottomSheetDialog bsd = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetEdit);
                final View v = View.inflate(MainActivity.this, R.layout.dialog_del, null);
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
                        msgBeans.remove(position);
                        data_save();
                        adapter.notifyDataSetChanged();
                        bsd.dismiss();
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
                        data_resume();
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    /**
     * 恢复列表数据
     * @author 乐某
     * @date 2022/7/26 14:59
     */
    private void data_resume(){
        SharedPreferences sp =getSharedPreferences("list",MODE_PRIVATE);
        if (sp.getString("lists","").isEmpty()){
            return;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<SQLMsgBean>>() {}.getType();
        List<SQLMsgBean> list1 = gson.fromJson(sp.getString("lists",""), listType);
        msgBeans.clear();
        msgBeans.addAll(list1);

    }
    /**
     * 保存列表数据
     * @author 乐某
     * @date 2022/7/26 14:59
     */
    private void data_save(){
        SharedPreferences sp =getSharedPreferences("list",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        Gson gson = new Gson();
        String data1 = gson.toJson(msgBeans);
        editor.putString("lists", data1);
        editor.commit();
    }
}