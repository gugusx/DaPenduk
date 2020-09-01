package com.guguscode.dapenduk.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.guguscode.dapenduk.HomePage;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context mCtx;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME ="LOGIN";
    private static final String LOGIN = "IS_LOGIN";

    public SessionManager(Context mCtx) {
        this.mCtx = mCtx;
        sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public boolean isLoggin(){
        boolean status = false;
        status = sharedPreferences.getBoolean(LOGIN, false);
        return status;
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(mCtx, HomePage.class);
            mCtx.startActivity(i);
        }
    }


    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(mCtx, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(i);
    }
}
