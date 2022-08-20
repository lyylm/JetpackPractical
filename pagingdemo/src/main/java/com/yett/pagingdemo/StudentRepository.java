package com.yett.pagingdemo;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.paging.DataSource;

public class StudentRepository {
    private StudentDao studentDao;

    public StudentRepository(Context context) {
        this.studentDao = StudentDatabase.getInstance(context).getStudentDao();
    }

    public DataSource.Factory<Integer, Student> getAllStudents(){
        return this.studentDao.getAllStudents();
    }

    public void insertStudents(Student... students){
        new InsertAsyncTask(this.studentDao).execute(students);
    }

    public void deleteAllStudents(){
        new DeleteAllAsyncTask(this.studentDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentDao studentDao;

        public InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            this.studentDao.insertStudents(students);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private StudentDao studentDao;

        public DeleteAllAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.studentDao.deleteAllStudents();
            return null;
        }
    }
}
