package com.yett.viewmodelrestore;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private static final String TAG = "lyy";

    MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber() {
        if (number==null){
            number=new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public void add(){
        number.setValue(number.getValue()+1);
        Log.d(TAG, "add: "+number.getValue());
    }
}
