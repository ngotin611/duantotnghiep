package com.example.duantn.helper;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Random;

public class OTPManager {
    private static final String PREF_NAME = "OTPPrefs";
    private static final String KEY_OTP = "otp_code";
    private static final String KEY_EMAIL = "otp_email";
    private static final String KEY_TIMESTAMP = "otp_timestamp";
    private static final long OTP_VALID_DURATION = 10 * 60 * 1000; // 10 phút

    private SharedPreferences preferences;

    public OTPManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Tạo mã OTP 6 chữ số
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 100000 - 999999
        return String.valueOf(otp);
    }

    // Lưu mã OTP và email
    public void saveOTP(String email, String otp) {
        preferences.edit()
                .putString(KEY_OTP, otp)
                .putString(KEY_EMAIL, email)
                .putLong(KEY_TIMESTAMP, System.currentTimeMillis())
                .apply();
    }

    // Kiểm tra mã OTP có hợp lệ không
    public boolean verifyOTP(String email, String otp) {
        String savedOTP = preferences.getString(KEY_OTP, "");
        String savedEmail = preferences.getString(KEY_EMAIL, "");
        long timestamp = preferences.getLong(KEY_TIMESTAMP, 0);

        // Kiểm tra email khớp
        if (!email.equals(savedEmail)) {
            return false;
        }

        // Kiểm tra mã OTP khớp
        if (!otp.equals(savedOTP)) {
            return false;
        }

        // Kiểm tra mã OTP còn hạn không (10 phút)
        long currentTime = System.currentTimeMillis();
        if (currentTime - timestamp > OTP_VALID_DURATION) {
            return false; // Mã OTP đã hết hạn
        }

        return true;
    }

    // Xóa mã OTP sau khi sử dụng
    public void clearOTP() {
        preferences.edit()
                .remove(KEY_OTP)
                .remove(KEY_EMAIL)
                .remove(KEY_TIMESTAMP)
                .apply();
    }

    // Lấy email đã lưu
    public String getSavedEmail() {
        return preferences.getString(KEY_EMAIL, "");
    }
}



