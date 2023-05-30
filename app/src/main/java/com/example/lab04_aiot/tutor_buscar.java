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

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Obtener instancia de ApiService
        apiService = retrofit.create(ApiServiceTutor.class);

        // Obtener referencia al EditText en el layout
        codigoEditText = findViewById(R.id.codigoEditText);

        // Obtener referencia al botón en el layout
        Button buscarButton = findViewById(R.id.buscarButton);

        // Configurar el evento de clic en el botón
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el código ingresado por el usuario
                String codigo = codigoEditText.getText().toString();

                // Llamar a la API para buscar el trabajador por código
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
                    // Manejar el error de la respuesta de la API
                    Toast.makeText(tutor_buscar.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar el error de la solicitud
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
            // Manejar el error al guardar el archivo
            Toast.makeText(tutor_buscar.this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

}