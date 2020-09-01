package com.guguscode.dapenduk.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.guguscode.dapenduk.Model.DataModel;
import com.guguscode.dapenduk.Presenter.DBHandler;
import com.guguscode.dapenduk.R;

public class EditData extends AppCompatActivity {

    EditText mEtNm, mEtAlm, mEtTpt, mEtPk;
    TextView mEtTgl;
    Button mBtnSimpan;
    DataModel dataModel;
    DBHandler dbhelp;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private Spinner spinnerJK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        getSupportActionBar().setTitle("DaPenduk");
        getSupportActionBar().setSubtitle("Edit Data Penduduk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.icupdate));
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEtNm = findViewById(R.id.etNm);
        spinnerJK = findViewById(R.id.etJk);
        mEtAlm = findViewById(R.id.etAlm);
        mEtTpt = findViewById(R.id.etTpt);
        mEtTgl = findViewById(R.id.etTgl);
        mEtPk = findViewById(R.id.etPkr);
        mBtnSimpan = findViewById(R.id.btnSimpan);
        dbhelp = new DBHandler(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            dataModel = dbhelp.getData(bundle.getInt("ID"));
            mEtNm.setText(dataModel.getNama());
            spinnerJK.getSelectedItem();
            mEtAlm.setText(dataModel.getAlm());
            mEtTpt.setText(dataModel.getTpt());
            mEtTgl.setText(dataModel.getTgl());
            mEtPk.setText(dataModel.getPk());
        }
        mBtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelp.updateData(dataModel.getId(), mEtNm.getText().toString(), spinnerJK.getSelectedItem().toString(), mEtAlm.getText().toString(),
                        mEtTpt.getText().toString(),mEtTgl.getText().toString(), mEtPk.getText().toString());
                finish();
            }
        });
    }
    }

