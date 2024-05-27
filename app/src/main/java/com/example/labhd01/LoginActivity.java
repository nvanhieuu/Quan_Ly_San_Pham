package com.example.labhd01;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labhd01.App.NguoiDungApp;
import com.example.labhd01.util.SendMail;

public class LoginActivity extends AppCompatActivity {
    private SendMail sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtForgot = findViewById(R.id.txtForgot);
        TextView txtSignUp = findViewById(R.id.txtSignUp);

        NguoiDungApp nguoiDungApp = new NguoiDungApp(this);
        sendMail = new SendMail();

        btnLogin.setOnClickListener(v -> {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                boolean check = nguoiDungApp.CheckLogin(user, pass);

                if (check) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui  lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
        });

        txtSignUp.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        txtForgot.setOnClickListener(v -> showDialogForgot());

        edtPass.setOnEditorActionListener((v, actionId, event) -> {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String user = edtUser.getText().toString();
                    String pass = edtPass.getText().toString();

                    boolean check = nguoiDungApp.CheckLogin(user, pass);

                    if (check) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
        });

    }

    private void showDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forget, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText edtEmail = view.findViewById(R.id.edtEmail);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        NguoiDungApp nguoiDungApp = new NguoiDungApp(this);

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        btnSend.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String matkhau = nguoiDungApp.ForgotPassword(email);

            if (matkhau.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Không Tìm Thấy tài Khoản", Toast.LENGTH_SHORT).show();
            } else {
                sendMail.Send(LoginActivity.this, email, "Lấy lại mật khẩu", "Mật khẩu của bạn là :" + matkhau);
                alertDialog.dismiss();
            }
        });

    }

}