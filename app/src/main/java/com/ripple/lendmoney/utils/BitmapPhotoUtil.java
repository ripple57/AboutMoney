package com.ripple.lendmoney.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapPhotoUtil {
    /**
     * 根据路径获取图片bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * 将图片资源转换成文件file
     * @param mBitmap
     * @param mCurrentPhotoFile
     * @return
     */
    public static File replaceMyBitmap(Bitmap mBitmap, File mCurrentPhotoFile) {
        File f = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera" + getPhotoFileName());

        try {
            f.createNewFile();
            FileOutputStream fOut = null;

            fOut = new FileOutputStream(f);

            mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);

            fOut.flush();

            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return f;
    }
    /**
     * 将图片资源转换成文件file
     * @param mBitmap
     * @param path
     * @return
     */
    public static File saveToFile(Bitmap mBitmap, String  path) {
        File f = new File(path);

        try {
            f.createNewFile();
            FileOutputStream fOut = null;

            fOut = new FileOutputStream(f);

            mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);

            fOut.flush();

            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return f;
    }
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd HH-mm-ss");
        return dateFormat.format(date) + ".jpg";
    }
    public static Intent getTakePickIntent(Context context,File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            uri = Uri.fromFile(f);
        }else{
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(context, "com.lendmoney.provider", f);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

}
