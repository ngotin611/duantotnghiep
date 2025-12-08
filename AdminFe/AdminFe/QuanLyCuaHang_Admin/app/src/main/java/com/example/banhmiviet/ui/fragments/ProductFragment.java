package com.example.banhmiviet.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.Product;
import com.example.banhmiviet.ui.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddProduct;
    private ProductAdapter adapter;
    private DataRepository repo;
    private List<Product> productList = new ArrayList<>();

    // chọn ảnh
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ImageView currentPreviewImageView;  // ImageView trong dialog
    private String currentImageUri;             // Uri đang chọn trong dialog

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            currentImageUri = uri.toString();
                            if (currentPreviewImageView != null) {
                                currentPreviewImageView.setImageURI(uri);
                            }
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        fabAddProduct = view.findViewById(R.id.fabAddProduct);

        repo = DataRepository.getInstance();
        productList.clear();
        productList.addAll(repo.getProducts());

        adapter = new ProductAdapter(
                getContext(),
                productList,
                position -> { // delete
                    if (position >= 0 && position < productList.size()) {
                        productList.remove(position);
                        adapter.notifyItemRemoved(position);
                        // nếu muốn xoá trong repo:
                        // repo.removeProduct(position); (tự thêm hàm này trong DataRepository)
                    }
                },
                (position, product) -> showProductDialog(position, product)
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fabAddProduct.setOnClickListener(v -> showProductDialog(-1, null));

        return view;
    }

    private void showProductDialog(int editPosition, @Nullable Product oldProduct) {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_edit_product, null);

        ImageView imgPreview   = dialogView.findViewById(R.id.imgPreviewProduct);
        TextView btnChooseImg  = dialogView.findViewById(R.id.btnChooseImage);
        EditText edtName       = dialogView.findViewById(R.id.edtProductName);
        EditText edtCategory   = dialogView.findViewById(R.id.edtProductCategory);
        EditText edtPrice      = dialogView.findViewById(R.id.edtProductPrice);
        EditText edtDesc       = dialogView.findViewById(R.id.edtProductDesc);

        currentPreviewImageView = imgPreview;
        currentImageUri = null;

        if (oldProduct != null) {
            edtName.setText(oldProduct.getName());
            edtCategory.setText(oldProduct.getCategory());
            edtPrice.setText(String.format(Locale.getDefault(), "%.0f", oldProduct.getPrice()));
            edtDesc.setText(oldProduct.getDescription());

            if (oldProduct.getImageUri() != null && !oldProduct.getImageUri().isEmpty()) {
                currentImageUri = oldProduct.getImageUri();
                imgPreview.setImageURI(Uri.parse(currentImageUri));
            } else if (oldProduct.getImageResId() != 0) {
                imgPreview.setImageResource(oldProduct.getImageResId());
            }
        }

        btnChooseImg.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(pickIntent);
        });

        new AlertDialog.Builder(getContext())
                .setTitle(editPosition == -1 ? "Thêm sản phẩm" : "Sửa sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name     = edtName.getText().toString().trim();
                    String priceStr = edtPrice.getText().toString().trim();
                    String desc     = edtDesc.getText().toString().trim();
                    String category = edtCategory.getText().toString().trim();

                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                        Toast.makeText(getContext(),
                                "Tên và giá không được để trống",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double price;
                    try {
                        price = Double.parseDouble(priceStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // tạo / cập nhật Product
                    String id;
                    if (oldProduct == null || oldProduct.getId() == null) {
                        id = "P" + String.format("%03d", productList.size() + 1);
                    } else {
                        id = oldProduct.getId();
                    }

                    Product newProduct = new Product(
                            id,
                            name,
                            price,
                            desc,
                            category,
                            0,
                            currentImageUri
                    );

                    if (editPosition == -1) {
                        productList.add(newProduct);
                        adapter.notifyItemInserted(productList.size() - 1);
                        repo.addProduct(newProduct);
                    } else {
                        productList.set(editPosition, newProduct);
                        adapter.notifyItemChanged(editPosition);
                        // nếu muốn cập nhật repo, thêm hàm updateProduct trong DataRepository
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
    