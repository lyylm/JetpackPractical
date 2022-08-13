package com.example.sharepreferencebase;

import android.content.Context;
import android.content.SharedPreferences;

public class MyData {
    private String shp_file_name,shp_data_key;
    private int defValue;

    public int number;
    private Context context;

    public MyData(Context context) {
        this.context = context;
        shp_file_name = context.getResources().getString(R.string.MY_DATA);
        shp_data_key = context.getResources().getString(R.string.MY_KEY);
        defValue = context.getResources().getInteger(R.integer.defValue);
    }

    public void save(){
        SharedPreferences shp = context.getSharedPreferences(shp_file_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(shp_data_key, number);
        editor.apply();
    }

    public int load(){
        SharedPreferences shp = context.getSharedPreferences(shp_file_name, Context.MODE_PRIVATE);
        return shp.getInt(shp_data_key,defValue);
    }
}
