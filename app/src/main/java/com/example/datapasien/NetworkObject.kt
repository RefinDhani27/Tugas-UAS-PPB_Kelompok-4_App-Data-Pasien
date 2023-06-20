package com.example.datapasien

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

object NetworkObject {
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return okHttpClient
    }

//    retrofit
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2/server_api/index.php/ServerApi/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(PasienService::class.java)
}

interface PasienService{
    //fungsi create data
    @FormUrlEncoded
    @POST("addPasien")
    fun addPasien(@Field("name") name:String,
                 @Field("hp") hp: String,
                 @Field("alamat") alamat: String,
                 @Field("tgl_masuk") tgl_masuk: String,
                 @Field("tgl_keluar") tgl_keluar: String ) : Call<ResultStatus>

    //fungsi get data
    @GET("getDataPasien")
    fun getData(): Call<ResultPasien>

    //fungsi update data
    @FormUrlEncoded
    @POST("updatePasien")
    fun updatePasien(@Field("id") id: String,
                    @Field("name") name: String,
                    @Field("hp") hp: String,
                    @Field("alamat") alamat: String,
                    @Field("tgl_masuk") tgl_masuk: String,
                    @Field("tgl_keluar") tgl_keluar: String ) : Call<ResultStatus>

    //fungsi delete
    @FormUrlEncoded
    @POST("deletePasien")
    fun deletePasien(@Field("id") id: String?) : Call<ResultStatus>
}