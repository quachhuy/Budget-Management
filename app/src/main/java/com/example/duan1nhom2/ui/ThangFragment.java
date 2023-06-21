package com.example.duan1nhom2.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1nhom2.DAO.KhoanChiDAO;
import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThangFragment extends Fragment {
    DataBase dataBase;
    KhoanChiDAO khoanChiDAO;
    KhoanThuDAO khoanThuDAO;
    TextView tv_thu_thang, tv_chi_thang, tv_tichluy_thang, tv_1, tv_2, tv_3;
    Button btn_date_thang;
    EditText edt_thongke_thang;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thang, container, false);
        tv_thu_thang = view.findViewById(R.id.tv_thu_thang);
        tv_chi_thang = view.findViewById(R.id.tv_chi_thang);
        edt_thongke_thang = view.findViewById(R.id.edt_thongke_thang);
        tv_tichluy_thang = view.findViewById(R.id.tv_tichluy_thang);
        btn_date_thang = view.findViewById(R.id.btn_date_thang);
        tv_1 = view.findViewById(R.id.tv_1);
        tv_2 = view.findViewById(R.id.tv_2);
        tv_3 = view.findViewById(R.id.tv_3);
        tv_thu_thang.setVisibility(View.INVISIBLE);
        tv_chi_thang.setVisibility(View.INVISIBLE);
        tv_tichluy_thang.setVisibility(View.INVISIBLE);

        btn_date_thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase = new DataBase(getContext());
                khoanThuDAO = new KhoanThuDAO(dataBase);
                khoanChiDAO = new KhoanChiDAO(dataBase);
                String thang = edt_thongke_thang.getText().toString();
                if (thang.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa nhập tháng!", Toast.LENGTH_SHORT).show();
                } else {
                    int month = Integer.valueOf(thang);
                    if (month <= 0 || month > 12) {
                        Toast.makeText(getContext(), "Tháng phải lớn hơn 0 và nhỏ hơn 12", Toast.LENGTH_SHORT).show();
                    } else {
                        if (month > 0 && month < 10) {
                            thang = "0" + thang;
                        }
                        long sotienthu = khoanThuDAO.tienThuThang(thang);
                        long sotienchi = khoanChiDAO.tienChiThang(thang);
                        long tichluythang = sotienthu - sotienchi;
                        tv_thu_thang.setText(" + " + sotienthu + " VND");
                        tv_chi_thang.setText(" - " + sotienchi + " VND");
                        tv_tichluy_thang.setText(" " + tichluythang + " VND");
                        tv_thu_thang.setVisibility(View.VISIBLE);
                        tv_chi_thang.setVisibility(View.VISIBLE);
                        tv_tichluy_thang.setVisibility(View.VISIBLE);
                        tv_1.setVisibility(View.VISIBLE);
                        tv_2.setVisibility(View.VISIBLE);
                        tv_3.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        return view;
    }
}
