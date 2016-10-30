package com.gersion.stallareview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.dao
 * @待完成
 * @创建时间 2016/9/27
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class PasswordDao {
    private Context mContext;
    private PasswordOpenHelper mHelper;
    private String password;

    public PasswordDao(Context context) {
        mContext = context;
        mHelper = new PasswordOpenHelper(mContext);
    }

    public boolean add(String location, String name, String pwd) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlPassword.Table.NAME, name);
        long insert = db.insert(SqlPassword.TABLE_NAME, null, values);
        db.close();
        return insert != -1;
    }

    public boolean add(String name) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlPassword.Table.NAME, name);
        long insert = db.insert(SqlPassword.TABLE_NAME, null, values);
        db.close();
        return insert != -1;
    }

    public boolean addAll(List<String> data) {
        if (data == null) {
            return false;
        }
        boolean add = false;
        for (int i = 0; i < data.size(); i++) {
            add = add(data.get(i));
        }
        return add;
    }

    //关闭Helper
    public void destory() {
        mHelper.close();
    }

    public List<String> query() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<String> data = new ArrayList<>();
        String[] columns = new String[]{
                SqlPassword.Table.NAME
        };
        Cursor cursor = db.query(SqlPassword.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                data.add(name);
            }
        }
        cursor.close();
        db.close();
        return data;
    }



    //抽象的查询某个条目，不知道传入的数据到底是哪一种，用于被其它方法调用
    public boolean update(String id, String keyer) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] args = new String[]{id};
        ContentValues columns = new ContentValues();
        columns.put(SqlPassword.Table.NAME,name);
        int update = db.update(SqlPassword.TABLE_NAME, columns, "_id=?", args);
        db.close();
        return update > 0;
    }

    //删除单个
    public boolean delete(String id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delete = db.delete(SqlPassword.TABLE_NAME, "_id=?", new String[]{id});
        db.close();
        return delete > 0;
    }

    //删除全部
    public boolean deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delete = db.delete(SqlPassword.TABLE_NAME, null, null);
        db.close();
        return delete>0;
    }


}
