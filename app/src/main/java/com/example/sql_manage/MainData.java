package com.example.sql_manage;

import com.example.sql_manage.sql_bean.SQLMsgBean;
import com.example.sql_manage.sql_bean.TableBean;

/**
 * @ClassName MainData
 * @Description 存放当前访问的数据库的信息
 * @Author 乐某
 * @Date 2022/8/3 17:21
 * @Version 1.0
 */
public class MainData {
    // 当前状态的数据库
    public static SQLMsgBean sqlMsgBean;
    // 当前状态的数据表
    public static TableBean tableBean;

}
