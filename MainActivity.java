package com.killapp.imcpro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firstButton = findViewById(R.id.firstButton);
        firstButton.setOnClickListener(v -> onClickFirstButton());

        Button secondButton = findViewById(R.id.secondButton);
        secondButton.setOnClickListener(v -> onClickSecondButton());

    }

    private void onClickFirstButton() {
        Intent intent = new Intent(MainActivity.this, Calculer_IMC.class);
        startActivity(intent);
    }
    private void onClickSecondButton(){
        Intent intent = new Intent(MainActivity.this, SuiviActivity.class);
        startActivity(intent);
    }

}
