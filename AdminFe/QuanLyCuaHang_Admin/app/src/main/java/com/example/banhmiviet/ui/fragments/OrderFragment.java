package com.example.banhmiviet.ui.fragments;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.banhmiviet.ui.adapters.OrderAdapter;
import com.example.banhmiviet.ui.adapters.TableAdapter;
import com.example.banhmiviet.ui.dialogs.SelectProductDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderFragment extends Fragment implements SelectProductDialogFragment.OnOrderConfirmedListener {

    // ===== MODE =====
    private static final int MODE_ALL = 0;
    private static final int MODE_AT_TABLE = 1;
    private static final int MODE_TAKE_AWAY = 2;

    private int currentMode = MODE_AT_TABLE;

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddOrder;
    private Button btnAll, btnAtTable, btnTakeAway;

    private OrderAdapter orderAdapter;
    private TableAdapter tableAdapter;

    private final List<Order> orderList = new ArrayList<>();
    private DataRepository repo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        // ---- Ánh xạ ----
        recyclerView  = view.findViewById(R.id.recyclerViewOrders);
        fabAddOrder   = view.findViewById(R.id.fabAddOrder);

        btnAll        = view.findViewById(R.id.btnAll);
        btnAtTable    = view.findViewById(R.id.btnAtTable);
        btnTakeAway   = view.findViewById(R.id.btnTakeAway);

        // ---- Data ----
        repo = DataRepository.getInstance();
        orderList.clear();
        orderList.addAll(repo.getOrders());

        // ---- Adapter danh sách đơn (TẤT CẢ / MANG ĐI) ----
        orderAdapter = new OrderAdapter(orderList, new OrderAdapter.OnOrderActionListener() {
            @Override
            public void onViewDetail(Order order) {
                showOrderDetailDialog(order);
            }

            @Override
            public void onPay(Order order) {
                order.setStatus(Order.STATUS_PAID);
                Toast.makeText(getContext(),
                        "Đã tất toán đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
                refreshCurrentMode();
            }

            @Override
            public void onCancel(Order order) {
                order.setStatus(Order.STATUS_CANCELLED);
                Toast.makeText(getContext(),
                        "Đã hủy đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
                refreshCurrentMode();
            }

            @Override
            public void onPrint(Order order) {
                // Tạm thời chỉ toast, sau này nối máy in sau
                Toast.makeText(getContext(),
                        "In hóa đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        // ---- Adapter hiển thị bàn (TẠI BÀN) ----
        tableAdapter = new TableAdapter((tableNumber, activeOrder) -> {
            if (activeOrder == null) {
                Toast.makeText(getContext(),
                        "Bàn " + tableNumber + " đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                showOrderDetailDialog(activeOrder);
            }
        });

        // ===== BUTTON LISTENER =====
        btnAll.setOnClickListener(v -> setMode(MODE_ALL));
        btnAtTable.setOnClickListener(v -> setMode(MODE_AT_TABLE));
        btnTakeAway.setOnClickListener(v -> setMode(MODE_TAKE_AWAY));

        // ===== Floating button (Tạo đơn) =====
        fabAddOrder.setOnClickListener(v -> {
            // Tạm thời: nếu đang ở TẠI BÀN -> bàn 1, nếu MANG ĐI -> mã "TD01"
            String type = (currentMode == MODE_TAKE_AWAY)
                    ? Order.TYPE_TAKE_AWAY
                    : Order.TYPE_AT_TABLE;

            Integer tableNum = (currentMode == MODE_AT_TABLE) ? 1 : -1;
            String takeAwayCode = (currentMode == MODE_TAKE_AWAY) ? "TD01" : null;

            NewOrderFragment f = NewOrderFragment.newInstance(type, tableNum, takeAwayCode);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, f)   // id container phù hợp với app của bạn
                    .addToBackStack(null)
                    .commit();
        });


        // ===== Mặc định mở TẠI BÀN =====
        setMode(MODE_AT_TABLE);

        return view;
    }

    // ============================================================
    //                     SET MODE HIỂN THỊ
    // ============================================================

    private void setMode(int mode) {
        currentMode = mode;
        highlightModeButton();

        if (mode == MODE_AT_TABLE) {
            // --> Dạng lưới bàn 4 cột
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            recyclerView.setAdapter(tableAdapter);
        } else {
            // --> Dạng danh sách đơn
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            List<Order> filtered = new ArrayList<>();
            for (Order o : repo.getOrders()) {
                if (mode == MODE_ALL) {
                    filtered.add(o);
                } else if (mode == MODE_TAKE_AWAY &&
                        Order.TYPE_TAKE_AWAY.equals(o.getType())) {
                    filtered.add(o);
                }
            }

            orderAdapter.updateList(filtered);
            recyclerView.setAdapter(orderAdapter);
        }
    }

    private void refreshCurrentMode() {
        setMode(currentMode);
    }

    // ============================================================
    //              ĐỔI MÀU NÚT THEO MODE ĐANG CHỌN
    // ============================================================

    private void highlightModeButton() {
        // Bạn có thể tuỳ biến lại background & textColor theo ý
        btnAll.setSelected(currentMode == MODE_ALL);
        btnAtTable.setSelected(currentMode == MODE_AT_TABLE);
        btnTakeAway.setSelected(currentMode == MODE_TAKE_AWAY);

        // Nếu bạn có bg_filter_*.xml thì set như sau:
        btnAll.setBackgroundResource(
                currentMode == MODE_ALL ? R.drawable.bg_filter_left_selected : R.drawable.bg_filter_left);
        btnAtTable.setBackgroundResource(
                currentMode == MODE_AT_TABLE ? R.drawable.bg_filter_middle_selected : R.drawable.bg_filter_middle);
        btnTakeAway.setBackgroundResource(
                currentMode == MODE_TAKE_AWAY ? R.drawable.bg_filter_right_selected : R.drawable.bg_filter_right);
    }

    // ============================================================
    //               SAU KHI TẠO ĐƠN TỪ DIALOG
    // ============================================================

    @Override
    public void onOrderConfirmed(Order order) {
        repo.addOrder(order); // thêm vào kho chung

        Toast.makeText(getContext(),
                "Đã tạo đơn hàng #" + order.getId(), Toast.LENGTH_SHORT).show();

        // Cập nhật lại UI theo mode hiện tại
        refreshCurrentMode();
    }

    // ============================================================
    //                  POPUP XEM CHI TIẾT ĐƠN
    // ============================================================

    private void showOrderDetailDialog(Order order) {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_order_detail, null);

        TextView tvOrderId      = dialogView.findViewById(R.id.tvOrderId);
        TextView tvOrderTime    = dialogView.findViewById(R.id.tvOrderTime);
        TextView tvOrderType    = dialogView.findViewById(R.id.tvOrderType);
        TextView tvOrderCreator = dialogView.findViewById(R.id.tvOrderCreator);
        TextView tvOrderTotal   = dialogView.findViewById(R.id.tvOrderTotal);
        TextView tvOrderStatus  = dialogView.findViewById(R.id.tvOrderStatus);
        LinearLayout layoutItems= dialogView.findViewById(R.id.layoutOrderItems);

        Button btnPrint  = dialogView.findViewById(R.id.btnPrintInvoice);
        Button btnPay    = dialogView.findViewById(R.id.btnPayOrder);
        Button btnCancel = dialogView.findViewById(R.id.btnCancelOrder);

        // ===== Fill dữ liệu =====
        // Loại đơn
        String typeText;
        if (Order.TYPE_AT_TABLE.equals(order.getType())) {
            typeText = "Tại bàn " + (order.getTableNumber() == null ? "" : order.getTableNumber());
        } else {
            typeText = "Mang đi";
        }

        tvOrderId.setText("#" + order.getId() + " - " + typeText);
        tvOrderType.setText("Loại: " + typeText);

        // Thời gian
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String timeStr = df.format(new Date(order.getCreatedTime()));
        String agoStr  = getTimeAgo(order.getCreatedTime());
        tvOrderTime.setText("Thời gian: " + timeStr + " (" + agoStr + ")");

        // Người tạo
        tvOrderCreator.setText("Người tạo: " +
                (order.getCreatedBy() == null ? "Không rõ" : order.getCreatedBy()));

        // Tổng tiền
        NumberFormat vn = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvOrderTotal.setText("Tổng tiền: " + vn.format(order.getTotalPrice()) + " đ");

        // Trạng thái
        tvOrderStatus.setText("Trạng thái: " + statusToText(order.getStatus()));

        // Chi tiết món: tên trái - số lượng phải
        layoutItems.removeAllViews();
        String desc = order.getDescription();
        if (desc != null && !desc.trim().isEmpty()) {
            String[] parts = desc.split(",");
            for (String part : parts) {
                String itemStr = part.trim();
                if (itemStr.isEmpty()) continue;

                String quantityText = "";
                String nameText = itemStr;

                int xIndex = itemStr.indexOf("x");
                if (xIndex > 0) {
                    quantityText = itemStr.substring(0, xIndex).trim(); // số lượng
                    nameText = itemStr.substring(xIndex + 1).trim();     // tên món
                }

                LinearLayout row = new LinearLayout(getContext());
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                row.setPadding(0, 4, 0, 4);

                TextView tvName = new TextView(getContext());
                tvName.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                ));
                tvName.setText(nameText);
                tvName.setTextSize(14);

                TextView tvQty = new TextView(getContext());
                tvQty.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                tvQty.setText(quantityText);
                tvQty.setTextSize(14);
                tvQty.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                row.addView(tvName);
                row.addView(tvQty);

                layoutItems.addView(row);
            }
        }

        androidx.appcompat.app.AlertDialog dialog =
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setView(dialogView)
                        .create();

        // ===== Sự kiện nút =====
        btnPrint.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "In hóa đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnPay.setOnClickListener(v -> {
            order.setStatus(Order.STATUS_PAID);
            refreshCurrentMode();
            Toast.makeText(getContext(),
                    "Đã tất toán đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            order.setStatus(Order.STATUS_CANCELLED);
            refreshCurrentMode();
            Toast.makeText(getContext(),
                    "Đã hủy đơn #" + order.getId(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    // ============================================================
    //                    Helper: time ago
    // ============================================================

    private String getTimeAgo(long createdTime) {
        if (createdTime <= 0) return "không rõ";

        long now = System.currentTimeMillis();
        long diff = now - createdTime;
        if (diff < 0) diff = 0;

        long minutes = diff / 60000;
        long hours = minutes / 60;
        long remainMin = minutes % 60;

        if (minutes < 1) return "vừa xong";
        if (minutes < 60) return minutes + " phút trước";
        if (remainMin == 0) return hours + " giờ trước";
        return hours + " giờ " + remainMin + " phút trước";
    }

    private String statusToText(String status) {
        if (Order.STATUS_NEW.equals(status)) return "Chưa thanh toán";
        if (Order.STATUS_PAID.equals(status)) return "Đã thanh toán";
        if (Order.STATUS_CANCELLED.equals(status)) return "Đã hủy";
        return status;
    }
}
