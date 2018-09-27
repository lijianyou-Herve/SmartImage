package com.herve.library.smartimage.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.WorkerThread;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BitmapSlicerManager {

    private BitmapSlicerListener mBitmapSlicerListener;

    public static BitmapSlicerManager create() {
        return new BitmapSlicerManager();
    }

    public Disposable doSlicer(File outPutFile) {
        try {
            InputStream is = new FileInputStream(outPutFile);
            Bitmap srcBitmap = BitmapFactory.decodeStream(is);
            is.close();
            outPutFile.delete();
            return Observable.just(srcBitmap)
                    .map(new Function<Bitmap, List<Bitmap>>() {
                        @Override
                        public List<Bitmap> apply(Bitmap bitmap) throws Exception {
                            return doSlice(bitmap);
                        }
                    })
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {

                        }
                    })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Bitmap>>() {
                        @Override
                        public void accept(List<Bitmap> bitmaps) throws Exception {
                            if (mBitmapSlicerListener != null) {
                                if (bitmaps != null) {
                                    mBitmapSlicerListener.onSlicerSuccess(bitmaps);
                                } else {
                                    mBitmapSlicerListener.onSlicerFailed("图片生成失败！");
                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            if (mBitmapSlicerListener != null) {
                                mBitmapSlicerListener.onSlicerFailed("裁剪失败！");
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WorkerThread
    private List<Bitmap> doSlice(Bitmap srcBitmap) {
        int srcW = srcBitmap.getWidth();
        int srcH = srcBitmap.getHeight();
        int hNum = mBitmapSlicerListener.getHorizontalPicNumber();
        int vNum = mBitmapSlicerListener.getVerticalPicNumber();
        int picW = srcW / hNum;
        int picH = srcH / vNum;
//        int dividerW = srcW * PIC_DIVIDER_LEN / mAspectX;
//        int dividerH = srcH * PIC_DIVIDER_LEN / mAspectY;
        List<Bitmap> bitmaps = new ArrayList<>();
        for (int j = 0; j < vNum; j++) {
            for (int i = 0; i < hNum; i++) {
                Bitmap bitmap = Bitmap.createBitmap(srcBitmap, picW * i, picH * j, picW, picH);
                bitmaps.add(bitmap);
            }
        }
        return bitmaps;
    }

    public BitmapSlicerManager setBitmapSlicerListener(BitmapSlicerListener bitmapSlicerListener) {
        this.mBitmapSlicerListener = bitmapSlicerListener;
        return this;
    }

    public interface BitmapSlicerListener {
        /**
         * @return 横向图片个数
         */
        int getHorizontalPicNumber();

        /**
         * @return 纵向图片个数
         */
        int getVerticalPicNumber();

        void onSlicerSuccess(List<Bitmap> bitmaps);

        void onSlicerFailed(String message);
    }
}
