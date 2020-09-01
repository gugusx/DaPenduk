package com.guguscode.dapenduk.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.guguscode.dapenduk.Model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_dapenduk"; // NAMA DATABASE
    private static final String TABLE_PEND = "table_penduduk"; // NAMA TABEL
    private static  String COLUMN_ID = "id"; // NAMA KOLOM ID
    private static  String COLUMN_NAMA = "nama"; // NAMA KOLOM NAMA
    private static  String COLUMN_JK = "jenis_kelamin";
    private static  String COLUMN_ALM = "alamat";
    private static  String COLUMN_TEMPATLAHIR = "tempat_lahir"; // NAMA KOLOM TEMPAT LAHIR
    private static  String COLUMN_TGL = "tanggal_lahir";
    private static  String COLUMN_PK = "pekerjaan";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_PEND + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAMA + " TEXT, "
                + COLUMN_JK + " TEXT, " + COLUMN_ALM + " TEXT, "
                + COLUMN_TEMPATLAHIR + " TEXT, " + COLUMN_TGL + " DATE, "
                + COLUMN_PK + " TEXT); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEND);
        onCreate(db);
    }

    /**create record**/
    public void insertData(String nm, String jk, String alm, String Tp, String Tg, String Pk) {

        String insertData = "INSERT INTO "+ TABLE_PEND + " ("+ COLUMN_NAMA +","+COLUMN_JK+","+ COLUMN_ALM+","+ COLUMN_TEMPATLAHIR+"," + COLUMN_TGL+","+ COLUMN_PK+") VALUES ('"+nm +"', '"+jk+"', '"+alm+"', '"+Tp+"', '"+Tg+"', '"+Pk+"')";
        this.getWritableDatabase().execSQL(insertData);
    }

    public void updateData(int id, String nm, String jk, String alm, String Tp, String Tg, String Pk){
        String updateData = "UPDATE "+TABLE_PEND+ " SET "+ COLUMN_NAMA + "= '"+nm +"', "+COLUMN_JK + "= '"+jk + "', "+COLUMN_ALM + "= '"+alm +"', "+COLUMN_TEMPATLAHIR + "= '"+Tp+"', "+COLUMN_TGL + "= '"+Tg+"', "+COLUMN_PK +"= '"+Pk+"' WHERE "+COLUMN_ID +" ="+id;
        this.getWritableDatabase().execSQL(updateData);
    }

    public void deleteData(int id){
        String deleteData = "DELETE FROM "+TABLE_PEND +" WHERE id="+id;
        this.getWritableDatabase().execSQL(deleteData);
    }

    public DataModel getData(int id){
        DataModel model = null;
        String selectData = "SELECT * FROM "+TABLE_PEND + " WHERE id="+String.valueOf(id);
        Cursor data = this.getWritableDatabase().rawQuery(selectData, null);
        if(data.moveToFirst()){
            model = new DataModel(Integer.parseInt(data.getString(data.getColumnIndex(COLUMN_ID))),
                    data.getString(data.getColumnIndex(COLUMN_NAMA)), data.getString(data.getColumnIndex(COLUMN_JK)),
                    data.getString(data.getColumnIndex(COLUMN_ALM)), data.getString(data.getColumnIndex(COLUMN_TEMPATLAHIR)),
                    data.getString(data.getColumnIndex(COLUMN_TGL)), data.getString(data.getColumnIndex(COLUMN_PK)));
        }
        return model;
    }

    public List<DataModel> getAll(){
        List<DataModel> model = new ArrayList<>();
        String selectData = "SELECT * FROM "+TABLE_PEND;
        Cursor data = this.getWritableDatabase().rawQuery(selectData, null);
        if(data.moveToFirst()){
            do{
                model.add(new DataModel(Integer.parseInt(data.getString(data.getColumnIndex(COLUMN_ID))),
                        data.getString(data.getColumnIndex(COLUMN_NAMA)), data.getString(data.getColumnIndex(COLUMN_JK)),
                        data.getString(data.getColumnIndex(COLUMN_ALM)), data.getString(data.getColumnIndex(COLUMN_TEMPATLAHIR)),
                        data.getString(data.getColumnIndex(COLUMN_TGL)), data.getString(data.getColumnIndex(COLUMN_PK))));
            }while (data.moveToNext());
        }
        return model;
    }

    }


