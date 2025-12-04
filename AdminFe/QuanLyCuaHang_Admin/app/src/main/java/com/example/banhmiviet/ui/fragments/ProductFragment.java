package com.example.banhmiviet.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.controller.ProductController;
import com.example.banhmiviet.model.Product;
import com.example.banhmiviet.ui.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddProduct;
    private ProductAdapter adapter;
    private ProductController controller;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        fabAddProduct = view.findViewById(R.id.fabAddProduct);

        controller = new ProductController();
        productList = controller.getProducts();

        adapter = new ProductAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Sự kiện thêm sản phẩm
        fabAddProduct.setOnClickListener(v -> showProductDialog(-1, null));

        // Sự kiện sửa sản phẩm từ adapter
        adapter.setOnItemClickListener((position, product) -> {
            showProductDialog(position, product);
        });

        return view;
    }

    private void showProductDialog(int editPosition, @Nullable Product oldProduct) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_product, null);
        EditText edtName = dialogView.findViewById(R.id.edtProductName);
        EditText edtPrice = dialogView.findViewById(R.id.edtProductPrice);
        EditText edtDesc = dialogView.findViewById(R.id.edtProductDesc);

        if (oldProduct != null) {
            edtName.setText(oldProduct.getName());
            edtPrice.setText(String.valueOf(oldProduct.getPrice()));
            edtDesc.setText(oldProduct.getDescription());
        }

        new AlertDialog.Builder(getContext())
                .setTitle(editPosition == -1 ? "Thêm sản phẩm" : "Sửa sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = edtName.getText().toString().trim();
                    String priceStr = edtPrice.getText().toString().trim();
                    String desc = edtDesc.getText().toString().trim();

                    if (name.isEmpty() || priceStr.isEmpty()) {
                        Toast.makeText(getContext(), "Tên và giá không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double price;
                    try {
                        price = Double.parseDouble(priceStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Product newProduct = new Product(name, price, desc);

                    if (editPosition == -1) {
                        adapter.addProduct(newProduct);
                    } else {
                        adapter.updateProduct(editPosition, newProduct);
                    }

                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
}
