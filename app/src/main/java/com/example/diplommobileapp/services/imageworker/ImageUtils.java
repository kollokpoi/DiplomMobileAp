package com.example.diplommobileapp.services.imageworker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageUtils {
    public static void setImageViewFromByteArray(byte[] byteArray, ImageView imageView) {
        if (byteArray != null && byteArray.length > 0) {
            new LoadImageTask(imageView).execute(byteArray);
        }
    }

    private static class LoadImageTask extends AsyncTask<byte[], Void, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(byte[]... params) {
            byte[] byteArray = params[0];
            if (byteArray != null && byteArray.length > 0) {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
