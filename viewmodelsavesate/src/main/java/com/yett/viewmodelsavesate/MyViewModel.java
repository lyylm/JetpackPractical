package com.yett.viewmodelsavesate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private SavedStateHandle handle;

    public MyViewModel(SavedStateHandle handle) {
        if (!handle.contains("NUMBER")){
            handle.set("NUMBER",0);
        }
        this.handle = handle;
    }
    public LiveData<Integer> getNumber(){
        return handle.getLiveData("NUMBER");
    }
    public void add(){
        handle.set("NUMBER",(int)handle.get("NUMBER")+1);
    }
}
