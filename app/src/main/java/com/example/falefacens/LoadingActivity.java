package com.example.falefacens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main); // Crie um layout para a tela de abertura

    // Handler para aguardar alguns segundos e iniciar a MainActivity
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent mainIntent = new Intent(LoadingActivity.this, MenuActivity.class);
            startActivity(mainIntent);
            finish(); // Fecha a SplashActivity para que não possa ser retornada ao pressionar o botão Voltar
        }
    }, 3000); // Tempo em milissegundos (3 segundos neste exemplo)
}
}