package com.jagito.puzzle15.contracts;

import com.jagito.puzzle15.models.Coordinate;

import java.util.List;



public interface PuzzleContract {
    interface Model {
        List<Integer> getNumbers();
    }

    interface View {
        void finishGame();
        void loadData(List<Integer> data);

        void setElementText(Coordinate coordinate, String s);

        String getElementText(Coordinate coordinate);

        void setScore(int score);

        void showWin(int score);

        void startTimer();
    }

    interface Presenter {

        void finish();

        void restart();

        void click(Coordinate coordinate);

        Coordinate getSpace();
        void setSpace(Coordinate space);
        int getStep();
        void setStep(int step);
    }
}
