package com.example.diplommobileapp.services.httpwork;

import android.content.Context;

import com.example.diplommobileapp.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static final String BASE_URL = "http://192.168.1.2:5207/api/";
    private static IApi apiService;

    public static IApi getApiService(Context context) {
        if (apiService == null) {
            synchronized (RetrofitFactory.class) {
                if (apiService == null) {
                    OkHttpClient client = MyHttpClient.getClient(context);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    apiService = retrofit.create(IApi.class);
                }
            }
        }
        return apiService;
    }

}
