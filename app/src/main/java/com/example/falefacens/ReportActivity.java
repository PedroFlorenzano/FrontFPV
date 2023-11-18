package com.example.falefacens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.falefacens.Service.FaleFacensServices;
import com.example.falefacens.models.Categoria;
import com.example.falefacens.models.Contato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {

    private Spinner spinner;

    private TextView teste;
    private static final String TAG = "Log";

    String[] arrayNomeCategoria = new String[]{
          "",  "Secretaria", "Biblioteca", "TI", "Ouvidoria"
    };

    List<Categoria> categorias = new ArrayList<>();
    List<String> nomeCategoria = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        spinner = findViewById(R.id.spinner);

        chamarContatos();
        Log.d(TAG, categorias.toString());
        PreencheSprinner();
    }

    private void PreencheSprinner() {
        spinner.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.textviewspinner,
                arrayNomeCategoria
        ));
    }
    private void chamarContatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FaleFacensServices.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FaleFacensServices service = retrofit.create(FaleFacensServices.class);

        Call<List<Categoria>> call =service.listaCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Erro: " + response.code());
                } else {
//                  categorias.addAll(response.body());
                    categorias.add(new Categoria("Categoria", Arrays.asList(1L, 2L)));
                    nomeCategoria.addAll(categorias.stream().map(c -> c.getNome()).collect(Collectors.toList()));
                }
                Log.i(TAG, categorias.toString());
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });
        Log.d(TAG, categorias.toString());
    }
    private void enviarEmail(String titulo, String texto){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pedrohp36@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
        intent.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(intent);
    }
}