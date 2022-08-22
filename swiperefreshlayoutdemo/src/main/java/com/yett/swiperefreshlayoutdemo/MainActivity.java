package com.yett.swiperefreshlayoutdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    String url1 = "https://pixabay.com/get/gef9995a01c065608235088b996a6a1769c5360388060666750873001e253a42d395841b56b41758db8aacea91cc043a7bb12476ccb99e7360067af65e0a27981_640.jpg";
    String url2 = "https://pixabay.com/get/gafbf852a1df222005dbc9f65e03fecd91fb05b08e7a3525685699e166340927c0af78fcb7e325f404192b25d102b05f7_640.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImage();
            }
        });
    }

    void loadImage(){
        Random random = new Random();
        String url = random.nextBoolean() ? url1:url2;
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }
                })
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}



























