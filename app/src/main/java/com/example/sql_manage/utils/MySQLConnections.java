package com.example.sql_manage.utils;
import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLConnections {
    //全局参数
    private static String driver;
    private String dbURL = "";
    private String user = "";
    private String password = "";
    private static MySQLConnections connection = null;
    private MySQLConnections(String dbURL, String user, String password) throws Exception {
        //设置参数
        driver = "com.mysql.jdbc.Driver";//每个都一样
        this.dbURL = dbURL;
        this.user = user;
        this.password = password;
    }


    public static Connection getConnection(String dbURL, String user, String password) {
        Connection conn = null;
        if (connection == null) {
            try {
                //开始连接
                connection = new MySQLConnections(dbURL,user,password);

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
