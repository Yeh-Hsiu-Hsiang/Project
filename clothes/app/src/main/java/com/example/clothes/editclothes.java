package com.example.clothes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class editclothes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editclothes);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> clothesList = ArrayAdapter.createFromResource(editclothes.this,
                R.array.clothesclass, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(clothesList);
    }
}
