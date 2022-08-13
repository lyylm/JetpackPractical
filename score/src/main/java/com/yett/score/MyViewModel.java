package com.yett.score;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> aTeamScore;
    private MutableLiveData<Integer> bTeamScore;
    private int oldATeamScore, oldBTeamScore;

    public MutableLiveData<Integer> getATeamScore() {
        if (aTeamScore == null) {
            aTeamScore = new MutableLiveData<>();
            aTeamScore.setValue(0);
        }
        return aTeamScore;
    }

    public MutableLiveData<Integer> getBTeamScore() {
        if (bTeamScore == null) {
            bTeamScore = new MutableLiveData<>();
            bTeamScore.setValue(0);
        }
        return bTeamScore;
    }

    public void aTeamAdd(int n) {
        oldATeamScore = aTeamScore.getValue();
        //oldBTeamScore = bTeamScore.getValue();
        aTeamScore.setValue(aTeamScore.getValue() + n);
    }

    public void bTeamAdd(int n) {
        //oldATeamScore = aTeamScore.getValue();
        oldBTeamScore = bTeamScore.getValue();
        bTeamScore.setValue(bTeamScore.getValue() + n);
    }

    public void undo() {
        aTeamScore.setValue(oldATeamScore);
        bTeamScore.setValue(oldBTeamScore);
    }

    public void reset() {
        oldATeamScore = aTeamScore.getValue();
        oldBTeamScore = bTeamScore.getValue();
        aTeamScore.setValue(0);
        bTeamScore.setValue(0);
    }
}
