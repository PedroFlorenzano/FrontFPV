package com.example.falefacens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity3 extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//    }

    private Spinner spinner;
    String[] teste = new String[]{
            "", "teste1", "teste2", "teste3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        spinner = findViewById(R.id.spinner);

        PreencheSprinner();

        // Execute a AsyncTask para buscar os dados da API
        new FetchDataAsyncTask().execute("URL_DA_SUA_API");
    }

    private void PreencheSprinner() {

        spinner.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                teste
                ));

    }

    private void PopulaSpinner(ArrayList<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Adicione um ouvinte de item selecionado ao spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ação a ser executada quando um item é selecionado
                String selectedName = parentView.getItemAtPosition(position).toString();
                // Faça algo com o item selecionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Ação a ser executada quando nenhum item é selecionado
            }
        });
    }

    private class FetchDataAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            if (urls.length == 0) {
                return null;
            }

            String apiUrl = urls[0];

            try {
                // Faz a chamada à API e retorna a resposta como uma String JSON
                String jsonResponse = makeHttpRequest(new URL(apiUrl));

                // Extrai os nomes da resposta JSON
                return extractNamesFromJson(jsonResponse);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> data) {
            if (data != null) {
                // Preenche o Spinner com os dados obtidos da API
                PopulaSpinner(data);
            }
        }

        private String makeHttpRequest(URL url) throws IOException {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            String jsonResponse = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                if (scanner.hasNext()) {
                    jsonResponse = scanner.next();
                }
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }

            return jsonResponse;
        }

        private ArrayList<String> extractNamesFromJson(String jsonResponse) throws JSONException {
            ArrayList<String> names = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(jsonResponse);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Assumindo que o nome está em um campo chamado "name"
                String name = jsonObject.getString("name");
                names.add(name);
            }

            return names;
        }
    }
}