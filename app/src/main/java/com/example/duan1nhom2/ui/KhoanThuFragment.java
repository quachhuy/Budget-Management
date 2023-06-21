package com.example.duan1nhom2.ui;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1nhom2.Adapter.AdapterKhoanThu;
import com.example.duan1nhom2.DAO.KhoanChiDAO;
import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.KhoanThu;
import com.example.duan1nhom2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KhoanThuFragment extends Fragment {
    ListView lv_khoanthu;
    AdapterKhoanThu adapterKhoanThu;
    DataBase dataBase;
    List<KhoanThu> khoanThus;
    KhoanThuDAO khoanThuDAO;
    FloatingActionButton floatingActionButton;
    KhoanChiDAO khoanChiDAO;
    TextInputLayout input_nameThu, input_soTien;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_khoan_thu, container, false);
        lv_khoanthu = view.findViewById(R.id.lv_khoanThu);
        floatingActionButton = view.findViewById(R.id.btn_themThu);
        dataBase = new DataBase(getActivity());
        khoanThuDAO = new KhoanThuDAO(dataBase);
        khoanThus = khoanThuDAO.getAllKhoanThu();
        khoanChiDAO = new KhoanChiDAO(dataBase);
        adapterKhoanThu = new AdapterKhoanThu(getActivity(), khoanThus);
        lv_khoanthu.setAdapter(adapterKhoanThu);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(lv_khoanthu, "translationX", -1000f, 0f);
        objectAnimator.setDuration(1300);
        objectAnimator.start();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.add_thu, null);
                builder.setView(view1);
                final EditText edt_namethu = view1.findViewById(R.id.edt_nameThu);
                final EditText edt_sotien = view1.findViewById(R.id.edt_stThu);
                final TextView tv_ngaythu = view1.findViewById(R.id.tv_date);
                input_nameThu = view1.findViewById(R.id.input_nameThu);
                input_soTien = view1.findViewById(R.id.input_soTien);
                Button btn_datepiker = view1.findViewById(R.id.btn_datePicker);

                btn_datepiker.setOnClickListener(new View.OnClickListener() {
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
                                tv_ngaythu.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                // kiểm soát lỗi để trống
                if (edt_namethu.getText().length() == 0) {
                    input_nameThu.setError("Không được để trống");
                } else {
                    input_nameThu.setError(null);
                }
                edt_namethu.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            input_nameThu.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                if (edt_namethu.getText().length() == 0) {
                    input_soTien.setError("Không được để trống");
                } else {
                    input_soTien.setError(null);
                }
                edt_sotien.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            input_soTien.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (tv_ngaythu.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Bạn chưa chọn ngày!", Toast.LENGTH_SHORT).show();
                        } else if (edt_namethu.getText().toString().isEmpty() || edt_sotien.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        } else {
                            Date date = null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                date = simpleDateFormat.parse(tv_ngaythu.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            KhoanThu khoanThu = new KhoanThu();
                            khoanThu.setNamethu(edt_namethu.getText().toString());
                            khoanThu.setSotien(Long.valueOf(edt_sotien.getText().toString()));
                            khoanThu.setNgaythu(date);

                            dataBase = new DataBase(getActivity());
                            KhoanThuDAO khoanThuDAO;
                            khoanThuDAO = new KhoanThuDAO(dataBase);
                            long value = khoanThuDAO.insertKhoanThu(khoanThu);
                            if (value > 0) {
                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                            khoanThus = khoanThuDAO.getAllKhoanThu();
                            adapterKhoanThu.setDatachange(khoanThus);
                        }
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }
}
