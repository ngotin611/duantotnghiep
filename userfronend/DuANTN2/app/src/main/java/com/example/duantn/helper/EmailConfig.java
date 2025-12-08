package com.example.duantn.helper;

/**
 * Cấu hình email Gmail
 * 
 * HƯỚNG DẪN TẠO APP PASSWORD:
 * 1. Vào https://myaccount.google.com/
 * 2. Chọn "Bảo mật" (Security)
 * 3. Bật "Xác minh 2 bước" (2-Step Verification) nếu chưa bật
 * 4. Tìm "Mật khẩu ứng dụng" (App passwords)
 * 5. Tạo mật khẩu ứng dụng mới cho "Mail"
 * 6. Copy mật khẩu 16 ký tự và dán vào EMAIL_PASSWORD bên dưới
 */
public class EmailConfig {
    // ✅ ĐÃ CẤU HÌNH GMAIL
    public static final String EMAIL = "hau2k4lc@gmail.com"; // Email Gmail của bạn
    public static final String EMAIL_PASSWORD = "tdgtzcomnapcqygm"; // App Password (bỏ dấu cách)
    
    // Cấu hình SMTP Gmail
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";
    public static final String SMTP_AUTH = "true";
    public static final String SMTP_STARTTLS = "true";
}

