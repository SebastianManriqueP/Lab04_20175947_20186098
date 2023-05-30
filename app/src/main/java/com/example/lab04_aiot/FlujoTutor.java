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

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Obtener instancia de ApiService
        apiService = retrofit.create(ApiServiceTutor.class);

        // Obtener referencia al botón en el layout
        Button descargarButton = findViewById(R.id.descargarButton);


        // Configurar el evento de clic en el botón
        descargarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a la API para descargar la lista de trabajadores
                descargarListaTrabajadores();
            }
        });

        Button botonBuscar = findViewById(R.id.buscarButton);

        // Configurar el evento de clic en el botón
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad TutorBuscar
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
                    // Manejar el error de la respuesta de la API
                    Toast.makeText(FlujoTutor.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar el error de la solicitud
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
            // Manejar el error al guardar el archivo
            Toast.makeText(FlujoTutor.this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }


}