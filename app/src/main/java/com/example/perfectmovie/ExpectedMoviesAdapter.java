package com.example.perfectmovie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class ExpectedMoviesAdapter extends RecyclerView.Adapter<ExpectedMoviesAdapter.FilmViewHolder> {
    private List<Film> films;
    private Context context;
    private KinopoiskApiClient kinopoiskApiClient;

    public void setData(List<Film> films) {
        this.films = films;

    }

    public ExpectedMoviesAdapter(Context context, KinopoiskApiClient kinopoiskApiClient) {
        this.context = context;
        this.kinopoiskApiClient = kinopoiskApiClient;
    }

    @NonNull
    @Override
    public ExpectedMoviesAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_expected_movies, parent, false);
        return new ExpectedMoviesAdapter.FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpectedMoviesAdapter.FilmViewHolder holder, int position) {
        final Film film = films.get(position);
        holder.bind(context, film);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilmDetailsFragment(film);
            }
        });

        holder.filmReleaseDate.setText("Дата релиза: " + film.getPremiereRu());
    }

    private void openFilmDetailsFragment(Film film) {
        Log.d("FilmsAdapter", "Opening FilmDetailsFragment for film: " + film.getNameRu());
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();

        FilmDetailsFragment fragment = new FilmDetailsFragment(kinopoiskApiClient);
        fragment.setFilm(film);

        Bundle args = new Bundle();
        args.putSerializable("kinopoiskApiClient", kinopoiskApiClient);
        fragment.setArguments(args);

        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public int getItemCount() {
        if (films != null) {
            return films.size();
        } else {
            return 0;
        }
    }

    static class FilmViewHolder extends RecyclerView.ViewHolder {
        private TextView filmTitle;
        private ImageView movieCover;
        private TextView filmReleaseDate;


        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            filmTitle = itemView.findViewById(R.id.movie_title_expected);
            movieCover = itemView.findViewById(R.id.movie_cover_expected);
            filmReleaseDate = itemView.findViewById(R.id.movie_release_date_expected);

        }

        public void bind(Context context, Film film) {
            filmTitle.setText(film.getNameRu());

            TextView movieReleaseDate = itemView.findViewById(R.id.movie_release_date_expected);
            movieReleaseDate.setText(film.getPremiereRu());

            Glide.with(context)
                    .load(film.getPosterUrl())
                    .into(movieCover);

        }
    }
}
