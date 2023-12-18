package com.example.perfectmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        loadExpectedMoviesFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.menu_expected && !(activeFragment instanceof ExpectedMoviesFragment)) {
                        selectedFragment = new ExpectedMoviesFragment();
                    } else if (item.getItemId() == R.id.menu_top_rating && !(activeFragment instanceof TopRatingFragment)) {
                        selectedFragment = new TopRatingFragment();
                    }
                    loadFragment(selectedFragment);
                    return true;
                }
            };

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            activeFragment = fragment;
        }
    }

    private void loadExpectedMoviesFragment() {
        ExpectedMoviesFragment expectedMoviesFragment = new ExpectedMoviesFragment();

        loadFragment(expectedMoviesFragment);
    }

}

