package com.example.duan1nhom2.Model;

import java.util.Date;

public class KhoanChi {
    private int idchi;
    private String namechi;
    private long sotienchi;
    private Date ngaychi;

    public KhoanChi() {
    }


    public int getIdchi() {
        return idchi;
    }

    public void setIdchi(int idchi) {
        this.idchi = idchi;
    }

    public String getNamechi() {
        return namechi;
    }

    public void setNamechi(String namechi) {
        this.namechi = namechi;
    }

    public long getSotienchi() {
        return sotienchi;
    }

    public void setSotienchi(long sotienchi) {
        this.sotienchi = sotienchi;
    }

    public Date getNgaychi() {
        return ngaychi;
    }

    public void setNgaychi(Date ngaychi) {
        this.ngaychi = ngaychi;
    }
}
