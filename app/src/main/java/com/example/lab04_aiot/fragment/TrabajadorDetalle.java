package com.example.lab04_aiot.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab04_aiot.EmpleadoViewModel;
import com.example.lab04_aiot.R;
import com.example.lab04_aiot.databinding.FragmentTrabajadorDetalleBinding;
import com.example.lab04_aiot.model.Employee;


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
        return binding.getRoot();
    }
}