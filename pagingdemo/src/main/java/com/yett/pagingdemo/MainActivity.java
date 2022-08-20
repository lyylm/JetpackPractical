package com.yett.pagingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lyy";
    private RecyclerView recyclerView;
    private Button buttonPopulate, buttonClear;
    private MyPagedAdapter pagedAdapter;
    private LiveData<PagedList<Student>> allStudentsLivePaged;

    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        buttonClear = findViewById(R.id.buttonClear);
        buttonPopulate = findViewById(R.id.buttonPopulate);

        pagedAdapter = new MyPagedAdapter();
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(pagedAdapter);

        allStudentsLivePaged = new LivePagedListBuilder<>(studentViewModel.getAllStudents(),10).build();
        allStudentsLivePaged.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(PagedList<Student> students) {
                pagedAdapter.submitList(students);
                students.addWeakCallback(null, new PagedList.Callback() {
                    @Override
                    public void onChanged(int i, int i1) {
                        //当数据加载时触发，未能打印
                        Log.d(TAG, "onChanged: "+students.size());
                    }

                    @Override
                    public void onInserted(int i, int i1) {

                    }

                    @Override
                    public void onRemoved(int i, int i1) {

                    }
                });
            }
        });

        buttonPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student[] students = new Student[1000];
                for (int i=0;i<1000;i++){
                    students[i] = new Student(i);
                }
                studentViewModel.insertStudents(students);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentViewModel.deleteAllStudents();
            }
        });
    }
}