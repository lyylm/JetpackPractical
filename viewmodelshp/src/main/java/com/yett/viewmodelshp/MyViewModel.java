package com.yett.viewmodelshp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {
    private final String dataKey;
    private final String shpName;
    SavedStateHandle handle;
    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        dataKey=application.getResources().getString(R.string.data_key);
        shpName=application.getResources().getString(R.string.shp_name);
        this.handle=handle;
        if (!handle.contains(dataKey)){
            load();
        }
    }

    public void add(int x){
        handle.set(dataKey, getInt()+x);
        //save();//这个比较耗时和消耗资源；不建议进行频繁的保存
    }

    public LiveData<Integer> getNumber(){
        return handle.getLiveData(dataKey);
    }

    private void load(){
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        int x = shp.getInt(dataKey,0);
        handle.set(dataKey,x);
    }

    void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(shpName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(dataKey,getInt());
        editor.apply();
    }

    private int getInt(){
        return getNumber().getValue()==null ? 0 : getNumber().getValue();
    }
}
