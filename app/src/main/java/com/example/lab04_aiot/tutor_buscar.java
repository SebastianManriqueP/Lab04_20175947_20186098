package com.example.lab04_aiot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import com.example.lab04_aiot.model.Employee;
import com.example.lab04_aiot.repositroy.ApiServiceTutor;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tutor_buscar extends AppCompatActivity {
    private ApiServiceTutor apiService;

    private EditText codigoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_buscar);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Obtener instancia de ApiService
        apiService = retrofit.create(ApiServiceTutor.class);

        codigoEditText = findViewById(R.id.codigoEditText);

        Button buscarButton = findViewById(R.id.buscarButton);

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = codigoEditText.getText().toString();

                buscarTrabajador(codigo);
            }
        });
    }

    private void buscarTrabajador(String id) {
        Call<String> call = apiService.buscarTrabajador(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Guardar el archivo en el almacenamiento local
                    saveFile(response.body(), "informacionDe" + id + ".txt");
                    Toast.makeText(tutor_buscar.this, "Archivo descargado y guardado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(tutor_buscar.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(tutor_buscar.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void saveFile(String body, String fileName) {
        try {
            File file = new File(getExternalFilesDir(null), fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(body);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(tutor_buscar.this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

}