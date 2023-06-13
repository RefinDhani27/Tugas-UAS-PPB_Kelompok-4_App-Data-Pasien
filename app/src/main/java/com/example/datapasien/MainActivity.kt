package com.example.datapasien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.datapasien.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CrudView {
    private lateinit var presenter: Presenter
    private lateinit var etSearch: EditText
    private lateinit var adapter: DataAdapter
    private lateinit var binding: ActivityMainBinding
    private val dataList: MutableList<DataItem> = mutableListOf()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etSearch =findViewById(R.id.etSearch)

        presenter = Presenter(this)
        presenter.getData()
        setupSearch()

        adapter = DataAdapter(dataList, object : DataAdapter.onClickItem {
            override fun clicked(item: DataItem?) {
                val bundle = Bundle()
                bundle.putSerializable("dataItem", item)
                val intent = Intent(applicationContext, UpdateAddActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun delete(item: DataItem?) {
                item?.pasienId?.let { id ->
                    presenter.hapusData(this@MainActivity, id)
                    presenter.getData()
                }
            }
        })

        binding.rvCategory.adapter = adapter

        binding.btnTambah.setOnClickListener {
            startActivity(Intent(applicationContext, UpdateAddActivity::class.java))
            finish()
        }
    }

    override fun onSuccessGet(data: List<DataItem>?) {
        dataList.clear()
        data?.let { dataList.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onFailedGet(msg: String) {

    }

    override fun onSuccessDelete(msg: String) {
        presenter.getData()
    }

    override fun onErrorDelete(msg: String) {
        Toast.makeText(this, "Delete tidak berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessAdd(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onErrorAdd(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onErrorUpdate(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessUpdate(msg: String) {
        TODO("Not yet implemented")
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }
        })
    }

    private fun search(query: String) {
        val filteredData = dataList.filter { data ->
            data.pasienName?.contains(query, ignoreCase = true) == true ||
                    data.pasienAlamat?.contains(query, ignoreCase = true) == true ||
                    data.pasienHp?.contains(query, ignoreCase = true) == true ||
                    data.pasienTglMasuk?.contains(query, ignoreCase = true) == true ||
                    data.pasienTglKeluar?.contains(query, ignoreCase = true) == true
        }
        if (filteredData.isEmpty()) {
            binding.tvDataNotFound.visibility = View.VISIBLE
        } else {
            binding.tvDataNotFound.visibility = View.GONE
        }

        adapter.setData(filteredData)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Clean up the handler when the activity is destroyed
    }
}