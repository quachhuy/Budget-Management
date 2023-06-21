package com.example.duan1nhom2.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    DataBase dataBase;
    public static final String TABLE_NAME = "nguoidung";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String SQL_NGUOIDUNG = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_PASSWORD + " TEXT, " + COLUMN_PHONE + " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_FULLNAME + " TEXT)";

    public NguoiDungDAO(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public long insertNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, nguoiDung.getUsername());
        contentValues.put(COLUMN_PASSWORD, nguoiDung.getPassword());
        contentValues.put(COLUMN_PHONE, nguoiDung.getPhone());
        contentValues.put(COLUMN_EMAIL, nguoiDung.getEmail());
        contentValues.put(COLUMN_FULLNAME, nguoiDung.getFullname());

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public long updateNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, nguoiDung.getUsername());
        contentValues.put(COLUMN_PASSWORD, nguoiDung.getPassword());
        contentValues.put(COLUMN_PHONE, nguoiDung.getPhone());
        contentValues.put(COLUMN_EMAIL, nguoiDung.getEmail());
        contentValues.put(COLUMN_FULLNAME, nguoiDung.getFullname());

        return sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_USERNAME + "=?", new String[]{nguoiDung.getUsername()});
    }

    public long deleteNguoiDung(String userName) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_USERNAME + "=?", new String[]{userName});
    }

    public List<NguoiDung> getAllND() {
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        String Select = "SELECT * FROM " + TABLE_NAME;
        List<NguoiDung> nguoiDungList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(Select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            nguoiDung.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            nguoiDung.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            nguoiDung.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            nguoiDung.setFullname(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));

            nguoiDungList.add(nguoiDung);
            cursor.moveToNext();
        }
        cursor.close();
        return nguoiDungList;
    }
}

