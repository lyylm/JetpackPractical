package com.yett.navigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

/**
 * 导航的初步使用：
 * NavGraph的使用，进行快速的实现导航
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //添加上部导航显示
//        NavController controller = Navigation.findNavController(this, R.id.fragmentContainerView);
//        NavigationUI.setupActionBarWithNavController(this,controller);
        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController controller = hostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,controller);
    }

    //上部导航栏的返回按钮起作用
    @Override
    public boolean onSupportNavigateUp() {
        //return super.onSupportNavigateUp();
        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController controller = hostFragment.getNavController();
        return controller.navigateUp();
    }
}