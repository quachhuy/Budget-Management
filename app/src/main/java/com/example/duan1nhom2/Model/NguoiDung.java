package com.example.duan1nhom2.Model;

public class NguoiDung {
    private String Username;
    private String Password;
    private String Phone;
    private String Email;
    private String Fullname;

    public NguoiDung() {
    }

    public NguoiDung(String username, String password, String phone, String email, String fullname) {
        Username = username;
        Password = password;
        Phone = phone;
        Email = email;
        Fullname = fullname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }
}
