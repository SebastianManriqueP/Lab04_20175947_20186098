package com.example.lab04_aiot.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab04_aiot.EmpleadoViewModel;
import com.example.lab04_aiot.R;
import com.example.lab04_aiot.databinding.FragmentTrabajadorDetalleBinding;
import com.example.lab04_aiot.model.Employee;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class TrabajadorDetalle extends Fragment {

    FragmentTrabajadorDetalleBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrabajadorDetalleBinding.inflate(inflater,container,false);

        /*Recibir el empleado del Fragment anterior*/
        EmpleadoViewModel exampleViewModel = new ViewModelProvider(requireActivity()).get(EmpleadoViewModel.class);
         Employee empleado = exampleViewModel.getEmpleado().getValue();


        /*Llenar con datos de trabajador*/

        binding.textId.setText("ID: "+empleado.getId());
        binding.textNombre.setText("NOMBRE: "+empleado.getFirst_name()+" "+empleado.getLast_name());
        binding.textEmail.setText("EMAIL: "+empleado.getEmail());
        binding.textTelefono.setText("TELEFONO: "+empleado.getPhone_number());
        binding.textSalario.setText("SALARIO: "+empleado.getSalary());

        binding.buttonDescargar.setOnClickListener(view -> {
            guardarArchivoComoJson(empleado);
            Toast.makeText(this.getContext(),"Archivo EmpleadoDatos.txt guardado",Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }


    public void guardarArchivoComoJson(Employee empleado){
        Gson gson = new Gson();
        String empleadoJson = gson.toJson(empleado);
        String fileNameJson = "EmpleadoDatos";

        try (FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(empleadoJson);
        } catch (IOException e) {
            Log.d("msg-tst", "error al guardar ");;
        }
    }
}