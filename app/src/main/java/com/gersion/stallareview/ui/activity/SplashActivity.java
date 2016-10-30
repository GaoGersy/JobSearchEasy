package com.gersion.stallareview.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.gersion.stallareview.R;
import com.gersion.stallareview.db.PasswordDao;
import com.gersion.stallareview.utils.SpfUtils;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    private static final String IS_IMPORT = "isImport";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
        intentTo();
    }

    private void initData() {
        boolean isImport = SpfUtils.getBoolean(this, IS_IMPORT, false);
        if (!isImport){
            PasswordDao passwordDao = new PasswordDao(this);
       }
    }

    private void  intentTo(){
        Observable.just("测试")
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        SystemClock.sleep(1500);
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }

}
