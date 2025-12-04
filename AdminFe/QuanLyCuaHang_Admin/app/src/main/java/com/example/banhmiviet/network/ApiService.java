package com.example.banhmiviet.network;

import com.example.banhmiviet.model.DashboardSummary;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/dashboard/summary")
    Call<DashboardSummary> getDashboardSummary(@Query("from") String from, @Query("to") String to);
}
