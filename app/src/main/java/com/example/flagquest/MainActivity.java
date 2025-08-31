package com.example.flagquest;
//SA24610121- Kahingalage T M
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("CountryGamePrefs", MODE_PRIVATE);
        String username = preferences.getString("username", "User");
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome, " + username + "!");

        findViewById(R.id.btnPlayGame).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        });

        findViewById(R.id.btnScoreBoard).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ScoreBoardActivity.class));
        });

        findViewById(R.id.btnCapitalFinder).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CapitalFinderActivity.class));
        });

        findViewById(R.id.btnSurpriseFeature).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SurpriseFeatureActivity.class));
        });
    }
}