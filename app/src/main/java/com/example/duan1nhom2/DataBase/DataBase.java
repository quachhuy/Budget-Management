package com.example.duan1nhom2.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.duan1nhom2.DAO.KhoanChiDAO;
import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DAO.NguoiDungDAO;

public class
DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KhoanThuDAO.SQL_KHOANTHU);
        db.execSQL(KhoanChiDAO.SQL_KHOANCHI);
        db.execSQL(NguoiDungDAO.SQL_NGUOIDUNG);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
