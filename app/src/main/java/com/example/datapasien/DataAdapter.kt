package com.example.datapasien

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(private var data: List<DataItem>?, private val click: onClickItem) :
    RecyclerView.Adapter<DataAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.onBind(data?.get(position))
        holder.itemView.findViewById<Button>(R.id.btnUpdate).setOnClickListener() {
            click.clicked(data?.get(position))
        }
        holder.itemView.findViewById<Button>(R.id.btnHapus).setOnClickListener() {
            click.delete(data?.get(position))
        }
    }

    fun setData(newDataList: List<DataItem>) {
        data = newDataList
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: DataItem?) {
            itemView.findViewById<TextView>(R.id.tvName).text = item?.pasienName
            itemView.findViewById<TextView>(R.id.tvPhone).text = item?.pasienHp
            itemView.findViewById<TextView>(R.id.tvAddress).text = item?.pasienAlamat
            itemView.findViewById<TextView>(R.id.tvTglMsk).text = item?.pasienTglMasuk
            itemView.findViewById<TextView>(R.id.tvTglKlr).text = item?.pasienTglKeluar
        }
    }

    interface onClickItem {
        fun clicked(item: DataItem?)
        fun delete(item: DataItem?)
    }

}

