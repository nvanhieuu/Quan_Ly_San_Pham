package com.example.labhd01.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labhd01.App.SanPhamApp;
import com.example.labhd01.R;
import com.example.labhd01.model.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final Context content;
    private ArrayList<Product> list;
    private final SanPhamApp sanPhamApp;

    public ProductAdapter(Context content, ArrayList<Product> list, SanPhamApp sanPhamApp) {
        this.content = content;
        this.list = list;
        this.sanPhamApp = sanPhamApp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)content).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).getTensp());

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = list.get(position).getGiaban();
        String formattedNumber = formatter.format(myNumber);

        holder.txtPrice.setText(formattedNumber + content.getString(R.string.price_format));
        holder.txtQuantity.setText(content.getString(R.string.sl, list.get(position).getSoluong()));

        holder.txtEdit.setOnClickListener(v -> showDialogUpdate(list.get(holder.getAdapterPosition())));
        holder.txtDel.setOnClickListener(v -> showDialogDelete(list.get(holder.getAdapterPosition()).getTensp(), list.get(holder.getAdapterPosition()).getMasp()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQuantity, txtEdit, txtDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtDel = itemView.findViewById(R.id.txtDel);
        }
    }

    private void showDialogDelete(String tensp, int masp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(content);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.ic_warning);
        builder.setMessage("bạn Muốn Xóa Sản Phẩm \"" + tensp +"\" Không? ");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            boolean check = sanPhamApp.XoaSP(masp);
            if (check) {
                Toast.makeText(content, "Xóa Thành Công",Toast.LENGTH_SHORT).show();
                loadData();
            }else {
                Toast.makeText(content, "Xóa Thất Bại",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        list.clear();
        list=sanPhamApp.getDS();
        notifyDataSetChanged();
    }

    private void showDialogUpdate(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(content);
        LayoutInflater inflater = ((Activity)content).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSLSP = view.findViewById(R.id.edtSoLuongSP);

        Button btnSuaSP = view.findViewById(R.id.btnSuaSP);
        Button btnHuy = view.findViewById(R.id.btnHuySP);

        edtTenSP.setText(product.getTensp());
        edtGiaSP.setText(String.valueOf(product.getGiaban()));
        edtSLSP.setText(String.valueOf(product.getSoluong()));
        edtTenSP.setText(product.getTensp());
        edtTenSP.setText(product.getTensp());

        btnHuy.setOnClickListener(v -> alertDialog.dismiss());

        btnSuaSP.setOnClickListener(v ->  {
                int masp = product.getMasp();
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String slsp = edtSLSP.getText().toString();

            if (tensp.isEmpty() || giasp.isEmpty() || slsp.isEmpty()) {
                Toast.makeText(content, R.string.nhap_day_du_thong_tin, Toast.LENGTH_SHORT).show();
            } else {
                Product productChinhSua = new Product(masp, tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                boolean check = sanPhamApp.chinhSuaSP(productChinhSua);
                if (check) {
                    Toast.makeText(content, R.string.chinh_sua_thanh_cong, Toast.LENGTH_SHORT).show();
                    int index = list.indexOf(product);
                    if (index != -1) {
                        list.set(index, productChinhSua);
                        notifyItemChanged(index);
                    }
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(content, R.string.chinh_sua_that_bai, Toast.LENGTH_SHORT).show();
                }
            }        });

    }
}
