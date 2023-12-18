package com.example.perfectmovie;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilmDetailsFragment extends Fragment {
    private Film film;
    private KinopoiskApiClient kinopoiskApiClient;


    public FilmDetailsFragment() {

    }

    public FilmDetailsFragment(KinopoiskApiClient kinopoiskApiClient) {
        this.kinopoiskApiClient = kinopoiskApiClient;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film_details, container, false);

        ImageView movieCover = view.findViewById(R.id.film_poster);
        TextView movieTitle = view.findViewById(R.id.film_title);
        TextView movieReleaseDate = view.findViewById(R.id.film_release_date);
        TextView movieDescription = view.findViewById(R.id.film_description);
        TextView movieRating = view.findViewById(R.id.film_rating);



        if (film != null) {
            Log.d("FilmDetailsFragment", "Film details: " + film.getNameRu());
            Glide.with(requireContext()).load(film.getPosterUrl()).into(movieCover);
            movieTitle.setText(film.getNameRu());
            movieReleaseDate.setText(film.getPremiereRu());

            movieReleaseDate.setText("Дата релиза: " + film.getPremiereRu());

            float rating = film.getRatingKinopoisk();
            if (rating >= 0 && rating <= 10) {
                String ratingText = String.format(Locale.US, "рейтинг: %.1f", rating);
                movieRating.setText(ratingText);
            } else {
                movieRating.setText("рейтинг: N/A");
            }



        } else {
            Log.e("FilmDetailsFragment", "Film is null.");
        }
        return view;
    }


}

