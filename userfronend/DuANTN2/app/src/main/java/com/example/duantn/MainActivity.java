package com.example.duantn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.NotificationsActivity;
import com.example.duantn.SettingsActivity;
import com.example.duantn.adapter.FoodAdapter;
import com.example.duantn.helper.ManagmentCart;
import com.example.duantn.helper.ProductMapper;
import com.example.duantn.helper.TableManager;
import com.example.duantn.interface_api.CategoryApi;
import com.example.duantn.interface_api.ProductApi;
import com.example.duantn.models.CategoryDomain;
import com.example.duantn.models.FoodDomain;
import com.example.duantn.models.product.ResponseProduct;
import com.example.duantn.models.page.PageResponse;
import com.example.duantn.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private ArrayList<FoodDomain> foodList;
    private ArrayList<FoodDomain> allFoodList;
    private EditText searchEdt;
    private ImageView cartBtn;
    private TextView cartBadge;

    private Button btnBanhMi, btnHamburger, btnPizza, btnNuocUong;
    private String currentCategory = "Tất cả";
    private String currentSearchText = "";
    
    // API services
    private ProductApi productApi;
    private CategoryApi categoryApi;
    private ProgressBar progressBar;
    
    // Map để lưu category name theo ID
    private Map<Long, String> categoryMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kiểm tra đã chọn bàn chưa, nếu chưa thì chuyển đến màn hình chọn bàn
        TableManager tableManager = new TableManager(this);
        if (!tableManager.isTableSelected()) {
            Intent intent = new Intent(MainActivity.this, TableSelectionActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        recyclerView = findViewById(R.id.gridView);
        searchEdt = findViewById(R.id.edt_search);
        cartBtn = findViewById(R.id.img_cart);
        cartBadge = findViewById(R.id.cart_badge);
        progressBar = findViewById(R.id.progressBar); // Có thể null nếu layout chưa có ProgressBar

        btnBanhMi = findViewById(R.id.btn_banhmi);
        btnHamburger = findViewById(R.id.btn_hamburger);
        btnPizza = findViewById(R.id.btn_pizza);
        btnNuocUong = findViewById(R.id.btn_nuocuong);

        // Khởi tạo API services
        productApi = RetrofitClient.getRetrofitInstance().create(ProductApi.class);
        categoryApi = RetrofitClient.getRetrofitInstance().create(CategoryApi.class);

        // Khởi tạo danh sách rỗng trước
        allFoodList = new ArrayList<>();
        foodList = new ArrayList<>();
        
        setupRecyclerView();
        setupSearch();
        setupCart();
        setupCategoryButtons();
        setupBottomNavigation();
        updateCartBadge();
        
        // Load dữ liệu từ API
        loadCategories();
        loadProducts();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(cartUpdateReceiver, new IntentFilter("UPDATE_CART_BADGE"),
                    Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(cartUpdateReceiver, new IntentFilter("UPDATE_CART_BADGE"));
        }
    }

    private final BroadcastReceiver cartUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int cartCount = intent.getIntExtra("cart_count", 0);
            updateCartBadge(cartCount);
        }
    };

    /**
     * Load danh sách categories từ API
     */
    private void loadCategories() {
        Call<List<CategoryDomain>> call = categoryApi.getAllCategories();
        call.enqueue(new Callback<List<CategoryDomain>>() {
            @Override
            public void onResponse(Call<List<CategoryDomain>> call, Response<List<CategoryDomain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu category map để dùng khi load products
                    for (CategoryDomain category : response.body()) {
                        if (category.getId() != null) {
                            categoryMap.put(category.getId(), category.getTitle());
                        }
                    }
                    Log.d("MainActivity", "Loaded " + categoryMap.size() + " categories");
                } else {
                    Log.e("MainActivity", "Failed to load categories: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDomain>> call, Throwable t) {
                Log.e("MainActivity", "Error loading categories", t);
                // Nếu không load được category, vẫn tiếp tục load products
            }
        });
    }

    /**
     * Load danh sách products từ API
     */
    /**
     * Load danh sách products từ API
     */
    private void loadProducts() {
        // Hiển thị loading indicator nếu có
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // Gọi API lấy sản phẩm có phân trang (page=0, size=50, sort theo id tăng dần)
        Call<PageResponse<ResponseProduct>> call = productApi.getProducts(0, 50, "id,asc");
        call.enqueue(new Callback<PageResponse<ResponseProduct>>() {
            @Override
            public void onResponse(Call<PageResponse<ResponseProduct>> call, Response<PageResponse<ResponseProduct>> response) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (response.isSuccessful() && response.body() != null && response.body().getContent() != null) {
                    // Chuyển đổi ResponseProduct sang FoodDomain
                    List<ResponseProduct> products = response.body().getContent();
                    List<FoodDomain> loadedFoods = ProductMapper.toFoodDomainList(products);
                    
                    // Set category name cho mỗi food
                    for (int i = 0; i < products.size() && i < loadedFoods.size(); i++) {
                        ResponseProduct product = products.get(i);
                        FoodDomain food = loadedFoods.get(i);
                        
                        // Lấy category name từ map
                        if (product.getIdCategory() != null && categoryMap.containsKey(product.getIdCategory())) {
                            food.setCategory(categoryMap.get(product.getIdCategory()));
                        } else {
                            food.setCategory("Khác"); // Default category
                        }
                    }
                    
                    // Cập nhật danh sách
                    allFoodList.clear();
                    allFoodList.addAll(loadedFoods);
                    
                    // Áp dụng filter hiện tại
                    filter();
                    
                    Log.d("MainActivity", "Loaded " + loadedFoods.size() + " products from API");
                } else {
                    Log.e("MainActivity", "Failed to load products: " + response.code());
                    Toast.makeText(MainActivity.this, "Không thể tải danh sách sản phẩm. Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    // Fallback: sử dụng dữ liệu mẫu nếu API thất bại
                    initFoodListFallback();
                }
            }

            @Override
            public void onFailure(Call<PageResponse<ResponseProduct>> call, Throwable t) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                
                Log.e("MainActivity", "Error loading products", t);
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Fallback: sử dụng dữ liệu mẫu nếu API thất bại
                initFoodListFallback();
            }
        });
    }

    /**
     * Fallback: Load dữ liệu mẫu nếu API thất bại
     */
    private void initFoodListFallback() {
        allFoodList = new ArrayList<>();
        allFoodList.add(new FoodDomain("Bánh mì Xúc Xích", "anh_1", "Bánh mì Việt Nam truyền thống với xúc xích tươi, rau sống giòn, dưa leo, cà rốt, ngò gai và sốt mayonnaise đặc biệt", 15000, "Bánh mì"));
        allFoodList.add(new FoodDomain("Bánh mì thịt nướng", "anh_30", "Bánh mì thịt nướng đặc biệt với thịt heo nướng than hoa thơm lừng, ướp gia vị đặc biệt, rau sống tươi, dưa leo, cà rốt và sốt mayonnaise. Thịt mềm, béo ngậy, hương vị đặc trưng miền Nam", 20000, "Bánh mì"));
        allFoodList.add(new FoodDomain("bánh mì chả", "anh_31", "Bánh mì chả lụa thơm ngon với chả lụa mềm mịn, rau sống tươi, dưa leo giòn, cà rốt ngọt và sốt mayonnaise. Chả được làm từ thịt heo tươi", 12000, "Bánh mì"));
        allFoodList.add(new FoodDomain("bánh mì pate", "anh_32", "Bánh mì pate là sự kết hợp hoàn hảo giữa lớp vỏ bánh giòn rụm và phần nhân pate mềm mịn, béo ngậy. Pate thơm nồng, đậm vị gan, quyện cùng chút bơ, dưa leo, rau mùi và nước tương tạo nên hương vị hấp dẫn, ăn hoài không ngán. Đây là món ăn quen thuộc, đơn giản mà ngon khó cưỡng, đặc biệt khi dùng nóng", 10000, "Bánh mì"));
        allFoodList.add(new FoodDomain("Bánh mì gà", "anh_33", "Bánh mì gà xé cay với thịt gà xé sợi, ướp gia vị cay nồng, rau sống tươi, dưa leo giòn và sốt mayonnaise. Thịt gà mềm, vị cay đậm đà", 25000, "Bánh mì"));
        allFoodList.add(new FoodDomain("Bánh mì trứng", "anh_34", "Bánh mì trứng là sự kết hợp hoàn hảo giữa lớp vỏ bánh mì giòn rụm bên ngoài và phần nhân trứng thơm béo bên trong. Trứng được chiên vừa chín tới, giữ được độ mềm mại, thêm một chút nước tương hoặc tương ớt cay nhẹ làm tăng hương vị đậm đà", 22000, "Bánh mì"));

        allFoodList.add(new FoodDomain("Trà Tắc", "anh_35", "Trà tắc chua ngọt thanh mát, pha cùng tắc tươi và đá lạnh, giải khát cực đã.", 15000, "Nước uống"));
        allFoodList.add(new FoodDomain("Chanh Dây", "anh_36", "Nước chanh dây thơm nồng, chua dịu và ngọt nhẹ, rất tốt cho sức khỏe và làm dịu cơn khát.", 20000, "Nước uống"));
        allFoodList.add(new FoodDomain("Trà Dâu", "anh_37", "Trà dâu có vị ngọt nhẹ và hương thơm tự nhiên của dâu tây, thêm đá mát lạnh, thích hợp mọi lúc.", 35000, "Nước uống"));
        allFoodList.add(new FoodDomain("Trà Dưa Lưới", "anh_38", "Trà dưa lưới thơm dịu, hậu ngọt mát và mang đến cảm giác sảng khoái tươi mới.", 25000, "Nước uống"));
        allFoodList.add(new FoodDomain("Trà Dứa Nha Đam", "anh_39", "Trà dứa kết hợp nha đam giòn mát, vị chua ngọt hài hòa giúp thanh lọc cơ thể và giải nhiệt tốt.", 25000, "Nước uống"));

        allFoodList.add(new FoodDomain("Pizza bò", "anh_16", "Pizza bò Ý truyền thống với đế bánh mỏng giòn, thịt bò xay tươi, phô mai Mozzarella chảy, sốt cà chua đặc biệt, rau oregano và lá basil tươi. Hương vị đậm đà kiểu Ý, đế giòn, nhân phong phú.", 120000, "Pizza"));
        allFoodList.add(new FoodDomain("Pizza gà", "anh_21", "Pizza gà nấm với đế bánh mỏng, thịt gà tươi, nấm tươi, phô mai Mozzarella, sốt cà chua và rau oregano. Thịt gà mềm, nấm thơm, hương vị đặc biệt.", 100000, "Pizza"));
        allFoodList.add(new FoodDomain("Pizza hải sản", "anh_20", "Pizza hải sản tươi với đế bánh mỏng, tôm tươi, mực tươi, cá hồi, phô mai Mozzarella, sốt cà chua và rau oregano. Hải sản tươi ngon, giàu dinh dưỡng, hương vị biển đặc trưng.", 150000, "Pizza"));
        allFoodList.add(new FoodDomain("Pizza phô mai", "anh_18", "Pizza 4 phô mai với đế bánh mỏng, phô mai Mozzarella, Cheddar, Parmesan và Gouda chảy, sốt cà chua và rau oregano. Phô mai béo ngậy, đậm đà, hương vị đặc biệt cho người yêu phô mai.", 130000, "Pizza"));

        allFoodList.add(new FoodDomain("Hamburger bò", "anh_11", "Hamburger bò Mỹ với thịt bò xay tươi, nướng than hoa, phô mai béo ngậy, rau sống tươi, cà chua, dưa leo và sốt đặc biệt. Bánh mềm, thịt bò mọng nước, hương vị đậm đà kiểu Mỹ.", 35000, "Hamburger"));
        allFoodList.add(new FoodDomain("Hamburger gà", "anh_15", "Hamburger gà giòn với ức gà tươi, tẩm bột chiên giòn, rau sống tươi, cà chua, dưa leo và sốt mayonnaise. Thịt gà giòn rụm bên ngoài, mềm mịn bên trong, hương vị đặc biệt.", 30000, "Hamburger"));
        allFoodList.add(new FoodDomain("Hamburger phô mai", "anh_13", "Hamburger phô mai béo ngậy với thịt bò xay tươi, phô mai Cheddar và Mozzarella chảy, rau sống tươi, cà chua và sốt đặc biệt. Phô mai béo ngậy, thịt mềm, hương vị đậm đà.", 40000, "Hamburger"));
        allFoodList.add(new FoodDomain("Hamburger cá", "anh_10", "Hamburger cá hồi tươi với phi lê cá hồi nướng, rau sống tươi, cà chua, dưa leo và sốt tartar đặc biệt. Cá hồi tươi, mềm mịn, giàu omega-3, hương vị đặc biệt.", 45000, "Hamburger"));

        filter();
    }

    private void setupRecyclerView() {
        adapter = new FoodAdapter(foodList, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setupCategoryButtons() {
        btnBanhMi.setOnClickListener(v -> {
            currentCategory = "Bánh mì";
            filter();
            updateButtonStyles(btnBanhMi);
        });

        btnHamburger.setOnClickListener(v -> {
            currentCategory = "Hamburger";
            filter();
            updateButtonStyles(btnHamburger);
        });

        btnPizza.setOnClickListener(v -> {
            currentCategory = "Pizza";
            filter();
            updateButtonStyles(btnPizza);
        });

        btnNuocUong.setOnClickListener(v -> {
            currentCategory = "Nước uống";
            filter();
            updateButtonStyles(btnNuocUong);
        });
    }

    private void updateButtonStyles(Button selectedButton) {
        btnBanhMi.setBackgroundTintList(getColorStateList(R.color.white));
        btnBanhMi.setTextColor(getColor(R.color.category_text));

        btnHamburger.setBackground(getDrawable(R.drawable.border_button));
        btnHamburger.setTextColor(getColor(R.color.category_text));

        btnPizza.setBackground(getDrawable(R.drawable.border_button));
        btnPizza.setTextColor(getColor(R.color.category_text));

        btnNuocUong.setBackground(getDrawable(R.drawable.border_button));
        btnNuocUong.setTextColor(getColor(R.color.category_text));

        if (selectedButton == btnBanhMi) {
            btnBanhMi.setBackgroundTintList(getColorStateList(R.color.category_selected_bg));
            btnBanhMi.setTextColor(getColor(R.color.category_selected_text));
        } else if (selectedButton == btnHamburger) {
            btnHamburger.setBackgroundTintList(getColorStateList(R.color.category_selected_bg));
            btnHamburger.setTextColor(getColor(R.color.category_selected_text));
        } else if (selectedButton == btnPizza) {
            btnPizza.setBackgroundTintList(getColorStateList(R.color.category_selected_bg));
            btnPizza.setTextColor(getColor(R.color.category_selected_text));
        } else if (selectedButton == btnNuocUong) {
            btnNuocUong.setBackgroundTintList(getColorStateList(R.color.category_selected_bg));
            btnNuocUong.setTextColor(getColor(R.color.category_selected_text));
        }
    }

    private void setupSearch() {
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchText = s.toString().trim();
                filter();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void filter() {
        ArrayList<FoodDomain> foodList = new ArrayList<>();

        for (FoodDomain item : allFoodList) {
            boolean matchesCategory = currentCategory.equals("Tất cả") || item.getCategory().equals(currentCategory);
            boolean matchesSearch = currentSearchText.isEmpty() ||
                    item.getTitle().toLowerCase().contains(currentSearchText.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(currentSearchText.toLowerCase());

            if (matchesCategory && matchesSearch) {
                foodList.add(item);
            }
        }

        this.foodList.clear();
        this.foodList.addAll(foodList);
        adapter.notifyDataSetChanged();
    }

    private void setupCart() {
        cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            // Đánh dấu mục Home là đang được chọn
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
            
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    // Đã ở màn home, không làm gì
                    return true;
                } else if (itemId == R.id.nav_notifications) {
                    Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            });
        }
    }

    private void updateCartBadge() {
        ManagmentCart managmentCart = new ManagmentCart(this);
        int cartCount = managmentCart.getListCart().size();
        updateCartBadge(cartCount);
    }

    private void updateCartBadge(int count) {
        if (count > 0) {
            cartBadge.setVisibility(View.VISIBLE);
            cartBadge.setText(String.valueOf(count));
        } else {
            cartBadge.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cartUpdateReceiver);
    }
}