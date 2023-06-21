package com.example.duan1nhom2.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1nhom2.DAO.KhoanChiDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.KhoanChi;
import com.example.duan1nhom2.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterKhoanChi extends BaseAdapter {
    Activity context;
    List<KhoanChi> khoanChiList;
    DataBase dataBase;
    KhoanChiDAO khoanChiDAO;
    TextInputLayout fix_nameChi, fix_soTienChi;

    public AdapterKhoanChi(Activity context, List<KhoanChi> khoanChiList) {
        this.context = context;
        this.khoanChiList = khoanChiList;
    }

    @Override
    public int getCount() {
        return khoanChiList.size();
    }

    @Override
    public KhoanChi getItem(int position) {
        return khoanChiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chi, parent, false);
        final KhoanChi khoanChi = (KhoanChi) getItem(position);

        TextView tv_ngaychi = convertView.findViewById(R.id.ngayChi);
        TextView tv_name = convertView.findViewById(R.id.nameChi);
        TextView tv_sotien = convertView.findViewById(R.id.sotienChi);
        ImageView img_delete = convertView.findViewById(R.id.delete_Chi);
        ImageView img_update = convertView.findViewById(R.id.update_Chi);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(khoanChi.getNgaychi());

        tv_ngaychi.setText(s);
        tv_name.setText(khoanChi.getNamechi());
        tv_sotien.setText("Số Tiền: " + khoanChi.getSotienchi() + " VND");
        img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View view = LayoutInflater.from(context).inflate(R.layout.sua_chi, null);
                builder.setView(view);
                final TextView update_ngaychi = view.findViewById(R.id.update_ngaychi);
                final EditText update_sotien_chi = view.findViewById(R.id.update_sotienchi);
                final EditText update_namechi = view.findViewById(R.id.update_namechi);
                fix_nameChi = view.findViewById(R.id.fix_nameChi);
                fix_soTienChi = view.findViewById(R.id.fix_soTienChi);
                Button datepicker_ngaychi = view.findViewById(R.id.datepicker_chi);
                datepicker_ngaychi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        final int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                update_ngaychi.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                // kiểm soát nhập vào
                if (update_namechi.getText().length() == 0) {
                    fix_nameChi.setError("Không được để trống");
                } else {
                    fix_nameChi.setError(null);
                }
                update_namechi.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            fix_nameChi.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                if (update_sotien_chi.getText().length() == 0) {
                    fix_soTienChi.setError("Không được để trống");
                } else {
                    fix_soTienChi.setError(null);
                }
                update_sotien_chi.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            fix_soTienChi.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (update_ngaychi.getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(), "Bạn chưa chọn ngày!", Toast.LENGTH_SHORT).show();
                        } else if (update_namechi.getText().toString().isEmpty() || update_ngaychi.getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        } else {
                            Date date = null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                date = simpleDateFormat.parse(update_ngaychi.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            khoanChi.setNamechi(update_namechi.getText().toString());
                            khoanChi.setSotienchi(Long.valueOf(update_sotien_chi.getText().toString()));
                            khoanChi.setNgaychi(date);

                            dataBase = new DataBase(parent.getContext());
                            khoanChiDAO = new KhoanChiDAO(dataBase);
                            khoanChiDAO.updateKhoanChi(khoanChi);
                            setDatachange(khoanChiDAO.getAllKhoanChi());
                        }
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn Có Muốn Xóa Không")
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBase = new DataBase(parent.getContext());
                                khoanChiDAO = new KhoanChiDAO(dataBase);
                                khoanChiDAO.deleteKhoanChi(khoanChi.getIdchi());
                                setDatachange(khoanChiDAO.getAllKhoanChi());
                            }
                        })
                        .setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
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

    public void setDatachange(List<KhoanChi> items) {
        this.khoanChiList = items;
        notifyDataSetChanged();
    }
}
