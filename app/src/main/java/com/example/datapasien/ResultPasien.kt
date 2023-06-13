package com.example.datapasien

import com.google.gson.annotations.SerializedName

class ResultPasien {
    @field:SerializedName("pesan")
    val pesan: String? = null

    @field:SerializedName("pasien")
    val pasien: List<DataItem>? = null

    @field:SerializedName("status")
    val status: Int? = null

}