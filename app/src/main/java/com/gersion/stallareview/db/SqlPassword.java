package com.gersion.stallareview.db;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.dao
 * @待完成
 * @创建时间 2016/9/27
 * @功能描述 数据库中的内容
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public interface SqlPassword {
    String TABLE_NAME="info";
    String DB_NAME = "tata";
    interface Table{
        String ID = "_id";
        String NAME = "name";
        public static final String CREATE_TABLE = "create table "+TABLE_NAME+"(" +
                ID+" integer primary key autoincrement ," +
                NAME+" text )";
    }
}
