package com.yett.pagingdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.paging.DataSource;

public class StudentViewModel extends AndroidViewModel {
    StudentRepository studentRepository;
    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
    }

    public DataSource.Factory<Integer,Student> getAllStudents(){
        return studentRepository.getAllStudents();
    }

    public void insertStudents(Student... students){
        this.studentRepository.insertStudents(students);
    }

    public void deleteAllStudents(){
        this.studentRepository.deleteAllStudents();
    }
}
