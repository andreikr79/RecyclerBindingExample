package com.a256devs.recyclerbindingexample.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a256devs.recyclerbindingexample.R;
import com.a256devs.recyclerbindingexample.databinding.ActivityMainBinding;
import com.a256devs.recyclerbindingexample.di.App;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getMainComponent().inject(this);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
