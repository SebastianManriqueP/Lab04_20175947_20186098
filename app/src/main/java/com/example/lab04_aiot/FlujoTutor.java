package com.example.lab04_aiot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab04_aiot.repositroy.ApiServiceTutor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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
public class FlujoTutor extends AppCompatActivity {
    private ApiServiceTutor apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiServiceTutor.class);

        Button descargarButton = findViewById(R.id.descargarButton);



        descargarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarListaTrabajadores();
            }
        });

        Button botonBuscar = findViewById(R.id.buscarButton);


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlujoTutor.this, tutor_buscar.class);
                startActivity(intent);
            }
        });


    }

    private void descargarListaTrabajadores() {
        Call<String> call = apiService.descargarListaTrabajadores();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Guardar el archivo en el almacenamiento local
                    saveFile(response.body());
                    Toast.makeText(FlujoTutor.this, "Archivo descargado y guardado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FlujoTutor.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(FlujoTutor.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFile(String body) {
        try {
            File file = new File(getExternalFilesDir(null), "listaDeTrabajadores.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(body);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FlujoTutor.this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }


}