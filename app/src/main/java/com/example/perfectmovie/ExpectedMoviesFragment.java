package com.example.perfectmovie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpectedMoviesFragment extends Fragment {
    private KinopoiskApiClient kinopoiskApiClient;
    private RecyclerView recyclerView;
    private ExpectedMoviesAdapter expectedMoviesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expected_movies, container, false);

        kinopoiskApiClient = new KinopoiskApiClient();
        recyclerView = view.findViewById(R.id.recycler_view_expected_movies);
        expectedMoviesAdapter = new ExpectedMoviesAdapter(requireContext(), kinopoiskApiClient);
        recyclerView.setAdapter(expectedMoviesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadExpectedMovies();

        return view;
    }



    private void loadExpectedMovies() {
        kinopoiskApiClient.getPremiereFilms(2024, "JANUARY", new Callback<FilmsResponse>() {
            @Override
            public void onResponse(Call<FilmsResponse> call, Response<FilmsResponse> response) {
                if (response.isSuccessful()) {
                    FilmsResponse filmsResponse = response.body();
                    List<Film> expectedMovies = filmsResponse.getItems();

                    expectedMoviesAdapter.setData(expectedMovies);
                    expectedMoviesAdapter.notifyDataSetChanged();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<FilmsResponse> call, Throwable t) {
                handleFailure(t);
            }
        });
    }

    private void handleErrorResponse(Response<FilmsResponse> response) {
        try {
            String errorBody = response.errorBody().string();
            Toast.makeText(getContext(), errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка при обработке ответа", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFailure(Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
