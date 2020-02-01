package com.jagito.puzzle15.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.jagito.puzzle15.R;

import java.sql.Time;


public class ResultActivity extends AppCompatActivity {
    TextView textScore;
    Chronometer textTimer;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = getIntent().getExtras();
         textScore = findViewById(R.id.result_score);
         textTimer = findViewById(R.id.result_time);

        if (bundle != null) {
            textScore.setText("SCORE: " + bundle.getInt("SCORE", 0));
            textTimer.setText(String.valueOf(bundle.getString("TIME", "--:--")));
        }

        findViewById(R.id.result_main).setOnClickListener(v -> {
            setResult(1);
            finish();
        });
        findViewById(R.id.result_restart).setOnClickListener(v -> {
            setResult(2);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("SCORE", (String) textScore.getText());
        outState.putString("TIME", (String) textTimer.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textScore.setText((String)savedInstanceState.get("SCORE"));
        textTimer.setText((String) savedInstanceState.get("TIME"));
    }
}
