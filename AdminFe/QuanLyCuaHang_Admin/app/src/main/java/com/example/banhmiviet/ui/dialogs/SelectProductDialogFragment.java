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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
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

    private DataRepository repo;
    private ProductAdapter adapter;
    private List<Product> fullProductList;
    private final HashMap<Product, Integer> selectedProducts = new HashMap<>();

    private OnOrderConfirmedListener listener;

    // Giao tiếp ngược về Fragment cha (OrderFragment)
    public interface OnOrderConfirmedListener {
        void onOrderConfirmed(Order order);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Listener lấy từ parent fragment (OrderFragment)
        if (getParentFragment() instanceof OnOrderConfirmedListener) {
            listener = (OnOrderConfirmedListener) getParentFragment();
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

        // Dùng kho dữ liệu chung
        repo = DataRepository.getInstance();
        fullProductList = repo.getProducts();

        adapter = new ProductAdapter(fullProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Tìm kiếm theo tên sản phẩm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProductList(newText);
                return true;
            }
        });

        // Nút xác nhận tạo đơn hàng
        btnConfirm.setOnClickListener(v -> {
            if (selectedProducts.isEmpty()) {
                dismiss();
                return;
            }

            // Sinh mã đơn hàng mới (tự động tăng, dùng list trong DataRepository)
            String newOrderId = String.format(Locale.getDefault(),
                    "%03d", repo.getOrders().size() + 1);

            // Tạo chuỗi mô tả + tính tổng tiền
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
                description.setLength(description.length() - 2); // xoá ", " cuối
            }

            // Dùng constructor 3 tham số: id + mô tả + tổng tiền
            Order newOrder = new Order(newOrderId, description.toString(), totalPrice);

            // Gửi về OrderFragment
            if (listener != null) {
                listener.onOrderConfirmed(newOrder);
            }

            dismiss();
        });

        return view;
    }

    // Lọc danh sách sản phẩm theo tên
    private void filterProductList(String query) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : fullProductList) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(p);
            }
        }
        adapter.updateList(filtered);
    }

    // Tính lại tổng tiền mỗi khi số lượng thay đổi
    private void updateTotalPriceDisplay() {
        double total = 0;
        for (Product product : selectedProducts.keySet()) {
            int quantity = selectedProducts.get(product);
            total += product.getPrice() * quantity;
        }
        txtTotalPrice.setText("Tổng: " + total + " đ");
    }

    // Adapter hiển thị danh sách sản phẩm trong dialog
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
            holder.txtPrice.setText(String.format(Locale.getDefault(),
                    "%,.0f đ", product.getPrice()));

            // Nếu sản phẩm đã chọn trước đó -> hiển thị lại số lượng
            Integer currentQty = selectedProducts.get(product);
            if (currentQty == null) currentQty = 0;
            if (currentQty == 0) {
                holder.edtQuantity.setText("");
            } else {
                holder.edtQuantity.setText(String.valueOf(currentQty));
            }

            // Lắng nghe thay đổi số lượng
            holder.edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    int qty = 0;
                    try {
                        String text = s.toString().trim();
                        if (!text.isEmpty()) qty = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        qty = 0;
                    }

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
