package com.example.lab04_aiot.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab04_aiot.R;
import com.example.lab04_aiot.databinding.FragmentTrabajadorBinding;
import com.example.lab04_aiot.model.Employee;
import com.example.lab04_aiot.repositroy.TrabajadorRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TrabajadorFragment extends Fragment {

    FragmentTrabajadorBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrabajadorBinding.inflate(inflater,container,false);

        NavController navController = NavHostFragment.findNavController(TrabajadorFragment.this);


        /**Cambiar la IP por la ip privada de la maquina donde se corre el web service**/
        TrabajadorRepository greenStoreRepository = new Retrofit.Builder()
                .baseUrl("http://192.168.18.7:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrabajadorRepository.class);

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
                                showTrabajadorNotifications(trabajador.getFirst_name());
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
}