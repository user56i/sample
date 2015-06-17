package com.nixsolutions.cupboard.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.nixsolutions.cupboard.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GlideActivity extends ActionBarActivity {

    private static final String URL = "http://bm.img.com.ua/berlin/storage/orig/4/ce/a79e019e160eb5ee45e6a8da85f9cce4.jpg";
    @InjectView(R.id.image_1)
    ImageView image1;
    @InjectView(R.id.image_2)
    ImageView image2;
    @InjectView(R.id.image_3)
    ImageView image3;
    @InjectView(R.id.image_4)
    ImageView image4;
    @InjectView(R.id.image_5)
    ImageView image5;
    @InjectView(R.id.image_6)
    ImageView image6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.inject(this);
        final RequestManager requestManager = null;//= Glide.with(GlideActivity.this)

        Toast.makeText(this, "l", Toast.LENGTH_LONG).show();

        final DiskCacheStrategy strategy = DiskCacheStrategy.ALL;
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestManager.load(URL).diskCacheStrategy(strategy).into(image6);
            }
        });
        BitmapPool bitmapPool = Glide.get(this).getBitmapPool();

//        requestManager.load(URL).diskCacheStrategy(strategy).bitmapTransform(new CropCircleTransformation(bitmapPool)).into(image3);
//        requestManager.load(URL).diskCacheStrategy(strategy).bitmapTransform(new InvertFilterTransformation(this, bitmapPool)).into(image4);
//        requestManager.load(URL).diskCacheStrategy(strategy).bitmapTransform(new BlurTransformation(this, bitmapPool, 5)).into(image5);
//        requestManager.load(URL).diskCacheStrategy(strategy).bitmapTransform(new ColorFilterTransformation(bitmapPool, Color.argb(128, 0, 0, 255))).into(image2);
        requestManager.load(URL).diskCacheStrategy(strategy).into(image1);

    }


}
