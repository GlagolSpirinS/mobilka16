package com.example.perfectmovie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopRatingFragment extends Fragment {
    private KinopoiskApiClient kinopoiskApiClient;
    private RecyclerView recyclerView;
    private TopRatingFilmsAdapter topRatingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rating, container, false);

        kinopoiskApiClient = new KinopoiskApiClient();
        recyclerView = view.findViewById(R.id.recycler_view_top_rating);
        topRatingAdapter = new TopRatingFilmsAdapter(requireContext());
        recyclerView.setAdapter(topRatingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        Log.d("TopRatingFragment", "Loading top rating movies...");

        loadTopRatingMovies();

        return view;
    }

    private void loadTopRatingMovies() {
        kinopoiskApiClient.getTopRatingMovies(new Callback<FilmsResponse>() {
            @Override
            public void onResponse(Call<FilmsResponse> call, Response<FilmsResponse> response) {
                Log.d("TopRatingFragment", "Response received.");
                if (response.isSuccessful()) {
                    FilmsResponse filmsResponse = response.body();
                    List<Film> topRatingMovies = filmsResponse.getItems();
                    topRatingAdapter.setData(topRatingMovies);
                    topRatingAdapter.notifyDataSetChanged();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("TopRatingFragment", "Error response: " + errorBody);
                        Toast.makeText(getContext(), errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("TopRatingFragment", "Error processing response.");
                        Toast.makeText(getContext(), "Ошибка при обработке ответа", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<FilmsResponse> call, Throwable t) {
                Log.e("TopRatingFragment", "Request failed: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
