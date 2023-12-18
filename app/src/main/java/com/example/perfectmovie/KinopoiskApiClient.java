package com.example.perfectmovie;

import android.provider.MediaStore;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KinopoiskApiClient implements Serializable {
    private static String BASE_URL = "https://kinopoiskapiunofficial.tech";
    public static String TOP_250_MOVIES = "TOP_250_MOVIES";

    private KinopoiskApiService apiService;

    public KinopoiskApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();

        apiService = retrofit.create(KinopoiskApiService.class);
    }



    public void getPremiereFilms(int year, String month, Callback<FilmsResponse> callback) {
        Call<FilmsResponse> call = apiService.getPremiereFilms(year, month);
        call.enqueue(callback);
    }

    public void getTopRatingMovies(Callback<FilmsResponse> callback) {
        Call<FilmsResponse> call = apiService.getTopRatedFilms(TOP_250_MOVIES, 1);
        call.enqueue(callback);
    }


    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("X-API-KEY", "37e0e23a-16ba-4116-8ffc-56409eac7946")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
