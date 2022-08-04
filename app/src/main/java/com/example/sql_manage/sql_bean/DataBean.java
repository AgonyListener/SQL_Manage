package com.example.sql_manage.sql_bean;

/**
 * @ClassName DataBean
 * @Description 列表中的列表
 * @Author 乐某
 * @Date 2022/8/4 16:45
 * @Version 1.0
 */
public class DataBean {
    private String row_name;
    private String row_value;
    private String row_type;

    public DataBean(String row_name, String row_value, String row_type) {
        this.row_name = row_name;
        this.row_value = row_value;
        this.row_type = row_type;
    }

    public String getRow_name() {
        return row_name;
    }

    public void setRow_name(String row_name) {
        this.row_name = row_name;
    }

    public String getRow_value() {
        return row_value;
    }

    public void setRow_value(String row_value) {
        this.row_value = row_value;
    }

    public String getRow_type() {
        return row_type;
    }

    public void setRow_type(String row_type) {
        this.row_type = row_type;
    }
}
