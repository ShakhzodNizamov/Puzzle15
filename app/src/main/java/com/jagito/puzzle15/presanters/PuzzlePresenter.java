package com.jagito.puzzle15.presanters;

import com.jagito.puzzle15.contracts.PuzzleContract;
import com.jagito.puzzle15.models.Coordinate;
import static java.lang.Math.abs;


public class PuzzlePresenter implements PuzzleContract.Presenter {
    private PuzzleContract.View view;
    private PuzzleContract.Model model;
    private Coordinate space = new Coordinate(3, 3);
    private int step;

    public PuzzlePresenter(PuzzleContract.View view, PuzzleContract.Model model) {
        this.view = view;
        this.model = model;
        restart();
    }

    @Override
    public void finish() {
        view.finishGame();
    }

    @Override
    public void restart() {
        step = 0;
        view.setScore(step);
        space = new Coordinate(3, 3);
        view.setElementText(space, "");
        view.loadData(model.getNumbers());
        view.startTimer();
    }

    @Override
    public void click(Coordinate coordinate) {

        int dx = abs(space.getX() - coordinate.getX());
        int dy = abs(space.getY() - coordinate.getY());
        if (dx + dy == 1) {
            step++;
            view.setScore(step);
            String t1 = view.getElementText(coordinate);
            view.setElementText(space, t1);
            view.setElementText(coordinate, "");
            space = coordinate;

        }
        if (isWin()) view.showWin(step);
    }

    private boolean isWin() {
        if (space.getY() != 3 || space.getX() != 3) return false;
        for (int i = 0; i < 15; i++) {
            String text = view.getElementText(new Coordinate(i / 4, i % 4));
            if (!text.equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    public Coordinate getSpace() {
        return space;
    }

    public void setSpace(Coordinate space) {
        this.space = space;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
