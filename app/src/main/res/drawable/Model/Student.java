package com.example.sqlite_with_intent.Model;

public class Student {

    private int idSV;
    private int idClass;
    private int maSV;
    private String hoTen;
    private String namSinh;
    private byte[] avatar;

    public Student() {
    }

    public Student(int idClass, int maSV, String hoTen, String namSinh, byte[] avatar) {
        this.idClass = idClass;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.avatar = avatar;
    }

    public Student(int idSV, int idClass, int maSV, String hoTen, String namSinh, byte[] avatar) {
        this.idSV = idSV;
        this.idClass = idClass;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.avatar = avatar;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public int getIdSV() {
        return idSV;
    }

    public void setIdSV(int idSV) {
        this.idSV = idSV;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

}
