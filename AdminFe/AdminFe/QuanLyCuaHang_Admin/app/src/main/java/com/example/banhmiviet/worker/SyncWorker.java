package com.example.banhmiviet.worker;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.banhmiviet.network.ApiClient;
import com.example.banhmiviet.network.ApiService;
import com.example.banhmiviet.util.SharedPrefUtil;
import retrofit2.Call;
import retrofit2.Response;

public class SyncWorker extends Worker {
    private static final String BASE_URL = "https://api.example.com/";

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            ApiService api = ApiClient.getClient(getApplicationContext(), BASE_URL).create(ApiService.class);
            Call<com.example.banhmiviet.model.DashboardSummary> call = api.getDashboardSummary("2025-01-01","2025-12-31");
            Response<com.example.banhmiviet.model.DashboardSummary> resp = call.execute();
            if (resp.isSuccessful() && resp.body()!=null) {
                // Lưu tạm JSON vào SharedPreferences để UI dùng (serialize to gson string)
                SharedPrefUtil sp = new SharedPrefUtil(getApplicationContext());
                // example store: you'll implement saveSummary method or save raw JSON
                // sp.saveSummary(new Gson().toJson(resp.body()));
                return Result.success();
            } else return Result.retry();
        } catch (Exception e) {
            return Result.retry();
        }
    }
}
