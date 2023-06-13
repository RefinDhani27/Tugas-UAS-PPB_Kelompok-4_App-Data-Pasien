package com.example.datapasien

import retrofit2.Call
import retrofit2.Response

class Presenter2 (val crudView: UpdateAddActivity) {
    //add data
    fun addData(name: String, hp: String, alamat: String, tgl_masuk: String, tgl_keluar: String) {
        NetworkObject.getService()
            .addPasien(name, hp, alamat, tgl_masuk, tgl_keluar)
            .enqueue(object : retrofit2.Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorAdd(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessAdd(response.body()?.pesan ?: "")
                    } else {
                        crudView.onErrorAdd(response.body()?.pesan ?: "")
                    }
                }
            })
    }

    //update data
    fun updateData(id: String, name: String, hp: String, alamat: String, tgl_masuk: String, tgl_keluar: String) {
        NetworkObject.getService()
            .updatePasien(id, name, hp, alamat, tgl_masuk, tgl_keluar)
            .enqueue(object : retrofit2.Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorUpdate(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessUpdate(response.body()?.pesan ?: "")
                    } else {
                        crudView.onErrorUpdate(response.body()?.pesan ?: "")
                    }
                }
            })
    }
}