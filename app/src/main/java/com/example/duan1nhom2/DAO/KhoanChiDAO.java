package com.example.duan1nhom2.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.KhoanChi;
import com.example.duan1nhom2.Model.KhoanThu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhoanChiDAO {
    DataBase dataBase;
    String dateInput = null;
    public static final String TABLE_NAME = "khoanchi";
    public static final String COLUMN_IDCHI = "idchi";
    public static final String COLUMN_NAME = "namechi";
    public static final String COLUMN_SOTIEN = "sotienchi";
    public static final String COLUMN_NGAYCHI = "ngaychi";

    public static final String SQL_KHOANCHI = "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_IDCHI + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " text," + COLUMN_SOTIEN + " long," + COLUMN_NGAYCHI + " date);";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public KhoanChiDAO(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public long insertKhoanChi(KhoanChi khoanChi) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, khoanChi.getNamechi());
        contentValues.put(COLUMN_SOTIEN, khoanChi.getSotienchi());
        contentValues.put(COLUMN_NGAYCHI, sdf.format(khoanChi.getNgaychi()));

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public long updateKhoanChi(KhoanChi khoanChi) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, khoanChi.getNamechi());
        contentValues.put(COLUMN_SOTIEN, khoanChi.getSotienchi());
        contentValues.put(COLUMN_NGAYCHI, sdf.format(khoanChi.getNgaychi()));

        return sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_IDCHI + "=?", new String[]{String.valueOf(khoanChi.getIdchi())});
    }

    public long deleteKhoanChi(int id) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_IDCHI + "=?", new String[]{String.valueOf(id)});
    }

    public List<KhoanChi> getAllKhoanChi() {
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        List<KhoanChi> chiList = new ArrayList<>();

        String select = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            KhoanChi khoanChi = new KhoanChi();
            khoanChi.setIdchi(cursor.getInt(cursor.getColumnIndex(COLUMN_IDCHI)));
            khoanChi.setNamechi(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            khoanChi.setSotienchi(cursor.getLong(cursor.getColumnIndex(COLUMN_SOTIEN)));
            try {
                khoanChi.setNgaychi((Date) sdf.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NGAYCHI))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chiList.add(khoanChi);
            cursor.moveToNext();
        }
        cursor.close();
        return chiList;
    }

    // truy vấn thống kê
    public long tienChiNgay(Date date) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateInput = sdf.format(date);
        long sotien = 0;
        String SQL = "SELECT sum(sotienchi) as tongchi FROM khoanchi WHERE ngaychi = " + '"' + dateInput + '"';
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long t = cursor.getLong(0);
            Log.e("t", cursor + "");
            sotien += t;
            cursor.moveToNext();
        }
        cursor.close();
        return sotien;
    }

    public long tienChiThang(String thang) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        long sotienthang = 0;
        String SQL = "SELECT sum(sotienchi) as tongchi FROM khoanchi WHERE strftime('%m',ngaychi) " + " = " + '"' + thang + '"';
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long t = cursor.getLong(0);
            sotienthang += t;
            cursor.moveToNext();
        }
        cursor.close();
        return sotienthang;
    }
    public long tienChiNam(String nam) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        long sotiennam = 0;
        String SQL = "SELECT sum(sotienchi) as tongchi FROM khoanchi WHERE strftime('%Y',ngaychi) " + " = " + '"' + nam + '"';
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long t = cursor.getLong(0);
            sotiennam += t;
            cursor.moveToNext();
        }
        cursor.close();
        return sotiennam;
    }
}
