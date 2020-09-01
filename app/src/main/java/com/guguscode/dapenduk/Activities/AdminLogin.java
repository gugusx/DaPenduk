package com.guguscode.dapenduk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.guguscode.dapenduk.Model.AdminLoginModel;
import com.guguscode.dapenduk.Presenter.AdminLoginPresenter;
import com.guguscode.dapenduk.R;
import com.guguscode.dapenduk.View.AdminLoginView;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener, AdminLoginView {

    EditText etUn, etPass;
    Button btnAdm;

    AdminLoginPresenter adminLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().setTitle("DaPenduk");
        getSupportActionBar().setSubtitle("Masuk sebagai Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.tooladm) );
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        etUn = findViewById(R.id.usern);
        etPass = findViewById(R.id.userpass);
        btnAdm = findViewById(R.id.login_btn);
        btnAdm.setOnClickListener(this);


        adminLoginPresenter = new AdminLoginModel(AdminLogin.this);
    }


    @Override
    public void onClick(View v) {
        String username = etUn.getText().toString();
        String password = etPass.getText().toString();

        adminLoginPresenter.runLogin(username, password);
    }

    @Override
    public void loginValid() {
        Toast.makeText(getApplicationContext(),"Username dan kata sandi harus diisi", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(getApplicationContext(),"Login berhasil", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LihatData.class));
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(),"Periksa kembali akun Anda", Toast.LENGTH_LONG).show();
    }
}
