package com.example.datapasien

interface CrudView   {
    //Get Data
    fun onSuccessGet(data: List<DataItem>?)
    fun onFailedGet(msg: String)

    //Add Data
    fun onSuccessAdd(msg: String)
    fun onErrorAdd(msg: String)

    //Update Data
    fun onSuccessUpdate(msg: String)
    fun onErrorUpdate(msg: String)

    //Delete Data
    fun onSuccessDelete(msg: String)
    fun onErrorDelete(msg: String)
}