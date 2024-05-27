package com.example.labhd01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labhd01.App.NguoiDungApp;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        EditText edtRePass = findViewById(R.id.edtRePass);
        EditText edtFullname = findViewById(R.id.edtFullName);
        Button btnRegister=findViewById(R.id.btnRegister);
        Button btnGoBack=findViewById(R.id.btnGoBack);

        NguoiDungApp nguoiDungApp = new NguoiDungApp(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String rePass = edtRePass.getText().toString();
                String fullName = edtFullname.getText().toString();

                if(!pass.equals(rePass)) {
                    Toast.makeText(RegisterActivity.this, "Nhập 2 mật khẩu trùng nhau. Kiểm tra lại.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = nguoiDungApp.Register(user,pass,fullName);
                    if (check) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}