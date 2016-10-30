package com.gersion.stallareview.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.ListView;

import com.gersion.stallareview.R;
import com.gersion.stallareview.adapter.ContentAdapter;
import com.gersion.stallareview.adapter.SearchAdapter;
import com.gersion.stallareview.db.PasswordDao;
import com.gersion.stallareview.utils.DialogHelp;
import com.gersion.stallareview.utils.SpfUtils;
import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String IS_IMPORT = "isImport";
    private SearchView sv_search;
    private ListView lv_result;
    private PasswordDao mPasswordDao;
    private List<String> mData;
    private List<String> mSearchData = new ArrayList<>();
    private ContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPasswordDao = new PasswordDao(this);
        initView();
        initEvent();
        iniData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isImport = SpfUtils.getBoolean(this, "isImport", false);
        if (!isImport){
            showFirstDiaglog();
        }
        if (mData.size()==0){
            mPasswordDao.destory();
            mPasswordDao = new PasswordDao(this);
            mData = mPasswordDao.query();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mData == null){
            Logger.d("执行到这里了");

        }
    }

    private void showFirstDiaglog() {
        AlertDialog.Builder messageDialog = DialogHelp.getMessageDialog(this, "根据百度贴巴里面网友反馈的数据制作的培训机构查询，防止被培训机构忽悠。保护深圳通财产安全！");
        messageDialog.show();

    }

    private void initView() {
        sv_search = (SearchView) findViewById(R.id.sv_search);
        lv_result = (ListView) findViewById(R.id.lv_result);
    }


    private void iniData() {
        importData();
        mData = mPasswordDao.query();
        mAdapter = new ContentAdapter(this,mData);
        lv_result.setAdapter(mAdapter);
    }

    private void initEvent() {
        sv_search.setOnQueryTextListener(this);
    }

    private void readFromFile() {
        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(getResources().getAssets().open("aaa.txt")));
            String result = null;
            while ((result=br.readLine())!=null){
                mPasswordDao.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        CharSequence query = sv_search.getQuery();
        String condition = query.toString().trim();
        onSearch(condition);
        if (condition.length()!=0){
            SearchAdapter adapter = new SearchAdapter(this,mSearchData);
            lv_result.setAdapter(adapter);
        }else{
            lv_result.setAdapter(mAdapter);
        }
        return true;
    }

    private void onSearch(String value){
        mSearchData.clear();
        for (String str : mData) {
            if (isMatch(str,value)){
                mSearchData.add(str);
            }
        }
    }

    private boolean isMatch(String content,String condition){
        StringBuilder regex = new StringBuilder();
        for (int i = 0;i<condition.length();i++){
            regex.append(".*"+condition.charAt(i));
            if (i==condition.length()-1){
                regex.append(".*");
            }
        }
Logger.d(regex.toString());
        if (content.matches(regex.toString())){
            return true;
        }
        return false;
    }

    private void importData() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File("/data/data/com.gersion.stallareview/databases/");
            Logger.d(file.exists());
            bis = new BufferedInputStream(getResources().getAssets().open("tata.db"));
            FileOutputStream out = new FileOutputStream("/data/data/com.gersion.stallareview/databases/tata.db");
            bos = new BufferedOutputStream(out);
            int len;
            byte [] buf = new byte[1024];
            while ((len = bis.read(buf))!= -1){
                bos.write(buf,0,len);
            }
//            SpfUtils.putBoolean(this,IS_IMPORT,true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bis!=null){
                    bis.close();
                }
                if (bos!=null){
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
