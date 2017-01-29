package com.androidcourse.socialbookstore.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class Tools
{

    public static void ShowToastMessage(String message,Context context)
    {
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

    public static void ShowDialogMessage(String message,Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}});
        alertDialog.show();
    }

    public static void ShowDialogMessageWithTitleAndGoBackAtOK(String title,String message, final Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Activity activity= (Activity)(context);
                        if(activity!=null)
                            activity.onBackPressed();
                    }
                });
        alertDialog.show();
    }

    /**
     *Here, you can load the image very easily like following.
     *new ImageDownloader(imageView).execute("Image URL will go here");
     *The image will be loaded in a thread of it's own, without delaying the UI
     *Don't forget to add the following permission into your project's Manifest.xml file:
     *<uses-permission android:name="android.permission.INTERNET" />INTERNET
     */
    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        protected ImageView imageView;
        protected String url;
        protected View loader;

        public ImageDownloader(ImageView imageView,View loader)
        {
            this.imageView = imageView;
            this.loader=loader;
        }

        protected Bitmap doInBackground(String... urls) {
            this.url = urls[0];
            if(url==null || url.isEmpty())
                return null;
            Bitmap bitmap = DataBaseFactory.getDatabase().getBitmap(url);
            if(bitmap==null)//not in cache, need to download
                try
                {
                    InputStream in = new java.net.URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    int g=0;
                }
                catch (Exception e){
                    Log.e("Error", e.getMessage());}
            return bitmap;
        }

        protected void onPostExecute(Bitmap result)
        {
            if(result==null)
                return;
            if(loader!=null)
                loader.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(result);
            DataBaseFactory.getDatabase().addBitmap(url,result);
        }
    }
}