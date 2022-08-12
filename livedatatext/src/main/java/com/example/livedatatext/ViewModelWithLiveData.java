package com.example.livedatatext;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelWithLiveData extends ViewModel {
    private MutableLiveData<Integer> likedNumber;

    public MutableLiveData<Integer> getLikedNumber() {
        if (likedNumber == null){
            likedNumber = new MutableLiveData<>();
            likedNumber.setValue(0);
        }
        return likedNumber;
    }

    public void addLikedNumber(int n){
        likedNumber.setValue(likedNumber.getValue()+n);
    }
}
