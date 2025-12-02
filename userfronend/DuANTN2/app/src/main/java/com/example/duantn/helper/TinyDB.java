package com.example.duantn.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.duantn.models.FoodDomain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TinyDB {
    private SharedPreferences preferences;
    private String CART_LIST_KEY = "CartList";
    private Gson gson;

    public TinyDB(Context context) {
        preferences = context.getSharedPreferences("MyCart", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void putListObject(String key, ArrayList<FoodDomain> list) {
        String json = gson.toJson(list);
        preferences.edit().putString(key, json).apply();
    }

    public ArrayList<FoodDomain> getListObject(String key) {
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<FoodDomain>>() {}.getType();
        return gson.fromJson(json, type) != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    public void clearCart() {
        preferences.edit().remove(CART_LIST_KEY).apply();
    }

    // ✅ HÀM MỚI THÊM VÀO ĐÂY
    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }
}
