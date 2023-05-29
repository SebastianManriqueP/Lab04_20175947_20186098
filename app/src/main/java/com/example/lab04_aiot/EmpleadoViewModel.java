package com.example.lab04_aiot;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab04_aiot.model.Employee;

public class EmpleadoViewModel extends ViewModel {

private MutableLiveData<Employee> empleado = new MutableLiveData<>();


    public MutableLiveData<Employee> getEmpleado() {
        return empleado;
    }

    public void setEmpleado(MutableLiveData<Employee> empleado) {
        this.empleado = empleado;
    }
}
