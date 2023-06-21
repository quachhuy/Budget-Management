package com.example.duan1nhom2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1nhom2.Adapter.AdapterNguoiDung;
import com.example.duan1nhom2.DAO.NguoiDungDAO;
import com.example.duan1nhom2.DataBase.DataBase;
import com.example.duan1nhom2.Model.NguoiDung;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    DataBase dataBase;

    EditText edt_user, edt_pass;
    CheckBox remember;
    AdapterNguoiDung adapterNguoiDung;
    List<NguoiDung> nguoiDungs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_user = findViewById(R.id.edt_user);
        edt_pass = findViewById(R.id.edt_pass);
        remember = findViewById(R.id.rememberLogin);
        loadLogin();
    }

    public void rememberLogin(String user, String pass, boolean check) {
        SharedPreferences sharedPreferences = getSharedPreferences("userFile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!check) {
            edit.clear();
        } else {
            edit.putString("username", user);
            edit.putString("pass", pass);
            edit.putBoolean("remember", check);
        }
        edit.commit();
    }

    public void login(View view) {
        String user = edt_user.getText().toString();
        String pass = edt_pass.getText().toString();
        boolean remem = remember.isChecked();
        DataBase dataBase = new DataBase(LoginActivity.this);
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(dataBase);
        List<NguoiDung> nguoiDungs = nguoiDungDAO.getAllND();
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            if (user.equalsIgnoreCase(nguoiDungs.get(0).getUsername()) && pass.equalsIgnoreCase(nguoiDungs.get(0).getPassword())) {
                rememberLogin(user, pass, remem);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                if (user.equals(nguoiDungs.get(0).getUsername()) && pass.equals(nguoiDungs.get(0).getPassword())) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Tài Khoản Mật Khẩu Không Đúng", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void signUp(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View view1 = LayoutInflater.from(this).inflate(R.layout.add_taikhoan, null);
        builder.setView(view1);
        final EditText user = view1.findViewById(R.id.sign_user);
        final EditText pass = view1.findViewById(R.id.sign_pass);
        final EditText fullName = view1.findViewById(R.id.sign_fullName);
        final EditText phone = view1.findViewById(R.id.sign_phone);
        final EditText email = view1.findViewById(R.id.sign_email);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!user.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !fullName.getText().toString().isEmpty()
                        && !phone.getText().toString().isEmpty() && !email.getText().toString().isEmpty()){
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setUsername(user.getText().toString());
                    nguoiDung.setPassword(pass.getText().toString());
                    nguoiDung.setPhone(phone.getText().toString());
                    nguoiDung.setFullname(fullName.getText().toString());
                    nguoiDung.setEmail(email.getText().toString());
                    NguoiDungDAO nguoiDungDAO;
                    dataBase = new DataBase(LoginActivity.this);
                    nguoiDungDAO = new NguoiDungDAO(dataBase);
                    long value = nguoiDungDAO.insertNguoiDung(nguoiDung);
                    Log.e("thien",value + "");
                    if (value > 0) {
                        Toast.makeText(LoginActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Không Được Để Trống", Toast.LENGTH_SHORT).show();
                }
            }
        }).setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    public void loadLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("userFile", MODE_PRIVATE);
        if (sharedPreferences != null) {
            String user = sharedPreferences.getString("username", null);
            String pass = sharedPreferences.getString("pass", null);
            boolean remem = sharedPreferences.getBoolean("remember", false);
            edt_user.setText(user);
            edt_pass.setText(pass);
            remember.setChecked(remem);
        }
    }
}