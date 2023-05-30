package com.example.lab04_aiot.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab04_aiot.EmpleadoViewModel;
import com.example.lab04_aiot.R;
import com.example.lab04_aiot.databinding.FragmentTrabajadorBinding;
import com.example.lab04_aiot.model.Employee;
import com.example.lab04_aiot.repositroy.TrabajadorRepository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TrabajadorFragment extends Fragment {

    FragmentTrabajadorBinding binding;
    private static final int REQUEST_CODE = 100;
    public static final String imageURL = "https://i.pinimg.com/564x/4e/8e/a5/4e8ea537c896aa277e6449bdca6c45da.jpg";
    String imageName = "horarios.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrabajadorBinding.inflate(inflater,container,false);

        NavController navController = NavHostFragment.findNavController(TrabajadorFragment.this);

        /**Cambiar la IP por la ip privada de la maquina donde se ejecuta el web service**/
        String baseUrl = "http://192.168.18.7:8080";

        TrabajadorRepository greenStoreRepository = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrabajadorRepository.class);

        /*Al presionar el boto ver info:*/
        binding.bVerInfo.setOnClickListener(view -> {
            String id = binding.editTextNumber.getText().toString();
            if(id.equals("")) {
                CrearToast("Por favor ingrese un id");
            }else{
                /*Si se llena el campo id:*/
                greenStoreRepository.getTrabajador(id).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        if (response.isSuccessful()){
                            if(response.body() == null){
                                CrearToast("No existe empleado con ese id");
                            }else {
                                Employee trabajador = response.body();
                                if (trabajador.getMeeting() == 1){
                                     String fechaTutoria = trabajador.getMeeting_date().toString();
                                    showTrabajadorNotifications("Tiene una tutoria agendada: "+fechaTutoria);
                                }
                                EmpleadoViewModel empleadoViewModel = new ViewModelProvider(requireActivity()).get(EmpleadoViewModel.class);
                                empleadoViewModel.getEmpleado().setValue(trabajador);
                                navController.navigate(R.id.action_trabajadorFragment_to_trabajadorDetalle);
                            }
                        }else{
                            Log.d("msg-test", "error en la respuesta del webservice");
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        /*Al presionar el boto ver Horarios:*/
        binding.bDescHorario.setOnClickListener(View ->{
            String id = binding.editTextNumber.getText().toString();
            if(id.equals("")) {
                CrearToast("Por favor ingrese un id");
            }else{
                greenStoreRepository.getTrabajador(id).enqueue(new Callback<Employee>() {

                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        if (response.isSuccessful()){
                            if(response.body() == null){
                                CrearToast("No existe empleado con ese id");
                            }else {
                                Employee trabajador = response.body();
                                if (trabajador.getMeeting() == 1){
                                    downloadImage(imageURL, imageName);
                                }else{
                                    CrearToast("No cuenta con tutorías pendientes");
                                }
                            }
                        }else{
                            Log.d("msg-test", "error en la respuesta del webservice");
                        }
                    }
                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });





        return binding.getRoot();
    }


    private void showTrabajadorNotifications(CharSequence contenido) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity(), "trabajador_channel")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(contenido)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getContext());

        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(3, builder.build());
        }
    }

    private void CrearToast(String mensaje){
        Toast.makeText(this.getContext(),mensaje,Toast.LENGTH_SHORT).show();
    }



    public void downloadImage(String url, String outputFileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(imageName);
        request.setDescription("Downloading " + imageName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName);
        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }



}