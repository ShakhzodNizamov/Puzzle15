package com.jagito.puzzle15.models;

import com.jagito.puzzle15.contracts.PuzzleContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class PuzzleRepository implements PuzzleContract.Model {
    private ArrayList<Integer> list = new ArrayList<>();

    public PuzzleRepository() {
        for (int i = 1; i < 16; i++) {
            list.add(i);
        }
    }

    @Override
    public List<Integer> getNumbers() {

        //Collections.shuffle(list);
       return checkShuffle(list);

    }
    private List<Integer> checkShuffle(List<Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(i) > list.get(j))
                    sum ++;
            }
        }
        if (sum % 2 == 0) {
            return list;
        }
        return checkShuffle(getNumbers());
    }
}
