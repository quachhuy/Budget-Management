package com.example.duan1nhom2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1nhom2.Adapter.AdapterNguoiDung;
import com.example.duan1nhom2.DAO.NguoiDungDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ListNguoiDungActivity extends AppCompatActivity {
    ListView list_nguoidung;
    NguoiDungDAO nguoiDungDAO;
    AdapterNguoiDung adapterNguoiDung;
    DataBase dataBase;
    List<NguoiDung> nguoiDungList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_nguoidung);

        list_nguoidung =findViewById(R.id.lvNguoiDung);
        dataBase = new DataBase(this);
        nguoiDungDAO = new NguoiDungDAO(dataBase);
        nguoiDungList = nguoiDungDAO.getAllND();
        adapterNguoiDung = new AdapterNguoiDung(ListNguoiDungActivity.this,nguoiDungList);
        list_nguoidung.setAdapter(adapterNguoiDung);

    }
}
