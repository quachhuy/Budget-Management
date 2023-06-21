package com.example.duan1nhom2.ui;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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

import com.example.duan1nhom2.Adapter.AdapterKhoanChi;
import com.example.duan1nhom2.DAO.KhoanChiDAO;
import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.KhoanChi;
import com.example.duan1nhom2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class KhoanChiFragment extends Fragment {
    ListView lv_khoanchi;
    AdapterKhoanChi adapterKhoanChi;
    DataBase dataBase;
    List<KhoanChi> khoanChis;
    KhoanChiDAO khoanChiDAO;
    FloatingActionButton floatingActionButton;
    TextInputLayout input_nameChi, input_soTien;
    KhoanThuDAO khoanThuDAO;

    public KhoanChiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoan_chi, container, false);
        lv_khoanchi = view.findViewById(R.id.lv_khoanChi);
        floatingActionButton = view.findViewById(R.id.btn_themChi);

        dataBase = new DataBase(getActivity());
        khoanChiDAO = new KhoanChiDAO(dataBase);
        khoanChis = khoanChiDAO.getAllKhoanChi();
        khoanThuDAO = new KhoanThuDAO(dataBase);
        adapterKhoanChi = new AdapterKhoanChi(getActivity(), khoanChis);
        lv_khoanchi.setAdapter(adapterKhoanChi);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(lv_khoanchi, "translationX", -1000f, 0f);
        objectAnimator.setDuration(1300);
        objectAnimator.start();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.add_chi, null);
                builder.setView(view1);

                final EditText edt_namechi = view1.findViewById(R.id.edt_nameChi);
                final EditText edt_sotienchi = view1.findViewById(R.id.edt_stChi);
                final TextView tv_ngaychi = view1.findViewById(R.id.tv_date_chi);
                input_nameChi = view1.findViewById(R.id.input_nameChi);
                input_soTien = view1.findViewById(R.id.input_soTienChi);
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
                                tv_ngaychi.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                // kiểm soát để trống khi nhập
                if (edt_namechi.getText().length() == 0) {
                    input_nameChi.setError("Không được để trống");
                } else {
                    input_nameChi.setError(null);
                }
                edt_namechi.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            input_nameChi.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                if (edt_sotienchi.getText().length() == 0) {
                    input_soTien.setError("Không được để trống");
                } else {
                    input_soTien.setError(null);
                }
                edt_sotienchi.addTextChangedListener(new TextWatcher() {
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
                        if (tv_ngaychi.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Bạn chưa chọn ngày!", Toast.LENGTH_SHORT).show();
                        } else if (edt_namechi.getText().toString().isEmpty() || edt_sotienchi.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        } else {
                            Date date = null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                date = simpleDateFormat.parse(tv_ngaychi.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            KhoanChi khoanChi = new KhoanChi();
                            khoanChi.setNamechi(edt_namechi.getText().toString());
                            khoanChi.setSotienchi(Long.valueOf(edt_sotienchi.getText().toString()));
                            khoanChi.setNgaychi(date);

                            dataBase = new DataBase(getActivity());
                            KhoanChiDAO khoanChiDAO;
                            khoanChiDAO = new KhoanChiDAO(dataBase);
                            long value = khoanChiDAO.insertKhoanChi(khoanChi);
                            if (value > 0) {
                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                            khoanChis = khoanChiDAO.getAllKhoanChi();
                            adapterKhoanChi.setDatachange(khoanChis);
                        }
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }
}
