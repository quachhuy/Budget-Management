package com.example.duan1nhom2.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.duan1nhom2.DAO.KhoanThuDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.KhoanThu;
import com.example.duan1nhom2.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterKhoanThu extends BaseAdapter {
    Context context;
    List<KhoanThu> khoanThuList;
    DataBase dataBase;
    KhoanThuDAO khoanThuDAO;
    TextInputLayout fix_nameThu, fix_soTienThu;

    public AdapterKhoanThu(Context context, List<KhoanThu> khoanThuList) {
        this.context = context;
        this.khoanThuList = khoanThuList;
    }

    @Override
    public int getCount() {
        return khoanThuList.size();
    }

    @Override
    public KhoanThu getItem(int position) {
        return khoanThuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_thu, parent, false);
        final KhoanThu khoanThu = (KhoanThu) getItem(position);
        final TextView tv_ngaythu = convertView.findViewById(R.id.ngayThu);
        TextView tv_name = convertView.findViewById(R.id.nameThu);
        TextView tv_sotien = convertView.findViewById(R.id.sotienThu);
        ImageView img_delete = convertView.findViewById(R.id.delete_Thu);
        ImageView img_update = convertView.findViewById(R.id.update_Thu);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(khoanThu.getNgaythu());

        tv_ngaythu.setText(s);
        tv_name.setText(khoanThu.getNamethu());
        tv_sotien.setText("Số Tiền: " + khoanThu.getSotien() + " VND");
        img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View view = LayoutInflater.from(context).inflate(R.layout.sua_thu, null);
                builder.setView(view);
                final TextView update_ngaythu = view.findViewById(R.id.update_ngaythu);
                final EditText update_sotien_thu = view.findViewById(R.id.update_sotienthu);
                final EditText update_name_thu = view.findViewById(R.id.update_namethu);
                fix_nameThu = view.findViewById(R.id.fix_nameThu);
                fix_soTienThu = view.findViewById(R.id.fix_soTienThu);
                Button datepicker_ngaythu = view.findViewById(R.id.datepicker_thu);
                datepicker_ngaythu.setOnClickListener(new View.OnClickListener() {
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
                                update_ngaythu.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                // kiểm soát nhập
                if (update_name_thu.getText().length() == 0) {
                    fix_nameThu.setError("Không được để trống");
                } else {
                    fix_nameThu.setError(null);
                }
                update_name_thu.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            fix_nameThu.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                if (update_sotien_thu.getText().length() == 0) {
                    fix_soTienThu.setError("Không được để trống");
                } else {
                    fix_soTienThu.setError(null);
                }
                update_sotien_thu.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            fix_soTienThu.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (update_ngaythu.getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(), "Bạn chưa chọn ngày!", Toast.LENGTH_SHORT).show();
                        } else if (update_name_thu.getText().toString().isEmpty() || update_sotien_thu.getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        } else {
                            Date date = null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                date = simpleDateFormat.parse(update_ngaythu.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            khoanThu.setNamethu(update_name_thu.getText().toString());
                            khoanThu.setSotien(Long.valueOf(update_sotien_thu.getText().toString()));
                            khoanThu.setNgaythu(date);

                            dataBase = new DataBase(parent.getContext());
                            khoanThuDAO = new KhoanThuDAO(dataBase);
                            khoanThuDAO.updateKhoanThu(khoanThu);
                            setDatachange(khoanThuDAO.getAllKhoanThu());
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
                                khoanThuDAO = new KhoanThuDAO(dataBase);
                                int id = khoanThu.getIdthu();
                                khoanThuDAO.deleteKhoanThu(id);
                                Log.e("id", "" + id);
                                setDatachange(khoanThuDAO.getAllKhoanThu());
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

    public void setDatachange(List<KhoanThu> items) {
        this.khoanThuList = items;
        notifyDataSetChanged();
    }
}
