package com.sawaaid.malltemplate.utils;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.data.AppConfig;
import com.sawaaid.malltemplate.data.DataApp;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Tools {
    public static void RTLMode(Window window) {
        if (AppConfig.RTL_LAYOUT) {
            window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public static void displayImage(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx.getApplicationContext()).load(url)
                    .transition(withCrossFade())
                    .diskCacheStrategy(DataApp.pref().getImageCache() ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .into(img);
        } catch (Exception ignored) {
        }
    }
/*
    public static void methodShare(Activity act, ProgressBar progressBar, androidx.appcompat.app.AlertDialog alertDialog) {
        try {
            Bitmap image = BitmapFactory.decodeResource(act.getResources(), R.drawable.logo_sham_trans);
            Uri uri = saveImage(image, act);
            // string to share
            String body = act.getString(R.string.app_name_share) + "\n" + act.getString(R.string.share_text);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, act.getString(R.string.app_name_share));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setType("image/*");
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            act.startActivity(Intent.createChooser(sharingIntent, "المشاركة باستخدام"));
            progressBar.setVisibility(View.INVISIBLE);
            alertDialog.dismiss();
        } catch (Exception e) {
            Toast.makeText(act, "فشل", Toast.LENGTH_LONG).show();
        }
    }

 */

    private static Uri saveImage(Bitmap image, Context context) {
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "avatar.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                    "com.smartservicescompany.shamschoolteachers" + ".provider", file);
        } catch (Throwable ignored) {

        }
        return uri;
    }

    public static void displayImageCircle(Context ctx, ImageView img, Bitmap bmp) {
        try {
            Glide.with(ctx.getApplicationContext()).load(bmp)
                    .transition(withCrossFade())
                    .diskCacheStrategy(DataApp.pref().getImageCache() ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .apply(RequestOptions.circleCropTransform())
                    .into(img);
        } catch (Exception ignored) {
        }
    }

    public static boolean isConnect(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected() || activeNetworkInfo.isConnectedOrConnecting();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static int getVersionCode(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

/*
    public static String getVersionName(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return ctx.getString(R.string.app_version) + " " + info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return ctx.getString(R.string.version_unknown);
        }
    }

 */

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    

}
