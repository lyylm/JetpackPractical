package com.example.viewmodeltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 该模块是进行Jetpack中ViewModel的练习，其中包括ViewModel的数据保活功能
 */
public class MainActivity extends AppCompatActivity {
    MyViewModel myViewModel;

    TextView textView;
    Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        textView = findViewById(R.id.textViewNumber);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        textView.setText(String.valueOf(myViewModel.number));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.number++;
                textView.setText(String.valueOf(myViewModel.number));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.number--;
                textView.setText(String.valueOf(myViewModel.number));
            }
        });
    }
}




