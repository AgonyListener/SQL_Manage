package com.example.sql_manage.sql_bean;

/**
 * @ClassName TableBean
 * @Description 表
 * @Author 乐某
 * @Date 2022/8/4 14:01
 * @Version 1.0
 */
public class TableBean {
    private int id;
    private String name;
    private String time;


    public TableBean(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
