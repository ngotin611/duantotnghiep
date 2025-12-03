package com.example.foodbe.config;

public class SecurityConstant {
    public static final String[] WHITE_LIST = {
      "/api/auth/**"
    };
    public static final String[] PUBLIC_GET = {
            "/api/categories/**","/api/products/**","/api/reviews/**"
    };
    public static final String[] PUBLIC_POST = {
            "/api/users"
    };
}
