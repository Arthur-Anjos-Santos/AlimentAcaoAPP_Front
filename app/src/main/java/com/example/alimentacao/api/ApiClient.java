package com.example.alimentacao.api;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Use 10.0.2.2 para acessar localhost no emulador Android
    private static final String BASE_URL = "http://xxx.xxx.x.xx:8080/";
    private static volatile Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    retrofit = createRetrofitInstance();
                }
            }
        }
        return retrofit.create(ApiService.class);
    }

    private static Retrofit createRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS) // timeout de conexão
                .readTimeout(30, TimeUnit.SECONDS)    // timeout de leitura
                .writeTimeout(30, TimeUnit.SECONDS)   // timeout de escrita
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}