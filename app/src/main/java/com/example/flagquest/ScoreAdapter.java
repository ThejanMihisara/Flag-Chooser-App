package com.example.flagquest;
//SA24610051 - J N Arawwawala
// Refered By Deepseek
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;


public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private List<Integer> scores;
    private int highestScorePosition = -1;

    public ScoreAdapter(List<Integer> scores) {
        this.scores = scores;
        findHighestScore();
    }

    private void findHighestScore() {
        if (scores.isEmpty()) return;

        int max = Collections.max(scores);
        highestScorePosition = scores.indexOf(max);
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        int attemptNumber = position + 1;
        int score = scores.get(position);

        holder.tvAttempt.setText("Attempt " + attemptNumber);
        holder.tvScore.setText("Score: " + score);

        // Highlight highest score
        if (position == highestScorePosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E3F2FD")); // Light blue
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvAttempt, tvScore;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttempt = itemView.findViewById(R.id.tvAttempt);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}