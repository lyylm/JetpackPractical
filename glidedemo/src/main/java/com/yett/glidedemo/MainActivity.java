package com.yett.glidedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageView1 = findViewById(R.id.imageView2);
        Button button = findViewById(R.id.button);
        //加载图片，不采用第三方方式加载图片
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://pixabay.com/get/gef9995a01c065608235088b996a6a1769c5360388060666750873001e253a42d395841b56b41758db8aacea91cc043a7bb12476ccb99e7360067af65e0a27981_640.jpg";

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                ImageLoader imageLoader =new ImageLoader(queue, new ImageLoader.ImageCache() {
                    //添加缓存
                    private LruCache<String,Bitmap> cache = new LruCache<>(50);
                    @Nullable
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);//从缓存中取出
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url,bitmap);//放入缓存
                    }
                });
                imageLoader.get(url, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        imageView.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                    }
                });
            }
        });
        //glide的方式加载图片
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://pixabay.com/get/gef9995a01c065608235088b996a6a1769c5360388060666750873001e253a42d395841b56b41758db8aacea91cc043a7bb12476ccb99e7360067af65e0a27981_640.jpg";

                Glide.with(MainActivity.this)
                        .load(url)
                        .placeholder(R.drawable.ic_launcher_background)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(imageView1);
            }
        });
    }
}









