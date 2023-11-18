package com.example.falefacens.Service;

import com.example.falefacens.models.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FaleFacensServices {

    public static final String BASE_URL = "https://falefacens-production.up.railway.app/";

    @GET("category")
    Call<List<Categoria>> listaCategorias();
}
