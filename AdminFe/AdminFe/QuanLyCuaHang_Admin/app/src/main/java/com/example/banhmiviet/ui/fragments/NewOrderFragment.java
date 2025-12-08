package com.example.banhmiviet.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.Order;
import com.example.banhmiviet.model.Product;
import com.example.banhmiviet.ui.adapters.NewOrderProductAdapter;
import com.example.banhmiviet.ui.adapters.CartItemAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewOrderFragment extends Fragment implements
        NewOrderProductAdapter.OnProductClickListener,
            CartItemAdapter.OnCartChangeListener {

    public static final String ARG_ORDER_TYPE      = "order_type";       // Order.TYPE_AT_TABLE / TYPE_TAKE_AWAY
    public static final String ARG_TABLE_NUMBER    = "table_number";     // Integer (bàn) hoặc -1 nếu không có
    public static final String ARG_TAKE_AWAY_CODE  = "take_away_code";   // String, có thể null

    private TextView tvTitle, tvCartInfo, tvTotalMoney;
    private EditText edtSearch;
    private ImageButton btnBack, btnOpenCart;
    private RecyclerView rvProducts;

    private DataRepository repo;
    private final List<Product> fullProducts = new ArrayList<>();
    private NewOrderProductAdapter productAdapter;

    // giỏ hàng: Product -> quantity
    private final HashMap<Product, Integer> cart = new HashMap<>();

    private String  orderType;
    private Integer tableNumber;
    private String  takeAwayCode;

    private final NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    public static NewOrderFragment newInstance(String orderType,
                                               @Nullable Integer tableNumber,
                                               @Nullable String takeAwayCode) {
        NewOrderFragment f = new NewOrderFragment();
        Bundle b = new Bundle();
        b.putString(ARG_ORDER_TYPE, orderType);
        if (tableNumber != null) b.putInt(ARG_TABLE_NUMBER, tableNumber);
        if (takeAwayCode != null) b.putString(ARG_TAKE_AWAY_CODE, takeAwayCode);
        f.setArguments(b);
        return f;
    }

    // ================== LIFECYCLE ==================

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_order, container, false);

        // Ánh xạ view
        tvTitle      = view.findViewById(R.id.tvTitle);          // đảm bảo trong XML có id này
        tvCartInfo   = view.findViewById(R.id.tvCartInfo);
        tvTotalMoney = view.findViewById(R.id.tvTotalMoney);     // đảm bảo trong XML có id này
        edtSearch    = view.findViewById(R.id.edtSearchProduct);
        btnBack      = view.findViewById(R.id.btnBack);
        btnOpenCart  = view.findViewById(R.id.btnOpenCart);
        rvProducts   = view.findViewById(R.id.rvProducts);

        // Lấy repo + dữ liệu sản phẩm
        repo = DataRepository.getInstance();
        fullProducts.clear();
        fullProducts.addAll(repo.getProducts());

        // Lấy tham số truyền vào
        Bundle args = getArguments();
        if (args != null) {
            orderType    = args.getString(ARG_ORDER_TYPE, Order.TYPE_TAKE_AWAY);
            if (args.containsKey(ARG_TABLE_NUMBER)) {
                tableNumber = args.getInt(ARG_TABLE_NUMBER);
            }
            takeAwayCode = args.getString(ARG_TAKE_AWAY_CODE);
        } else {
            orderType = Order.TYPE_TAKE_AWAY;
            tableNumber = null;
            takeAwayCode = null;
        }

        setupTitle();
        setupProductList();
        setupSearch();
        setupButtons();
        updateCartSummary();

        return view;
    }

    // ================== TITLE ==================

    private void setupTitle() {
        if (tvTitle == null) return;

        String title;
        if (Order.TYPE_AT_TABLE.equals(orderType)) {
            if (tableNumber == null || tableNumber <= 0) {
                title = "Tạo đơn - Tại bàn";
            } else {
                title = "Tạo đơn - Bàn " + tableNumber;
            }
        } else {
            // Mang đi
            if (!TextUtils.isEmpty(takeAwayCode)) {
                title = "Tạo đơn - Mang đi (" + takeAwayCode + ")";
            } else {
                title = "Tạo đơn - Mang đi";
            }
        }
        tvTitle.setText(title);
    }

    // ================== PRODUCT LIST ==================

    // ================== PRODUCT LIST ==================

    private void setupProductList() {
        // Adapter nhận danh sách sản phẩm + listener click
        productAdapter = new NewOrderProductAdapter(fullProducts, this);

        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvProducts.setAdapter(productAdapter);
    }


    // ================== SEARCH / FILTER ==================

    private void setupSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String q = s.toString().trim();
                filterProducts(q);
            }
        });
    }

    private void filterProducts(String query) {
        if (productAdapter == null) return;

        if (query.isEmpty()) {
            productAdapter.updateList(fullProducts);
            return;
        }

        String qNorm = normalize(query);

        List<Product> filtered = new ArrayList<>();
        for (Product p : fullProducts) {
            String nameNorm = normalize(p.getName());

            // 1. Tìm gần giống: chứa chuỗi
            boolean match = nameNorm.contains(qNorm);

            // 2. Tên tắt: lấy chữ cái đầu mỗi từ -> "bmn"
            if (!match) {
                String initials = buildInitials(nameNorm);
                if (initials.contains(qNorm)) {
                    match = true;
                }
            }

            if (match) filtered.add(p);
        }

        productAdapter.updateList(filtered);
    }

    private String buildInitials(String nameNorm) {
        String[] parts = nameNorm.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : parts) {
            if (!w.isEmpty()) sb.append(w.charAt(0));
        }
        return sb.toString();
    }

    private String normalize(String s) {
        s = s.toLowerCase(Locale.getDefault());
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        temp = temp.replace("đ", "d");
        return temp;
    }

    // ================== BUTTONS ==================

    private void setupButtons() {
        btnBack.setOnClickListener(v ->
                getParentFragmentManager().popBackStack()
        );

        btnOpenCart.setOnClickListener(v -> {
            if (cart.isEmpty()) {
                Toast.makeText(getContext(), "Chưa chọn món nào", Toast.LENGTH_SHORT).show();
            } else {
                openCartDialog();
            }
        });
    }

    // ================== PRODUCT CLICK (FROM ADAPTER) ==================

    @Override
    public void onProductClick(Product product) {
        // Khi bấm sản phẩm -> chọn số lượng trước khi thêm
        openQuantityDialog(product);
    }

    private void openQuantityDialog(Product product) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_choose_quantity, null, false);

        TextView tvName   = view.findViewById(R.id.tvProductName);
        TextView tvPrice  = view.findViewById(R.id.tvProductPrice);
        TextView tvQty    = view.findViewById(R.id.tvQuantity);
        ImageButton btnMinus = view.findViewById(R.id.btnMinus);
        ImageButton btnPlus  = view.findViewById(R.id.btnPlus);
        View btnConfirm      = view.findViewById(R.id.btnConfirmQty);

        tvName.setText(product.getName());
        tvPrice.setText(vnFormat.format((long) product.getPrice()) + " đ");

        int current = cart.containsKey(product) ? cart.get(product) : 1;
        if (current <= 0) current = 1;
        final int[] qty = new int[]{current};
        tvQty.setText(String.valueOf(qty[0]));

        btnMinus.setOnClickListener(v -> {
            if (qty[0] > 1) {
                qty[0]--;
                tvQty.setText(String.valueOf(qty[0]));
            }
        });

        btnPlus.setOnClickListener(v -> {
            qty[0]++;
            tvQty.setText(String.valueOf(qty[0]));
        });

        btnConfirm.setOnClickListener(v -> {
            cart.put(product, qty[0]);
            if (productAdapter != null) {
                productAdapter.notifyDataSetChanged();
            }
            updateCartSummary();
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

    // ================== CART SUMMARY (TRÊN MÀN HÌNH CHÍNH) ==================

    private void updateCartSummary() {
        int totalItems = 0;
        double totalMoney = 0;

        for (Map.Entry<Product, Integer> e : cart.entrySet()) {
            int q = e.getValue();
            totalItems += q;
            totalMoney += q * e.getKey().getPrice();
        }

        if (tvCartInfo != null) {
            tvCartInfo.setText("Giỏ hàng: " + totalItems + " món");
        }
        if (tvTotalMoney != null) {
            tvTotalMoney.setText(vnFormat.format((long) totalMoney) + " đ");
        }
    }

    // ================== CART DIALOG ==================

    private void openCartDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_cart, null, false);

        RecyclerView rvCart    = view.findViewById(R.id.rvCart);
        TextView tvCartTotal   = view.findViewById(R.id.tvCartTotalDialog);
        View btnConfirm        = view.findViewById(R.id.btnConfirmOrder);
        View btnClose          = view.findViewById(R.id.btnCloseCart);

        List<Map.Entry<Product, Integer>> cartEntries = new ArrayList<>(cart.entrySet());
        CartItemAdapter cartAdapter = new CartItemAdapter(cartEntries, this);
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCart.setAdapter(cartAdapter);

        // hiển thị tổng lần đầu
        double totalMoney = 0;
        for (Map.Entry<Product, Integer> e : cart.entrySet()) {
            totalMoney += e.getKey().getPrice() * e.getValue();
        }
        tvCartTotal.setText("Tổng: " + vnFormat.format((long) totalMoney) + " đ");

        btnClose.setOnClickListener(v -> dialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            if (cart.isEmpty()) {
                Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }
            createOrderAndFinish();
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

    // callback khi +/- trong giỏ hàng thay đổi (từ CartItemAdapter)
    @Override
    public void onCartChanged(List<Map.Entry<Product, Integer>> newEntries) {
        cart.clear();
        for (Map.Entry<Product, Integer> e : newEntries) {
            if (e.getValue() > 0) {
                cart.put(e.getKey(), e.getValue());
            }
        }
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
        updateCartSummary();
    }

    // ================== TẠO ĐƠN ==================

    private void createOrderAndFinish() {
        // build description + total
        StringBuilder description = new StringBuilder();
        double totalPrice = 0;
        int totalItems = 0;

        for (Map.Entry<Product, Integer> e : cart.entrySet()) {
            Product p = e.getKey();
            int q = e.getValue();
            totalItems += q;
            double itemTotal = p.getPrice() * q;
            totalPrice += itemTotal;

            description.append(q)
                    .append("x ")
                    .append(p.getName())
                    .append(", ");
        }

        if (description.length() > 2) {
            description.setLength(description.length() - 2);
        }

        // tạo id đơn mới
        int nextIndex = DataRepository.getInstance().getOrders().size() + 1;
        String id = String.format(Locale.getDefault(), "%03d", nextIndex);

        long now = System.currentTimeMillis();
        String createdBy = "Admin"; // sau này bạn truyền nhân viên đăng nhập vào

        Integer tableNum = Order.TYPE_AT_TABLE.equals(orderType) ? tableNumber : null;

        Order newOrder = new Order(
                id,
                description.toString(),
                totalPrice,
                orderType,
                tableNum,
                Order.STATUS_NEW,
                now,
                createdBy
        );

        DataRepository.getInstance().addOrder(newOrder);

        Toast.makeText(getContext(),
                "Đã tạo đơn #" + id + " (" + totalItems + " món)",
                Toast.LENGTH_SHORT).show();

        // quay lại danh sách đơn
        getParentFragmentManager().popBackStack();
    }
}
