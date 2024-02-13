package com.example.contacttask.Api;

import com.example.contacttask.database.GenderizeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenderizeContactApi {

   @GET("/")
   Call<GenderizeResponse> getGender(@Query("name") String name);
}
