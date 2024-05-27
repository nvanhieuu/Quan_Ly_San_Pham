package com.example.labhd01.App;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.labhd01.database.DbHelper;

public class NguoiDungApp {

    private DbHelper dbHelper;
    public NguoiDungApp(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean CheckLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?", new String[]{username, password});
        return cursor.getCount()>0;
    }

    public boolean Register(String usename, String password, String hoten) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tendangnhap", usename);
        contentValues.put("matkhau", password);
        contentValues.put("hoten", hoten);

        long check =sqLiteDatabase.insert("NGUOIDUNG",null, contentValues);
        return check != -1;
    }

    public String ForgotPassword(String email) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT matkhau FROM NGUOIDUNG WHERE tendangnhap=?", new String[]{email});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        } else {
            return "";
        }
    }

}
