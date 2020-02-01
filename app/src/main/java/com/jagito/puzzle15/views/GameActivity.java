package com.jagito.puzzle15.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jagito.puzzle15.R;
import com.jagito.puzzle15.contracts.PuzzleContract;
import com.jagito.puzzle15.models.Coordinate;
import com.jagito.puzzle15.models.PuzzleRepository;
import com.jagito.puzzle15.presanters.PuzzlePresenter;

import java.sql.Time;
import java.util.List;



public class GameActivity extends AppCompatActivity implements PuzzleContract.View {
    private PuzzleContract.Presenter presenter;
    private Button[][] buttons;
    private String[] strings;
    private TextView textScore;
    private Chronometer textTimer;
    private int SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("Game");
        loadViews();
        presenter = new PuzzlePresenter(this, new PuzzleRepository());

    }

    private void loadViews() {
        findViewById(R.id.buttonToBack).setOnClickListener(v -> presenter.finish());
        findViewById(R.id.buttonRestart).setOnClickListener(v -> presenter.restart());
        textScore = findViewById(R.id.textScore);
        textTimer = findViewById(R.id.textTimer);
        ViewGroup group = findViewById(R.id.group);
        SIZE = 4;
        strings = new String[SIZE*SIZE];
        buttons = new Button[4][4];
        for (int i = 0; i < group.getChildCount(); i++) {
            Button button = (Button) group.getChildAt(i);
            buttons[i / SIZE][i % SIZE] = button;
            button.setOnClickListener(v -> presenter.click((Coordinate) v.getTag()));
            button.setTag(new Coordinate(i / SIZE, i % SIZE));
        }
    }


    @Override
    public void finishGame() {
        finish();
    }

    @Override
    public void loadData(List<Integer> data) {
        for (int i = 0; i < data.size(); i++) {
            buttons[i / SIZE][i % SIZE].setText(String.valueOf(data.get(i)));
        }
    }

    @Override
    public void setElementText(Coordinate coordinate, String s) {
        buttons[coordinate.getX()][coordinate.getY()].setText(s);
    }

    @Override
    public String getElementText(Coordinate coordinate) {
        return buttons[coordinate.getX()][coordinate.getY()].getText().toString();
    }

    @Override
    public void setScore(int score) {
        textScore.setText(String.valueOf(score));
    }

    @Override
    public void showWin(int score) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TIME", textTimer.getText());
        startActivityForResult(intent,1);
    }

    @Override
    public void startTimer() {
        textTimer.stop();
        textTimer.setBase(SystemClock.elapsedRealtime());
        textTimer.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            finish();
        }
        if (resultCode == 2) {
            presenter = new PuzzlePresenter(this, new PuzzleRepository());
            presenter.restart();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < SIZE*SIZE; i++) {
            strings[i]= String.valueOf(buttons[i / SIZE][i % SIZE].getText());
        }
        outState.putStringArray("BUTTONS",strings);
        outState.putLong("TIME",  textTimer.getBase());
        outState.putString("SCORE", (String) textScore.getText());
        outState.putInt("SPACE_X",presenter.getSpace().getX());
        outState.putInt("SPACE_Y",presenter.getSpace().getY());
        outState.putInt("STEP",presenter.getStep());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            textTimer.setBase((Long) savedInstanceState.get("TIME"));
            textTimer.start();
            textScore.setText((String) savedInstanceState.get("SCORE"));
            strings = (String[]) savedInstanceState.get("BUTTONS");
            for (int i = 0; i < SIZE*SIZE; i++) {
                buttons[i / SIZE][i % SIZE].setText(strings[i]);
                Log.d("LOL",strings[i]);
            }
            int x = (int) savedInstanceState.get("SPACE_X");
            int y = (int) savedInstanceState.get("SPACE_Y");
            int step = (int) savedInstanceState.get("STEP");
            presenter.setSpace(new Coordinate(x,y));
            presenter.setStep(step);
        }
    }
}