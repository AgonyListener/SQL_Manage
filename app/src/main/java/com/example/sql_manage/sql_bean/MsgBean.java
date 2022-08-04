package com.example.sql_manage.sql_bean;

/**
 * @ClassName MsgBean
 * @Description 存放行的数据和类型
 * @Author 乐某
 * @Date 2022/8/4 15:05
 * @Version 1.0
 */
public class MsgBean {
    private String name;
    private String value;
    private String type;
    private RowBean rowBean;

    public MsgBean(String name, String value, String type, RowBean rowBean) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.rowBean = rowBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RowBean getRowBean() {
        return rowBean;
    }

    public void setRowBean(RowBean rowBean) {
        this.rowBean = rowBean;
    }
}
