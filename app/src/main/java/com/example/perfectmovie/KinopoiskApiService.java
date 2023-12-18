package com.example.perfectmovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KinopoiskApiService {
    @GET("/api/v2.2/films/premieres")
    Call<FilmsResponse> getPremiereFilms(@Query("year") int year, @Query("month") String month);

    @GET("/api/v2.2/films/collections")
    Call<FilmsResponse> getTopRatedFilms(@Query("type") String type, @Query("page") int page);

}


