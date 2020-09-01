package com.guguscode.dapenduk.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.guguscode.dapenduk.Presenter.DBHandler;
import com.guguscode.dapenduk.R;

import java.util.Calendar;

public class TambahData extends AppCompatActivity {

    EditText mEtNm, mEtAlm, mEtTpt, mEtPk;
    TextView mEtTgl;
    Button mBtnSimpan, mBtnTampil;

    DBHandler dbhelp;

    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private Spinner spinnerJK;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        getSupportActionBar().setTitle("DaPenduk");
        getSupportActionBar().setSubtitle("Tambah Data Penduduk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.add));
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEtNm = findViewById(R.id.etNm);
        spinnerJK = findViewById(R.id.etJk);
        mEtAlm = findViewById(R.id.etAlm);
        mEtTpt = findViewById(R.id.etTpt);
        mEtTgl = findViewById(R.id.etTgl);
        mEtPk = findViewById(R.id.etPkr);
        mBtnSimpan = findViewById(R.id.btnSimpan);
        mBtnTampil = findViewById(R.id.btnTampil);
        dbhelp = new DBHandler(this);

        mBtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanData();
            }
        });

        mBtnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LihatData.class));
            }
        });



        mEtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int tahun = calendar.get(Calendar.YEAR);
                int bulan = calendar.get(Calendar.MONTH);
                int hari = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(TambahData.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDataSetListener,
                        tahun,bulan,hari);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int tahun, int bulan, int hari) {
                bulan = bulan +1;
                String tanggal = hari + "/" + bulan + "/" + tahun;
                mEtTgl.setText(tanggal);
            }
        };

    }


    private void SimpanData() {

        String form_nama = mEtNm.getText().toString();
        String form_jk = spinnerJK.getSelectedItem().toString();
        String form_alm = mEtAlm.getText().toString();
        String form_tempatlahir = mEtTpt.getText().toString();
        String form_tgl = mEtTgl.getText().toString();
        String form_pk = mEtPk.getText().toString();

        if (form_nama.isEmpty()) {
            mEtNm.setError("Kolom tidak boleh kosong");
            mEtNm.requestFocus();
        }
        if (form_alm.isEmpty()) {
            mEtAlm.setError("Kolom tidak boleh kosong");
            mEtAlm.requestFocus();
        }
        if (form_tempatlahir.isEmpty()) {
            mEtTpt.setError("Kolom tidak boleh kosong");
            mEtTpt.requestFocus();
        } else {

            dbhelp.insertData(form_nama, form_jk, form_alm, form_tempatlahir, form_tgl, form_pk);
            Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LihatData.class));
            //mEtNm.setText(null);
            //mEtAlm.setText(null);
            //mEtTpt.setText(null);
            //mEtTgl.setText(null);
            //mEtPk.setText(null);
        }
    }

}
