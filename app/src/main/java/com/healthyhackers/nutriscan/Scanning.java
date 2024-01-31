package com.healthyhackers.nutriscan;// package com.healthyhackers.nutriscan;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Scanning extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private static final int PROGRESS_UPDATE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        Bitmap bitmap = getIntent().getParcelableExtra("imageBitmap");
        imageView = findViewById(R.id.imageASD);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView2);


        imageView.setImageBitmap(bitmap);
        // Set initial visibility of progress bar and text
        progressBar.setVisibility(ProgressBar.VISIBLE);
        textView.setText("NutriScan is analyzing the ingredients");

        // Simulate a task that takes 5 seconds
        simulateTask();
    }

    private void simulateTask() {
        final int totalProgressTime = 5000; // 5 seconds

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == PROGRESS_UPDATE) {
                    progressBar.setProgress(msg.arg1);
                    return true;
                }
                return false;
            }
        });

        new Thread(new Runnable() {
            public void run() {
                int progress = 0;
                int interval = totalProgressTime / 100;
                while (progress <= 100) {
                    try {
                        Thread.sleep(interval);
                        progress++;
                        Message msg = new Message();
                        msg.what = PROGRESS_UPDATE;
                        msg.arg1 = progress;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Hide progress bar after 5 seconds
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(ProgressBar.GONE);
                        textView.setText("Analysis complete");
                    }
                });
            }
        }).start();
    }
}
