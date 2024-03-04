package com.example.diplommobileapp.services.httpwork;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyHttpClient {

    private static OkHttpClient client;
    private static final String COOKIE_PREFERENCES = "CookiePreferences";
    private static final String COOKIE_KEY = "AspNetCoreIdentityCookie";
    public static OkHttpClient getClient(Context context) {
        if (client == null) {
            synchronized (MyHttpClient.class) {
                if (client == null) {
                    CookieJar cookieJar = new CookieJar() {
                        private final List<Cookie> cookies = new ArrayList<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            this.cookies.addAll(cookies);
                            saveCookiesToPreferences(context, cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> storedCookies = getCookiesFromPreferences(context);
                            return storedCookies != null ? storedCookies : cookies;
                        }
                    };

                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .addInterceptor(loggingInterceptor)
                            .build();
                }
            }
        }
        return client;
    }

    private static void saveCookiesToPreferences(Context context, List<Cookie> cookies) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String jsonCookies = gson.toJson(cookies);
        editor.putString(COOKIE_KEY, jsonCookies);
        editor.apply();
    }

    private static List<Cookie> getCookiesFromPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE_PREFERENCES, Context.MODE_PRIVATE);
        String jsonCookies = preferences.getString(COOKIE_KEY, null);

        if (jsonCookies != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Cookie>>() {}.getType();
            return gson.fromJson(jsonCookies, type);
        } else {
            return null;
        }
    }
}
