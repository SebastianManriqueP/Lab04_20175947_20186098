package com.example.lab04_aiot.repositroy;

import com.example.lab04_aiot.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrabajadorRepository {

    @GET("/employees/listar")
    Call<List<Employee>> getListaTrabajadores();

    @GET("/employees/buscar")
    Call<Employee> getTrabajador(@Query("id") String id);
}
