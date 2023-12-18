package com.example.perfectmovie;

import android.content.Context;
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
import java.util.Locale;

public class TopRatingFilmsAdapter extends RecyclerView.Adapter<TopRatingFilmsAdapter.FilmViewHolder> {
    private List<Film> films;
    private Context context;

    public void setData(List<Film> films) {
        this.films = films;
    }

    public TopRatingFilmsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TopRatingFilmsAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_top_rating, parent, false);
        return new TopRatingFilmsAdapter.FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatingFilmsAdapter.FilmViewHolder holder, int position) {
        final Film film = films.get(position);
        holder.bind(context, film);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilmDetailsFragment(film);
            }
        });
    }

    private void openFilmDetailsFragment(Film film) {
        Log.d("FilmsAdapter", "Opening FilmDetailsFragment for film: " + film.getNameRu());
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        FilmDetailsFragment fragment = new FilmDetailsFragment();
        fragment.setFilm(film);
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
        private TextView movieRating;


        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            filmTitle = itemView.findViewById(R.id.movie_title_top_rating);
            movieCover = itemView.findViewById(R.id.movie_cover_top_rating);
            movieRating = itemView.findViewById(R.id.movie_rating_top_rating);

        }

        public void bind(Context context, Film film) {
            filmTitle.setText(film.getNameRu());

            Glide.with(context)
                    .load(film.getPosterUrl())
                    .into(movieCover);

            float rating = film.getRatingKinopoisk();
            if (rating >= 0 && rating <= 10) {
                String ratingText = String.format(Locale.US, "рейтинг: %.1f", rating);
                movieRating.setText(ratingText);
            } else {
                movieRating.setText("рейтинг: N/A");
            }
        }


    }
}
