package com.example.lab04_aiot.repositroy;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiServiceTutor {
    @GET("/descargarListaTrabajadores")
    @Streaming
    Call<String> descargarListaTrabajadores();

    @GET("/employees/buscar")
    @Streaming
    Call<String> buscarTrabajador(@Query("id") String id);

}
