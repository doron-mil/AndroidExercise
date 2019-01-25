package com.academy.fundamentals.ex3.download;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DownloadThread extends Thread {
    private final String imageUrl;
    private final DownloadCallBack mDownloadCallBack;
    private long mLastUpdateTime;
    private int mProgress;

    public DownloadThread(String url, DownloadCallBack downloadCallBack) {
        imageUrl = url;
        this.mDownloadCallBack = downloadCallBack;
    }

    @Override
    public void run() {
        this.mLastUpdateTime = 0 ;
        this.mProgress = 0 ;

        File file = this.createFile();
        if (file == null) {
            mDownloadCallBack.onError("Can't create file");
            return;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        int fileLength = 0;

        URL url = null;
        try {
            url = new URL(this.imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                mDownloadCallBack.onError("Server returned HTTP response code: "
                        + connection.getResponseCode() + " - " + connection.getResponseMessage());
            }
            fileLength = connection.getContentLength();

            // Input stream (Downloading file)
            inputStream = new BufferedInputStream(url.openStream(), 8192);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Output stream (Saving file)
        try {
            fos = new FileOutputStream(file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            int next;
            byte[] data = new byte[1024];
            while ((next = inputStream.read(data)) != -1 && fileLength > 0 ) {
                fos.write(data, 0, next);
                // Log.e("doron - ddd", "" + fos.getChannel().size() + "/" +fileLength);
                this.updateProgress(fos, fileLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        mDownloadCallBack.onDownloadFinished(file.getPath());
    }

    private void updateProgress(FileOutputStream fos, int fileLength) throws IOException{
        if (this.mLastUpdateTime == 0 || System.currentTimeMillis() > this.mLastUpdateTime + 20) {
            int count = ((int) fos.getChannel().size()) * 100 / fileLength;
            if (count > this.mProgress  ) {
                this.mProgress = count;
                this.mLastUpdateTime = System.currentTimeMillis();
                this.mDownloadCallBack.onProgressUpdate(this.mProgress);
            }
        }

    }

    private File createFile() {
        File mediaStorageDirectory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String imageName = this.createImageFileName() + ".jpg";
        return new File(mediaStorageDirectory.getPath() + File.separator + imageName);
    }

    @NonNull
    private String createImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.US).format(new Date());
        return "MOVIE_IMG_" + timeStamp;
    }

    public interface DownloadCallBack {
        void onProgressUpdate(int percent);

        void onDownloadFinished(String filePath);

        void onError(String error);
    }
}
