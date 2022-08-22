package com.yett.serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yett.serializable.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "myFile.data";
    private static final String TAG = "lyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int math = Integer.parseInt(binding.editTextMath.getText().toString().trim());
                int chinese = Integer.parseInt(binding.editTextChinese.getText().toString().trim());
                int english = Integer.parseInt(binding.editTextEnglish.getText().toString().trim());
                int age = Integer.parseInt(binding.editTextAge.getText().toString().trim());
                String name = binding.editTextName.getText().toString().trim();
                Student student = new Student(name,age,new Score(math,english,chinese));

                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput(FILE_NAME,MODE_PRIVATE));
                    objectOutputStream.writeObject(student);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    Toast.makeText(MainActivity.this,"Data saved!",Toast.LENGTH_SHORT).show();
                    binding.editTextName.getText().clear();
                    binding.editTextAge.getText().clear();
                    binding.editTextMath.getText().clear();
                    binding.editTextEnglish.getText().clear();
                    binding.editTextChinese.getText().clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(openFileInput(FILE_NAME));
                    try {
                        Student student = (Student) objectInputStream.readObject();
                        binding.editTextName.setText(student.getName());
                        binding.editTextAge.setText(String.valueOf(student.getAge()));
                        binding.editTextChinese.setText(String.valueOf(student.getScore().getChinese()));
                        binding.editTextEnglish.setText(String.valueOf(student.getScore().getEnglish()));
                        binding.editTextMath.setText(String.valueOf(student.getScore().getMath()));
                        binding.textViewGrade.setText(String.valueOf(student.getScore().getGrade()));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
















