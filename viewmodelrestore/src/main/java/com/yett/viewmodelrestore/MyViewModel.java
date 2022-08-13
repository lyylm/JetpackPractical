package com.yett.viewmodelrestore;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private static final String TAG = "lyy";
    //方式二
    private SavedStateHandle handle;
    //方式一
//    MutableLiveData<Integer> number;

    //方式二
    public MyViewModel(SavedStateHandle handle) {
        this.handle = handle;
    }


    public MutableLiveData<Integer> getNumber() {
//        //方式一
//        if (number==null){
//            number=new MutableLiveData<>();
//            number.setValue(0);
//        }
//        return number;
        //方式二
        if (!handle.contains(MainActivity.KEY_NUMBER)){
            handle.set(MainActivity.KEY_NUMBER,0);
        }
        return handle.getLiveData(MainActivity.KEY_NUMBER);
    }

    public void add(){
        //方式一
//        number.setValue(number.getValue()+1);
//        Log.d(TAG, "add: "+number.getValue());
        //方式二
        getNumber().setValue(getNumber().getValue()+1);
    }
}
