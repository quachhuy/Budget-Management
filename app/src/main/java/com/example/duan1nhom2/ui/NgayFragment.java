package com.example.duan1nhom2.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1nhom2.DAO.KhoanChiDAO;
import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NgayFragment extends Fragment {

    TextView tv_thongke_ngay, tv_thu_ngay, tv_chi_ngay, tv_tichluy_ngay, tv_text1, tv_text2, tv_text3;
    Button btn_date_ngay;
    DataBase dataBase;
    KhoanThuDAO khoanThuDAO;
    KhoanChiDAO khoanChiDAO;
    Date date = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ngay, container, false);
        tv_thongke_ngay = view.findViewById(R.id.tv_thongke_ngay);
        tv_thu_ngay = view.findViewById(R.id.tv_thu_ngay);
        tv_chi_ngay = view.findViewById(R.id.tv_chi_ngay);
        tv_tichluy_ngay = view.findViewById(R.id.tv_tichluy_ngay);
        btn_date_ngay = view.findViewById(R.id.btn_date_ngay);
        tv_text1 = view.findViewById(R.id.tv_text1);
        tv_text2 = view.findViewById(R.id.tv_text2);
        tv_text3 = view.findViewById(R.id.tv_text3);
        tv_thu_ngay.setVisibility(View.INVISIBLE);
        tv_chi_ngay.setVisibility(View.INVISIBLE);
        tv_tichluy_ngay.setVisibility(View.INVISIBLE);
        tv_thongke_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tv_thongke_ngay.setText(simpleDateFormat.format(calendar.getTime()));
                        date = calendar.getTime();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btn_date_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date == null) {
                    Toast.makeText(getContext(), "Bạn chưa chọn ngày!", Toast.LENGTH_SHORT).show();
                } else {
                    dataBase = new DataBase(getContext());
                    khoanThuDAO = new KhoanThuDAO(dataBase);
                    khoanChiDAO = new KhoanChiDAO(dataBase);
                    long thungay = khoanThuDAO.tienThuNgay(date);
                    long chingay = khoanChiDAO.tienChiNgay(date);
                    long tichluy = thungay - chingay;
                    tv_thu_ngay.setText(" + " + thungay + " VND");
                    tv_chi_ngay.setText(" - " + chingay + " VND");
                    tv_tichluy_ngay.setText(" " + tichluy + " VND");
                    tv_thu_ngay.setVisibility(View.VISIBLE);
                    tv_chi_ngay.setVisibility(View.VISIBLE);
                    tv_tichluy_ngay.setVisibility(View.VISIBLE);
                    tv_text1.setVisibility(View.VISIBLE);
                    tv_text2.setVisibility(View.VISIBLE);
                    tv_text3.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}
