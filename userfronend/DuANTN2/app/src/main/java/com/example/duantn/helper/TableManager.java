package com.example.duantn.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class TableManager {
    private static final String PREF_NAME = "TablePrefs";
    private static final String KEY_TABLE_NUMBER = "table_number";
    private static final String KEY_ORDER_TYPE = "order_type";
    private static final String KEY_IS_TABLE_SELECTED = "is_table_selected";

    private SharedPreferences preferences;

    public TableManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Lưu thông tin bàn đã chọn
    public void saveTableInfo(String tableNumber, String orderType) {
        preferences.edit()
                .putString(KEY_TABLE_NUMBER, tableNumber)
                .putString(KEY_ORDER_TYPE, orderType)
                .putBoolean(KEY_IS_TABLE_SELECTED, true)
                .apply();
    }

    // Lấy số bàn
    public String getTableNumber() {
        return preferences.getString(KEY_TABLE_NUMBER, "");
    }

    // Lấy loại đơn (Tại quán / Mang đi)
    public String getOrderType() {
        return preferences.getString(KEY_ORDER_TYPE, "");
    }

    // Kiểm tra đã chọn bàn chưa
    public boolean isTableSelected() {
        return preferences.getBoolean(KEY_IS_TABLE_SELECTED, false);
    }

    // Xóa thông tin bàn (khi đăng xuất hoặc đổi bàn)
    public void clearTableInfo() {
        preferences.edit()
                .remove(KEY_TABLE_NUMBER)
                .remove(KEY_ORDER_TYPE)
                .remove(KEY_IS_TABLE_SELECTED)
                .apply();
    }
}




