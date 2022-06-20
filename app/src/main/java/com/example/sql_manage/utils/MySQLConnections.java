package com.example.sql_manage.utils;
import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLConnections {
    //全局参数
    private String driver = "";
    private String dbURL = "";
    private String user = "";
    private String password = "";
    private static MySQLConnections connection = null;
    private MySQLConnections() throws Exception {
        //设置参数
//        driver = "com.mysql.jdbc.Driver";//每个都一样
//        dbURL = "jdbc:mysql://rm-f8z1i1s40fh840cl83o.mysql.rds.aliyuncs.com:3306/library";//这里是数据库地址一般是 【数据库地址:3306/数据库名】
//        user = "admin_library";//你的数据库账号
//        password = "Aa123456";//你的数据库密码
        System.out.println("dbURL:" + dbURL);//打印地址，手机上看不见，用于调试
    }
    public static Connection getConnection() {
        Connection conn = null;
        if (connection == null) {
            try {
                //开始连接
                connection = new MySQLConnections();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            //提交信息，来访问你的数据库
            Class.forName(connection.driver);
            conn = DriverManager.getConnection(connection.dbURL,
                    connection.user, connection.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //最后返回连接，让你的程序在线程里对conn进行操作，读取内容
        return conn;
    }
}
