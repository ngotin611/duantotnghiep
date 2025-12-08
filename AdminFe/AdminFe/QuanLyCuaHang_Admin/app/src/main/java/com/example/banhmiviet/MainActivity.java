package com.example.banhmiviet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.banhmiviet.controller.LoginActivity;
import com.example.banhmiviet.ui.fragments.EmployeeFragment;
import com.example.banhmiviet.ui.fragments.HomeFragment;
import com.example.banhmiviet.ui.fragments.InventoryFragment;
import com.example.banhmiviet.ui.fragments.OrderFragment;
import com.example.banhmiviet.ui.fragments.ProductFragment;
import com.example.banhmiviet.ui.fragments.SettingsFragment;
import com.example.banhmiviet.ui.fragments.StatisticsFragment;
import com.example.banhmiviet.ui.fragments.UserFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Ẩn title để logo (ImageView) ở giữa toolbar hiển thị đẹp
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Drawer + NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // ActionBarDrawerToggle: sử dụng icon mặc định (hamburger)
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Cho phép hiển thị hamburger
        toggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Load fragment mặc định (Trang chủ)
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            if (navigationView != null) navigationView.setCheckedItem(R.id.nav_home);
        }

        // Xử lý chọn item trong drawer — dùng if/else (không dùng switch-case)
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_orders) {
                selectedFragment = new OrderFragment();
            } else if (id == R.id.nav_products) {
                selectedFragment = new ProductFragment();
            } else if (id == R.id.nav_employees) {
                selectedFragment = new EmployeeFragment();
            } else if (id == R.id.nav_users) {
                selectedFragment = new UserFragment();
            } else if (id == R.id.nav_inventory) {
                selectedFragment = new InventoryFragment();
            } else if (id == R.id.nav_stats) {
                selectedFragment = new StatisticsFragment();
            } else if (id == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            } else if (id == R.id.nav_logout) {
                // Đăng xuất: về LoginActivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    // Inflate menu (chuông) vào toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Xử lý click trên toolbar (chuông)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Nếu ActionBarDrawerToggle xử lý thì trả true
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_notification) {
            // Xử lý sự kiện chuông ở đây
            Toast.makeText(this, "Chưa có thông báo mới", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Khi nhấn back: nếu drawer mở thì đóng trước
    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // ------------------------------
    // Public helper methods để HomeFragment gọi khi click card
    // ------------------------------

    public void openOrders() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new OrderFragment())
                .commit();
        if (navigationView != null) navigationView.setCheckedItem(R.id.nav_orders);
        drawerLayout.closeDrawers();
    }

    public void openProducts() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ProductFragment())
                .commit();
        if (navigationView != null) navigationView.setCheckedItem(R.id.nav_products);
        drawerLayout.closeDrawers();
    }

    public void openCustomers() {
        // mở UserFragment (biến thể màn quản lý khách/người dùng)
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new UserFragment())
                .commit();
        if (navigationView != null) navigationView.setCheckedItem(R.id.nav_users);
        drawerLayout.closeDrawers();
    }

    public void openStatistics() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new StatisticsFragment())
                .commit();
        if (navigationView != null) navigationView.setCheckedItem(R.id.nav_stats);
        drawerLayout.closeDrawers();
    }
}
