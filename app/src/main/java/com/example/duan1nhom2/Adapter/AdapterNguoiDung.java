package com.example.duan1nhom2.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DAO.NguoiDungDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.KhoanThu;
import com.example.duan1nhom2.Model.NguoiDung;
import com.example.duan1nhom2.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AdapterNguoiDung extends BaseAdapter {
    Activity context;
    List<NguoiDung> nguoiDungList;
    DataBase dataBase;
    NguoiDungDAO nguoiDungDAO;


    public AdapterNguoiDung(Activity context, List<NguoiDung> nguoiDungList) {
        this.context = context;
        this.nguoiDungList = nguoiDungList;
    }

    @Override
    public int getCount() {
        return nguoiDungList.size();
    }

    @Override
    public Object getItem(int position) {
        return nguoiDungList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_taikhoan, parent, false);
        final NguoiDung nguoiDung = (NguoiDung) getItem(position);
        final TextView update_username = convertView.findViewById(R.id.userName);
        final TextView update_fullname = convertView.findViewById(R.id.fullName);
        final TextView update_phone = convertView.findViewById(R.id.phoneNumber);
        final TextView update_email = convertView.findViewById(R.id.email);
        ImageView img_edtND = convertView.findViewById(R.id.edit_nguoidung);

        update_username.setText(nguoiDung.getUsername());
        update_phone.setText(nguoiDung.getPhone());
        update_fullname.setText(nguoiDung.getFullname());
        update_email.setText(nguoiDung.getEmail());

        img_edtND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View view1 = LayoutInflater.from(context).inflate(R.layout.sua_tai_khoan, null);
                builder.setView(view1);
                final TextInputLayout layout_pass, layout_phone, layout_fullname, layout_email;
                final EditText edt_sua_pass, edt_sua_fullName, edt_sua_phone, edt_sua_email;
                layout_pass = view1.findViewById(R.id.layout_pass);
                layout_fullname = view1.findViewById(R.id.layou_fullname);
                layout_phone = view1.findViewById(R.id.layout_phone);
                layout_email = view1.findViewById(R.id.layout_email);
                edt_sua_pass = view1.findViewById(R.id.edt_sua_pass);
                edt_sua_fullName = view1.findViewById(R.id.edt_sua_fullName);
                edt_sua_phone = view1.findViewById(R.id.edt_sua_phone);
                edt_sua_email = view1.findViewById(R.id.edt_sua_email);
                // kiểm soát nhập
                if (edt_sua_pass.getText().length() == 0) {
                    layout_pass.setError("Không được để trống");
                } else {
                    layout_pass.setError(null);
                }
                edt_sua_pass.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            layout_pass.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if (edt_sua_fullName.getText().length() == 0) {
                    layout_fullname.setError("Không được để trống");
                } else {
                    layout_fullname.setError(null);
                }
                edt_sua_fullName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            layout_fullname.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if (edt_sua_phone.getText().length() == 0) {
                    layout_phone.setError("Không được để trống");
                } else {
                    layout_phone.setError(null);
                }
                edt_sua_phone.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            layout_phone.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if (edt_sua_email.getText().length() == 0) {
                    layout_email.setError("Không được để trống");
                } else {
                    layout_email.setError(null);
                }
                edt_sua_email.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            layout_email.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = edt_sua_pass.getText().toString(),
                                fullname = edt_sua_fullName.getText().toString(),
                                phone = edt_sua_phone.getText().toString(),
                                email = edt_sua_email.getText().toString();
                        NguoiDung nd = new NguoiDung(nguoiDung.getUsername(), pass, phone, email, fullname);
                        dataBase = new DataBase(context);
                        nguoiDungDAO = new NguoiDungDAO(dataBase);
                        nguoiDungDAO.updateNguoiDung(nd);
                        setDatachange(nguoiDungDAO.getAllND());
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
        return convertView;

    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setDatachange(List<NguoiDung> items) {
        this.nguoiDungList = items;
        notifyDataSetChanged();
    }
}