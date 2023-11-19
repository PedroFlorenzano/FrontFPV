package com.example.falefacens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class ContatosActivity extends AppCompatActivity {

    private EditText nome;
    private EditText ra;
    private EditText telefone;
    private EditText mensagem;
    private ImageButton botaoEnviar;
    private Spinner spinner;

    String[] arrayContato = new String[]{
            "", "Coordenador ADS", "LIGA", "LINCE", "EAD"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        InstanciaAtributos();
        PreencheSprinner();

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
            }
        });
    }

    private void InstanciaAtributos(){
        spinner = findViewById(R.id.spinner2);
        nome = findViewById(R.id.TextNomeContatos);
        ra = findViewById(R.id.TextRaContato);
        mensagem = findViewById(R.id.TextMsgContato);
        telefone = findViewById(R.id.TextTelefone);
        botaoEnviar = findViewById(R.id.botaoEnviar);
    }
    private void PreencheSprinner() {
        spinner.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrayContato
        ));
    }
    private void enviarEmail(){
        String email = null;

        switch(RecuperaParaTela()) {
            case "Coordenador ADS":
                email = "eliney.sabino@facens.br";
                break;
            case "LIGA":
                email = "liga@facens.br";
                break;
            case "LINCE":
                email = "lince@facens.br";
                break;
            case "EAD":
                email = "ead@facens.br";
                break;
            default:
                email = "";
        };

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "CONTATO - FALA FACENS");
        intent.putExtra(Intent.EXTRA_TEXT, MontaTextoEmail());
        startActivity(intent);
    }
    private String MontaTextoEmail(){
        String nome = RecuperaNomeTela();
        String ra = RecuperaRaTela();
        String mensagem = RecuperaMensagemTela();
        String telefone = RecuperaTelefoneTela();

        String textoEmail = nome + "\n" +
                ra + "\n"+
                telefone + "\n"+
                mensagem;

        return textoEmail;
    }
    private String RecuperaParaTela(){
        String Para = spinner.getSelectedItem().toString();
        return Para;
    }
    private String RecuperaTelefoneTela(){
        String Telefone = telefone.getText().toString();
        return Telefone;
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