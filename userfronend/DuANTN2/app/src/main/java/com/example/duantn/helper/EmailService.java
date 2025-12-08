package com.example.duantn.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    private Context context;

    public EmailService(Context context) {
        this.context = context;
    }

    // Gửi email chứa mã OTP
    public void sendOTPEmail(String email, String otp) {
        // Kiểm tra cấu hình email
        if (EmailConfig.EMAIL.equals("your-email@gmail.com") || 
            EmailConfig.EMAIL_PASSWORD.equals("your-app-password")) {
            // Nếu chưa cấu hình, hiển thị demo mode
            Toast.makeText(context, 
                "⚠️ Chưa cấu hình Gmail!\nMã OTP: " + otp + "\n\nVui lòng cấu hình trong EmailConfig.java", 
                Toast.LENGTH_LONG).show();
            android.util.Log.d("EmailService", "OTP (demo): " + otp);
            return;
        }

        // Gửi email thực tế trong background thread
        new SendEmailTask().execute(email, otp);
    }

    // AsyncTask để gửi email trong background
    private class SendEmailTask extends AsyncTask<String, Void, Boolean> {
        private String errorMessage = "";

        @Override
        protected Boolean doInBackground(String... params) {
            String recipientEmail = params[0];
            String otp = params[1];

            android.util.Log.d("EmailService", "Bắt đầu gửi email đến: " + recipientEmail);
            android.util.Log.d("EmailService", "Mã OTP: " + otp);
            android.util.Log.d("EmailService", "Từ email: " + EmailConfig.EMAIL);

            try {
                // Cấu hình properties
                Properties props = new Properties();
                props.put("mail.smtp.host", EmailConfig.SMTP_HOST);
                props.put("mail.smtp.port", EmailConfig.SMTP_PORT);
                props.put("mail.smtp.auth", EmailConfig.SMTP_AUTH);
                props.put("mail.smtp.starttls.enable", EmailConfig.SMTP_STARTTLS);
                props.put("mail.smtp.ssl.trust", EmailConfig.SMTP_HOST);

                // Tạo session với authentication
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EmailConfig.EMAIL, EmailConfig.EMAIL_PASSWORD);
                    }
                });

                // Tạo message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EmailConfig.EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("Mã OTP đặt lại mật khẩu");
                message.setText("Xin chào,\n\n" +
                        "Bạn đã yêu cầu đặt lại mật khẩu.\n\n" +
                        "Mã OTP của bạn là: " + otp + "\n\n" +
                        "Mã này có hiệu lực trong 10 phút.\n" +
                        "Vui lòng không chia sẻ mã này với bất kỳ ai.\n\n" +
                        "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ hỗ trợ");

                // Gửi email
                android.util.Log.d("EmailService", "Đang gửi email...");
                Transport.send(message);
                android.util.Log.d("EmailService", "✅ Email đã được gửi thành công!");
                return true;

            } catch (MessagingException e) {
                errorMessage = e.getMessage();
                android.util.Log.e("EmailService", "❌ Lỗi MessagingException: " + e.getMessage());
                e.printStackTrace();
                if (e.getCause() != null) {
                    android.util.Log.e("EmailService", "Nguyên nhân: " + e.getCause().getMessage());
                }
                return false;
            } catch (Exception e) {
                errorMessage = e.getMessage();
                android.util.Log.e("EmailService", "❌ Lỗi Exception: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(context, 
                    "✅ Mã OTP đã được gửi đến email của bạn!\n\nVui lòng kiểm tra hộp thư (có thể trong Spam)", 
                    Toast.LENGTH_LONG).show();
                android.util.Log.d("EmailService", "Email sent successfully");
            } else {
                // Hiển thị mã OTP trong Toast để test nếu gửi email thất bại
                String otpForTest = "";
                try {
                    // Lấy mã OTP từ OTPManager để hiển thị
                    com.example.duantn.helper.OTPManager otpManager = new com.example.duantn.helper.OTPManager(context);
                    String savedEmail = otpManager.getSavedEmail();
                    if (!savedEmail.isEmpty()) {
                        // Tìm mã OTP đã lưu (cần lấy từ SharedPreferences)
                        android.content.SharedPreferences prefs = context.getSharedPreferences("OTPPrefs", android.content.Context.MODE_PRIVATE);
                        String savedOTP = prefs.getString("otp_code", "");
                        if (!savedOTP.isEmpty()) {
                            otpForTest = "\n\nMã OTP (để test): " + savedOTP;
                        }
                    }
                } catch (Exception e) {
                    // Ignore
                }
                
                Toast.makeText(context, 
                    "❌ Lỗi gửi email: " + errorMessage + 
                    otpForTest +
                    (errorMessage.contains("535") ? "\n\n⚠️ Kiểm tra lại App Password" : 
                     errorMessage.contains("Could not connect") ? "\n\n⚠️ Kiểm tra kết nối internet" : ""), 
                    Toast.LENGTH_LONG).show();
                android.util.Log.e("EmailService", "Failed to send email: " + errorMessage);
            }
        }
    }
}
