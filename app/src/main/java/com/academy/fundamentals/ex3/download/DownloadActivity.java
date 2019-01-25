package com.academy.fundamentals.ex3.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.model.MovieModel;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DownloadActivity extends AppCompatActivity {

    public static final String ARG_FILE_PATH = "ARG_FILE_PATH";
    static public String ARG_MOVIE_MODEL = "ARG_MOVIE_MODEL";
    private static final int PERMISSIONS_REQUEST_CODE = 42;

    private BroadcastReceiver broadcastReceiver;

    private ImageView imageView;

    public static void startActivity(Context context, MovieModel movieModel) {
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.putExtra(DownloadActivity.ARG_MOVIE_MODEL, movieModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        this.imageView = super.findViewById(R.id.imageView);

        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String filePath = intent.getStringExtra(ARG_FILE_PATH);
                if (!TextUtils.isEmpty(filePath)) {
                    showImage(filePath);
                }
            }
        };

        if (this.isPermissionGranted()) {
            this.downloadFile();
        } else {
            this.requestPermission();
        }

    }

    private void showImage(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        this.imageView.setImageBitmap(bitmap);
    }

    private void downloadFile() {
        MovieModel movieModel = getIntent().getParcelableExtra(DownloadActivity.ARG_MOVIE_MODEL);
        if (movieModel == null) return;

        DownloadService.startService(this, movieModel.getBackImageResourceUri());
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)) {
            showExplainingRationaleDialog(); // next slide
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.downloadFile();
        } else {
            // Show user warning and disable functionality
        }
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
        }

    }

    private void showExplainingRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Dear User");
        builder.setMessage(
                "We need access to your storage in order to download the image file you requested");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermission();
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishActivity();
            }
        });

        builder.create().show();
    }

    private void finishActivity() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }
}
