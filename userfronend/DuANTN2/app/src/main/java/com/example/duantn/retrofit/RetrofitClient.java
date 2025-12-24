package com.example.duantn.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static Retrofit retrofit;
    // Địa chỉ IP backend - thay đổi theo môi trường
    // Emulator: http://10.0.2.2:8080/
    // Device thật: http://[IP_MÁY_TÍNH]:8080/ (ví dụ: http://192.168.1.100:8080/)
    // Lưu ý: baseUrl phải kết thúc bằng "/" để Retrofit nối path /api/products
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Tạo logging interceptor để debug API calls
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Tạo OkHttpClient với logging và timeout
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
