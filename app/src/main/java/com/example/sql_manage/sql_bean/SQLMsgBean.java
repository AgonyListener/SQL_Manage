package com.example.sql_manage.sql_bean;

/**
 * @ClassName SQLMsgBean
 * @Description 存储连接信息
 * @Author 乐某
 * @Date 2022/8/3 14:39
 * @Version 1.0
 */
public class SQLMsgBean {
    // 标题
    private String title;
    //数据库地址
    private String dburl;
    //数据库端口
    private String dbport;
    //数据库名
    private String dbname;
    //用户名
    private String user;
    //密码
    private String password;

    private String stauts = "未知";

    public SQLMsgBean(String title, String dburl, String dbport, String dbname, String user, String password) {
        this.title = title;
        this.dburl = dburl;
        this.dbport = dbport;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
    }

    public String getmainURL() {
        return "jdbc:mysql://"+dburl+":"+dbport+"/"+dbname;
    }
    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDburl() {
        return dburl;
    }

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
