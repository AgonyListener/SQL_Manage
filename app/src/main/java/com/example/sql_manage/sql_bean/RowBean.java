package com.example.sql_manage.sql_bean;

/**
 * @ClassName CowBean
 * @Description 存放列信息
 * @Author 乐某
 * @Date 2022/8/4 15:06
 * @Version 1.0
 */
public class RowBean {
    //第几列
    private int id;
    // 数据类型
    private String data_type;
    // 最大字符个数
    private int data_max;
    // 默认列标题
    private String data_row_title;

    //是否为自动递增
    private boolean isAutoInctement;
    //是否为空
    private int isNullable;
    //是否是只读
    private boolean isReadOnly;
    //是否允许出现在where中
    private boolean isSearchable;

    public RowBean(int id, String data_type, int data_max, String data_row_title, boolean isAutoInctement, int isNullable, boolean isReadOnly, boolean isSearchable) {
        this.id = id;
        this.data_type = data_type;
        this.data_max = data_max;
        this.data_row_title = data_row_title;
        this.isAutoInctement = isAutoInctement;
        this.isNullable = isNullable;
        this.isReadOnly = isReadOnly;
        this.isSearchable = isSearchable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public int getData_max() {
        return data_max;
    }

    public void setData_max(int data_max) {
        this.data_max = data_max;
    }

    public String getData_row_title() {
        return data_row_title;
    }

    public void setData_row_title(String data_row_title) {
        this.data_row_title = data_row_title;
    }

    public boolean isAutoInctement() {
        return isAutoInctement;
    }

    public void setAutoInctement(boolean autoInctement) {
        isAutoInctement = autoInctement;
    }

    public int isNullable() {
        return isNullable;
    }

    public void setNullable(int nullable) {
        isNullable = nullable;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isSearchable() {
        return isSearchable;
    }

    public void setSearchable(boolean searchable) {
        isSearchable = searchable;
    }
}
