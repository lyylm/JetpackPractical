package com.example.parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parcelable.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString().trim();
                int age = Integer.parseInt(binding.editTextAge.getText().toString().trim());
                int math = Integer.parseInt(binding.editTextMath.getText().toString().trim());
                int english = Integer.parseInt(binding.editTextEnglish.getText().toString().trim());

                Student student = new Student(name,age,new Score(math,english));

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("student", student);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
    }
}