package com.example.datapasien

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataItem: Serializable {
    @field:SerializedName("pasien_name")
    val pasienName: String? = null

    @field:SerializedName("pasien_id")
    val pasienId: String? = null

    @field:SerializedName("pasien_hp")
    val pasienHp: String? = null

    @field:SerializedName("pasien_alamat")
    val pasienAlamat: String? = null

    @field:SerializedName("tgl_masuk")
    val pasienTglMasuk: String? = null

    @field:SerializedName("tgl_keluar")
    val pasienTglKeluar: String? = null
}