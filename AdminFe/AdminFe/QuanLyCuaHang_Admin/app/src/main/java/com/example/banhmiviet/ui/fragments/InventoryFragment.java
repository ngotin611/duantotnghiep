package com.example.banhmiviet.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.InventoryItem;
import com.example.banhmiviet.ui.adapters.InventoryAdapter;

import java.util.List;

public class InventoryFragment extends Fragment {

    private RecyclerView rvInventory;
    private InventoryAdapter adapter;
    private EditText edtSearch;
    private TextView tvSummary;

    private DataRepository repo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        edtSearch   = view.findViewById(R.id.edtSearchInventory);
        tvSummary   = view.findViewById(R.id.tvInventorySummary);
        rvInventory = view.findViewById(R.id.rvInventory);

        rvInventory.setLayoutManager(new LinearLayoutManager(getContext()));

        repo = DataRepository.getInstance();
        List<InventoryItem> items = repo.getInventoryItems();

        adapter = new InventoryAdapter(items);
        rvInventory.setAdapter(adapter);

        tvSummary.setText("Tổng: " + items.size() + " mặt hàng");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.filter(edtSearch.getText().toString());
        }
    }
}
