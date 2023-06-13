package com.example.datapasien

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class Presenter (val crudView: MainActivity) {
    //fungsi get data
    fun getData() {
        NetworkObject.getService().getData()
            .enqueue(object : retrofit2.Callback<ResultPasien> {
                override fun onFailure(call: Call<ResultPasien>, t: Throwable) {
                    crudView.onFailedGet(t.localizedMessage)
                    Log.d("Error","Error data")
                }

                override fun onResponse(call: Call<ResultPasien>, response: Response<ResultPasien>) {
                    if (response.isSuccessful) {
                        val status = response.body()?.status
                        if (status == 200) {
                            val data = response.body()?.pasien
                            crudView.onSuccessGet(data)
                        } else {
                            crudView.onFailedGet("Error $status")
                        }
                    }
                }
            })
    }

    //fungsi hapus data
    fun hapusData(context: Context, id: String?) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Hapus Data")
            .setMessage("Apakah anda yakin ingin menghapus data ini cuy?")
            .setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                NetworkObject.getService()
                    .deletePasien(id)
                    .enqueue(object : retrofit2.Callback<ResultStatus> {
                        override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                            crudView.onErrorDelete(t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<ResultStatus>,
                            response: Response<ResultStatus>
                        ) {
                            if (response.isSuccessful && response.body()?.status == 200) {
                                crudView.onSuccessDelete(response.body()?.pesan ?: " ")
                            } else {
                                crudView.onErrorDelete(response.body()?.pesan ?: " ")
                            }
                        }
                    })
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}