package com.example.falefacens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
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
    private EditText nome;
    private EditText ra;
    private EditText mensagem;
    private ImageButton botaoEnviar;
    private static final String TAG = "Log";

    String[] arrayNomeCategoria = new String[]{
     "", "Secretaria", "Biblioteca", "Suporte TI", "Atendimento"
    };

    List<Categoria> categorias = new ArrayList<>();
    List<String> nomeCategoria = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        InstanciaAtributos();

        chamarContatos();

        Log.d(TAG, categorias.toString());
        PreencheSprinner();

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
            }
        });

    }

    private void InstanciaAtributos(){
        spinner = findViewById(R.id.spinner);
        nome = findViewById(R.id.textNome);
        ra = findViewById(R.id.editTextRA);
        mensagem = findViewById(R.id.TextMensagem);
        botaoEnviar = findViewById(R.id.botaoEnviar);
    }
    private void PreencheSprinner() {
        spinner.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
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
    private void enviarEmail(){
        String email = null;

        switch(RecuperaParaTela()) {
            case "Secretaria":
                email = "atendimento@facens.br";
                break;
            case "Biblioteca":
                email = "biblioteca@facens.br";
                break;
            case "Suporte TI":
                email = "suporte.ti@facens.br";
                break;
            case "Atendimento":
                email = "facens@facens.br";
                break;
            default:
                email = "";
        };

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "REPORT - FALA FACENS");
        intent.putExtra(Intent.EXTRA_TEXT, MontaTextoEmail());
        startActivity(intent);
    }
    private String MontaTextoEmail(){
        String nome = RecuperaNomeTela();
        String ra = RecuperaRaTela();
        String mensagem = RecuperaMensagemTela();

        String textoEmail = nome + "\n" +
                            ra + "\n"+
                            mensagem;

        return textoEmail;
    }
    private String RecuperaParaTela(){
        String Para = spinner.getSelectedItem().toString();
        return Para;
    }
    private String RecuperaNomeTela(){
        String NomeTela = nome.getText().toString();
        return NomeTela;
    }
    private String RecuperaRaTela(){
        String RaTela = ra.getText().toString();
        return RaTela;
    }
    private String RecuperaMensagemTela(){
        String MensagemTela = mensagem.getText().toString();
        return MensagemTela;
    }

}