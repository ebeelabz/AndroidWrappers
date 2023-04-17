package com.aspiresys.networklayer.network.core

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET
    fun getResource(@Url url: String,
                    @QueryMap query: HashMap<String, Any>?
    ): Call<JsonElement>

    @PUT
    fun updateResource(@Url url: String,
                       @QueryMap queryParams: HashMap<String, Any>?,
                       @Body bodyParams: HashMap<String, Any>?
    ): Call<JsonElement>

    @POST
    fun createResource(@Url url: String,
                       @QueryMap queryParams: HashMap<String, Any>?,
                       @Body bodyParams: HashMap<String, Any>?
    ): Call<JsonElement>

    @DELETE
    fun deleteResource(@Url url: String,
                       @QueryMap queryParams: HashMap<String, Any>?,
                       @Body bodyParams: HashMap<String, Any>?
    ): Call<JsonElement>

    @PATCH
    fun patchResource(@Url url: String,
                      @QueryMap query: HashMap<String, Any>?
    ): Call<JsonElement>
}