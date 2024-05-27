package com.example.labhd01.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labhd01.App.SanPhamApp;
import com.example.labhd01.R;
import com.example.labhd01.adapter.ProductAdapter;
import com.example.labhd01.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private RecyclerView recyclerProduct;
    private FloatingActionButton floatAdd;
    private SanPhamApp sanPhamApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerProduct = view.findViewById(R.id.recyclerViewProduct);
        floatAdd = view.findViewById(R.id.floatAdd);
        sanPhamApp = new SanPhamApp(getContext());
        loadSanPham();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });
        return view;
    }

    private void loadSanPham() {
        ArrayList<Product> list = sanPhamApp.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProduct.setLayoutManager(linearLayoutManager);

        ProductAdapter adapter = new ProductAdapter(getContext(),list, sanPhamApp);
        recyclerProduct.setAdapter(adapter);
    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSLSP = view.findViewById(R.id.edtSoLuongSP);

        Button btnThemSP = view.findViewById(R.id.btnThemSP);
        Button btnHuySP = view.findViewById(R.id.btnHuySP);

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String slsp = edtSLSP.getText().toString();
                if (tensp.length()==0 || giasp.length()==0 || slsp.length()==0){
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Product product = new Product(tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                    boolean check =sanPhamApp.themSPMoi(product);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadSanPham();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuySP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
