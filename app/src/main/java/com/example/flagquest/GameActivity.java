package com.example.flagquest;
//SA24610050 - M A S Dulneth
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ImageView ivFlag;
    private EditText etCountryGuess;
    private TextView tvScore, tvQuestionNumber, tvResult;
    private Button btnSubmit;

    private int score = 0;
    private int currentQuestion = 0;
    private final int TOTAL_QUESTIONS = 10;
    private List<Country> countries = new ArrayList<>();
    private Country currentCountry;
    private List<Integer> attempts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ivFlag = findViewById(R.id.ivFlag);
        etCountryGuess = findViewById(R.id.etCountryGuess);
        tvScore = findViewById(R.id.tvScore);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvResult = findViewById(R.id.tvResult);
        btnSubmit = findViewById(R.id.btnSubmit);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://restcountries.com/v3.1/all";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject countryObj = response.getJSONObject(i);
                            String name = countryObj.getJSONObject("name").getString("common");
                            String flagUrl = countryObj.getJSONObject("flags").getString("png");
                            String capital = countryObj.has("capital") ?
                                    countryObj.getJSONArray("capital").getString(0) : "No capital";

                            countries.add(new Country(name, flagUrl, capital));
                        }
                        startGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(GameActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show());

        queue.add(request);

        btnSubmit.setOnClickListener(v -> checkAnswer());
    }

    private void startGame() {
        score = 0;
        currentQuestion = 0;
        attempts.clear();
        loadNextQuestion();
    }

    //Refered By Deepseek
    private void loadNextQuestion() {
        if (currentQuestion >= TOTAL_QUESTIONS) {

            saveAttempt();
            showGameOverDialog();
            return;
        }

        currentQuestion++;
        tvQuestionNumber.setText(String.format("Question: %d/%d", currentQuestion, TOTAL_QUESTIONS));

        Random random = new Random();
        currentCountry = countries.get(random.nextInt(countries.size()));

        Picasso.get().load(currentCountry.getFlagUrl()).into(ivFlag);

        etCountryGuess.setText("");
        tvResult.setText("");
    }

    private void checkAnswer() {
        String userGuess = etCountryGuess.getText().toString().trim();
        if (userGuess.isEmpty()) {
            Toast.makeText(this, "Please enter a guess", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userGuess.equalsIgnoreCase(currentCountry.getName())) {
            score++;
            tvResult.setText("Correct!");
            tvResult.setTextColor(Color.GREEN);
        } else {
            score--;
            tvResult.setText("Wrong! Correct answer: " + currentCountry.getName());
            tvResult.setTextColor(Color.RED);
        }

        tvScore.setText("Score: " + score);
        new Handler().postDelayed(this::loadNextQuestion, 1500);
    }

    private void saveAttempt() {
        SharedPreferences preferences = getSharedPreferences("CountryGamePrefs", MODE_PRIVATE);
        int attemptNumber = preferences.getInt("attemptNumber", 0) + 1;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("attemptNumber", attemptNumber);

        String scoresKey = "attempt_" + attemptNumber;
        editor.putInt(scoresKey, score);
        editor.apply();
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("Your final score: " + score + "\nPlay again?")
                .setPositiveButton("Yes", (dialog, which) -> startGame())
                .setNegativeButton("No", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}