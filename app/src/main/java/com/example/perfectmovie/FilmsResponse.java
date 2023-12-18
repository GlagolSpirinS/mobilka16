package com.example.perfectmovie;

import android.provider.MediaStore;

import java.util.List;

public class FilmsResponse {
    private List<Film> items;

    public List<Film> getItems() {
        return items;
    }

    public void setItems(List<Film> items) {
        this.items = items;
    }


}