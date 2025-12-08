package com.example.banhmiviet.ui.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectProductAdapter extends RecyclerView.Adapter<SelectProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Map<Product, Integer> selectedQuantities = new HashMap<>();

    public SelectProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public Map<Product, Integer> getSelectedQuantities() {
        return selectedQuantities;
    }

    @NonNull
    @Override
    public SelectProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice() + " đ");

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
                    selectedQuantities.put(product, qty);
                } else {
                    selectedQuantities.remove(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        EditText edtQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtProductPrice);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
        }
    }
}
