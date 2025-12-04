package com.example.banhmiviet.ui.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.controller.OrderController;
import com.example.banhmiviet.controller.ProductController;
import com.example.banhmiviet.model.Order;
import com.example.banhmiviet.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SelectProductDialogFragment extends DialogFragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private TextView txtTotalPrice;
    private Button btnConfirm;

    private ProductController productController;
    private OrderController orderController;
    private ProductAdapter adapter;
    private List<Product> fullProductList;
    private HashMap<Product, Integer> selectedProducts = new HashMap<>();
    private OnOrderConfirmedListener listener;

    public interface OnOrderConfirmedListener {
        void onOrderConfirmed(Order order);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnOrderConfirmedListener) {
            listener = (OnOrderConfirmedListener) getParentFragment();
        } else {
            throw new RuntimeException("Parent fragment must implement OnOrderConfirmedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_products, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerViewSelectProducts);
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
        btnConfirm = view.findViewById(R.id.btnConfirmOrder);

        productController = new ProductController();
        orderController = new OrderController();
        fullProductList = productController.getProducts();

        adapter = new ProductAdapter(fullProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProductList(newText);
                return true;
            }
        });

        btnConfirm.setOnClickListener(v -> {
            if (selectedProducts.isEmpty()) {
                dismiss();
                return;
            }

            // Sinh mã đơn hàng mới (tự động tăng)
            String newOrderId = String.format("%03d", orderController.getOrders().size() + 1);

            // Tạo chuỗi mô tả sản phẩm + tổng giá
            StringBuilder description = new StringBuilder();
            double totalPrice = 0;

            for (Product product : selectedProducts.keySet()) {
                int quantity = selectedProducts.get(product);
                double itemTotal = product.getPrice() * quantity;
                totalPrice += itemTotal;
                description.append(String.format(Locale.getDefault(),
                        "%dx %s, ", quantity, product.getName()));
            }

            if (description.length() > 2) {
                description.setLength(description.length() - 2); // Xoá dấu phẩy cuối
            }

            // 👉 Nếu class Order chỉ nhận 2 tham số (id, description), thì truyền đúng:
            Order newOrder = new Order(newOrderId, description.toString());

            if (listener != null) {
                listener.onOrderConfirmed(newOrder);
            }

            dismiss();
        });

        return view;
    }

    private void filterProductList(String query) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : fullProductList) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(p);
            }
        }
        adapter.updateList(filtered);
    }

    private void updateTotalPriceDisplay() {
        double total = 0;
        for (Product product : selectedProducts.keySet()) {
            int quantity = selectedProducts.get(product);
            total += product.getPrice() * quantity;
        }
        txtTotalPrice.setText("Tổng: " + total + " đ");
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private List<Product> displayList;

        public ProductAdapter(List<Product> displayList) {
            this.displayList = new ArrayList<>(displayList);
        }

        public void updateList(List<Product> newList) {
            this.displayList = newList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_select_product, parent, false);
            return new ProductViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = displayList.get(position);
            holder.txtName.setText(product.getName());
            holder.txtPrice.setText(product.getPrice() + " đ");

            Integer quantity = selectedProducts.getOrDefault(product, 0);
            holder.edtQuantity.setText(String.valueOf(quantity > 0 ? quantity : ""));

            holder.edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    int qty = 0;
                    try {
                        qty = Integer.parseInt(s.toString());
                    } catch (NumberFormatException ignored) {}

                    if (qty > 0) {
                        selectedProducts.put(product, qty);
                    } else {
                        selectedProducts.remove(product);
                    }

                    updateTotalPriceDisplay();
                }
            });
        }

        @Override
        public int getItemCount() {
            return displayList.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView txtName, txtPrice;
            EditText edtQuantity;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                txtName = itemView.findViewById(R.id.txtProductName);
                txtPrice = itemView.findViewById(R.id.txtProductPrice);
                edtQuantity = itemView.findViewById(R.id.edtQuantity);
            }
        }
    }
}
