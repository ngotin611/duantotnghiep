package com.example.banhmiviet.ui.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.InventoryItem;
import com.example.banhmiviet.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private final List<InventoryItem> originalList;
    private final List<InventoryItem> displayList;
    private final NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
    private final DataRepository repo;

    public InventoryAdapter(List<InventoryItem> items) {
        this.originalList = items != null ? items : new ArrayList<>();
        this.displayList = new ArrayList<>(this.originalList);
        this.repo = DataRepository.getInstance();
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryItem item = displayList.get(position);
        holder.bind(item, repo, vnFormat);
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public void filter(String keyword) {
        displayList.clear();
        if (TextUtils.isEmpty(keyword)) {
            displayList.addAll(originalList);
        } else {
            keyword = normalize(keyword);
            for (InventoryItem item : originalList) {
                Product p = repo.findProductById(item.getProductId());
                String name = p != null && p.getName() != null
                        ? p.getName()
                        : (item.getProductName() == null ? "" : item.getProductName());
                if (matchesSearch(name, keyword)) {
                    displayList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    // ====== ViewHolder ======
    static class InventoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView tvName, tvPrice, tvSku, tvInStock, tvStatus;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName     = itemView.findViewById(R.id.tvName);
            tvPrice    = itemView.findViewById(R.id.tvPrice);
            tvSku      = itemView.findViewById(R.id.tvSku);
            tvInStock  = itemView.findViewById(R.id.tvInStock);
            tvStatus   = itemView.findViewById(R.id.tvStatus);
        }

        public void bind(InventoryItem item, DataRepository repo, NumberFormat vn) {
            Product p = repo.findProductById(item.getProductId());

            String name = p != null && p.getName() != null
                    ? p.getName()
                    : item.getProductName();
            tvName.setText(name);

            // Giá
            double price = p != null ? p.getPrice() : 0;
            tvPrice.setText(vn.format(price) + " đ");

            // Mã sản phẩm
            String code = p != null && p.getId() != null ? p.getId() : item.getProductId();
            tvSku.setText("Mã: " + code);

            // Tồn kho
            int stock = item.getQuantity();
            tvInStock.setText("Tồn: " + stock);

            if (stock <= 0) {
                tvStatus.setText("Hết hàng");
            } else if (stock < 5) {
                tvStatus.setText("Sắp hết");
            } else {
                tvStatus.setText("Đủ hàng");
            }

            // Ảnh
            if (p != null && p.getImageResId() != 0) {
                imgProduct.setImageResource(p.getImageResId());
            } else {
                imgProduct.setImageResource(R.drawable.ic_inventory);
            }
        }
    }

    // ====== Helper search ======

    private boolean matchesSearch(String name, String keyword) {
        if (TextUtils.isEmpty(keyword)) return true;

        String normalizedName = normalize(name);

        if (normalizedName.contains(keyword)) return true;

        String[] words = normalizedName.split("\\s+");
        StringBuilder abbr = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) abbr.append(w.charAt(0));
        }
        String abbrStr = abbr.toString();
        return abbrStr.contains(keyword);
    }

    private String normalize(String s) {
        if (s == null) return "";
        s = s.toLowerCase(Locale.getDefault());
        s = s.replaceAll("[áàảãạăắằẳẵặâấầẩẫậ]", "a");
        s = s.replaceAll("[éèẻẽẹêếềểễệ]", "e");
        s = s.replaceAll("[íìỉĩị]", "i");
        s = s.replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o");
        s = s.replaceAll("[úùủũụưứừửữự]", "u");
        s = s.replaceAll("[ýỳỷỹỵ]", "y");
        s = s.replaceAll("đ", "d");
        return s;
    }
}
