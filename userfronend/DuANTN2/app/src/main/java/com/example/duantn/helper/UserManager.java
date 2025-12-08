package com.example.duantn.helper;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String ACCOUNT_FILE = "account.txt";
    private Context context;

    public UserManager(Context context) {
        this.context = context;
    }

    // Kiểm tra email có tồn tại không
    public boolean emailExists(String email) {
        try {
            FileInputStream fis = context.openFileInput(ACCOUNT_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String savedEmail = parts[0].trim();
                    if (email.equals(savedEmail)) {
                        reader.close();
                        fis.close();
                        return true;
                    }
                }
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật mật khẩu mới
    public boolean updatePassword(String email, String newPassword) {
        try {
            // Đọc tất cả tài khoản
            FileInputStream fis = context.openFileInput(ACCOUNT_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            List<String> lines = new ArrayList<>();
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String savedEmail = parts[0].trim();
                    if (email.equals(savedEmail)) {
                        // Cập nhật mật khẩu mới
                        lines.add(email + "," + newPassword);
                        found = true;
                    } else {
                        lines.add(line); // Giữ nguyên dòng khác
                    }
                } else {
                    lines.add(line); // Giữ nguyên dòng không hợp lệ
                }
            }
            reader.close();
            fis.close();

            if (!found) {
                return false; // Không tìm thấy email
            }

            // Ghi lại toàn bộ file
            FileOutputStream fos = context.openFileOutput(ACCOUNT_FILE, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            for (String l : lines) {
                writer.write(l + "\n");
            }
            writer.close();
            fos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}



