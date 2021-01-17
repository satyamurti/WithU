package com.incoders.withu.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface FileUploadService {

    @POST("/check")
    fun uploadDocument(
            @Field("data") encodedAudioFile: String?
    ): Call<String>

    @GET("/")
    fun getHelloWolrd() : Call<String>


    @Multipart
    @POST("/check")
    fun uploadAudio(
        @Part("keys") keys: RequestBody,
        @Part myfile: MultipartBody.Part?
    ): Call<String>
}
