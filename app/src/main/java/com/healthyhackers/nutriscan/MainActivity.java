package com.healthyhackers.nutriscan;//package com.healthyhackers.nutriscan;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.FileProvider;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//
//import java.io.File;
//
//public class MainActivity extends AppCompatActivity {
//
//    public void Scan_a_product(View v)
//    {
//        Intent intent=new Intent(MainActivity.this, Scan_a_Product.class);
//        startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//    }
//}
import static com.healthyhackers.nutriscan.R.id.captureButton;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private ImageView imageView;
    private ImageView imageASD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button captureButton = findViewById(R.id.captureButton);


        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitMap = (Bitmap)data.getExtras().get("data");
            Uri uri = getImageUri(this,imageBitMap);

            Intent intent = new Intent(this, Scanning.class);
            intent.putExtra("imageBitmap",imageBitMap);
            startActivity(intent);
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }


}
