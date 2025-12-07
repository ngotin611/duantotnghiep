package com.example.banhmiviet.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.controller.OrderController;
import com.example.banhmiviet.model.Order;
import com.example.banhmiviet.ui.adapters.OrderAdapter;
import com.example.banhmiviet.ui.dialogs.SelectProductDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.banhmiviet.data.DataRepository;


import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements SelectProductDialogFragment.OnOrderConfirmedListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddOrder;
    private OrderAdapter adapter;
    private List<Order> orderList;
    private DataRepository repo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        fabAddOrder = view.findViewById(R.id.fabAddOrder);

        repo = DataRepository.getInstance();
        orderList = repo.getOrders();

        adapter = new OrderAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fabAddOrder.setOnClickListener(v -> {
            SelectProductDialogFragment dialog = new SelectProductDialogFragment();
            dialog.show(getChildFragmentManager(), "SelectProductDialogFragment");
        });

        return view;
    }

    @Override
    public void onOrderConfirmed(Order order) {
        repo.addOrder(order);
        orderList.add(order);
        adapter.notifyItemInserted(orderList.size() - 1);
        Toast.makeText(getContext(), "Đã tạo đơn hàng " + order.getId(), Toast.LENGTH_SHORT).show();
    }
}
