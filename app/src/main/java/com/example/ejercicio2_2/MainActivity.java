package com.example.ejercicio2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnRetrofit;
    Intent intentNavegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListeners();
    }

    private void init(){

        intentNavegar = null;

        btnRetrofit = (Button) findViewById(R.id.btnfit);

    }

    private void setListeners(){
        btnRetrofit.setOnClickListener(v -> irRetrofit());

    }

    private void irRetrofit(){

        intentNavegar = new Intent(getApplicationContext(), ActivityRetrofit.class);
        startActivity(intentNavegar);
    }


}