package com.example.flagquest;
//SA24610051 - J N Arawwawala
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity {
    private RecyclerView rvScores;
    private ScoreAdapter adapter;
    private List<Integer> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        rvScores = findViewById(R.id.rvScores);
        rvScores.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = getSharedPreferences("CountryGamePrefs", MODE_PRIVATE);
        int attemptNumber = preferences.getInt("attemptNumber", 0);

        for (int i = 1; i <= attemptNumber; i++) {
            String key = "attempt_" + i;
            int score = preferences.getInt(key, 0);
            scores.add(score);
        }

        adapter = new ScoreAdapter(scores);
        rvScores.setAdapter(adapter);
    }
}