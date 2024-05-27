package com.example.labhd01.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "qlsp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String qNguoiDung = "CREATE TABLE NGUOIDUNG(tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT)";
        db.execSQL(qNguoiDung);

        String qSanPham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT, tensp TEXT, giaban INTEGER,soluong INTEGER, hinhanh TEXT)";
        db.execSQL(qSanPham);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('admin','123','qt'),('user','123','nd')";
        db.execSQL(dNguoiDung);

        String dSanPham = "INSERT INTO SANPHAM VALUES(1,'Bánh',5000,30,''),(2,'Kẹo',10000,42,''), (3,'Bút',15000,10,'')";
        db.execSQL(dSanPham);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(db);
        }
    }

}
