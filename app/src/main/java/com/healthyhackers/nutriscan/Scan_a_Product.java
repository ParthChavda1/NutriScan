package com.healthyhackers.nutriscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Scan_a_Product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_aproduct);
    }

    public void Scanning(View v)
    {
        Intent intent=new Intent(Scan_a_Product.this, Scanning.class);
        startActivity(intent);
    }
}