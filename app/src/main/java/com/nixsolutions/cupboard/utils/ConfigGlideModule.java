package com.nixsolutions.cupboard.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;

public class ConfigGlideModule implements GlideModule {

    private static final int BUFF_SIZE_BYTES = 100000000;

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
                .setDiskCache(new DiskCache.Factory() {
                    @Override
                    public DiskCache build() {
                        return DiskLruCacheWrapper.get(context.getExternalCacheDir(), BUFF_SIZE_BYTES);
                    }
                });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
