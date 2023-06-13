package com.example.datapasien

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.example.datapasien.databinding.ActivityUpdateAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Suppress("SENSELESESS_COMPARISON")
class UpdateAddActivity : AppCompatActivity(), CrudView {
    private lateinit var presenter: Presenter2
    private lateinit var tgl_masuk: EditText
    private lateinit var tgl_keluar: EditText
    private lateinit var binding: ActivityUpdateAddBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = Presenter2(this)
        val itemDataItem = intent.getSerializableExtra("dataItem")

        tgl_masuk = findViewById(R.id.etTglMsk)
        tgl_keluar = findViewById(R.id.etTglKlr)

        if (itemDataItem == null) {
            binding.btnAction.text = "Tambah Pasien"
            binding.btnAction.setOnClickListener() {
                val name = binding.etName.text.toString().trim()
                val hp = binding.etPhone.text.toString().trim()
                val alamat = binding.etAddress.text.toString().trim()
                val tgl_masuk = binding.etTglMsk.text.toString().trim()
                val tgl_keluar = binding.etTglKlr.text.toString().trim()

                if (name.isEmpty()) {
                    binding.etName.error = "Nama Tidak Boleh Kosong Cuy"
                } else if (hp.isEmpty()) {
                    binding.etPhone.error = "Nomor Telepon Tidak Boleh Kosong Cuy"
                } else if (alamat.isEmpty()) {
                    binding.etAddress.error = "Alamat Tidak Boleh Kosong Cuy"
                } else if (tgl_masuk.isEmpty()) {
                    binding.etTglMsk.error = "Tanggal Masuk Tidak Boleh Kosong Cuy"
                } else if (tgl_keluar.isEmpty()) {
                    binding.etTglKlr.error = "Tanggal Keluar Tidak Boleh Kosong Cuy"
                } else {
                    presenter.addData(
                        name,
                        hp,
                        alamat,
                        convertToDatabaseDateFormat(tgl_masuk),
                        convertToDatabaseDateFormat(tgl_keluar)
                    )
                }
            }
        } else if (itemDataItem != null) {
            binding.btnAction.text = "Update Pasien"
            val item = itemDataItem as DataItem?
            binding.etName.setText(item?.pasienName.toString())
            binding.etPhone.setText(item?.pasienHp.toString())
            binding.etAddress.setText(item?.pasienAlamat.toString())
            binding.etTglMsk.setText(convertToDisplayDateFormat(item?.pasienTglMasuk.toString()))
            binding.etTglKlr.setText(convertToDisplayDateFormat(item?.pasienTglKeluar.toString()))
            binding.btnAction.setOnClickListener() {
                val name = binding.etName.text.toString().trim()
                val hp = binding.etPhone.text.toString().trim()
                val alamat = binding.etAddress.text.toString().trim()
                val tgl_masuk = binding.etTglMsk.text.toString().trim()
                val tgl_keluar = binding.etTglKlr.text.toString().trim()

                if (name.isEmpty()) {
                    binding.etName.error = "Nama Tidak Boleh Kosong Cuy"
                } else if (hp.isEmpty()) {
                    binding.etPhone.error = "Nomor Telepon Tidak Boleh Kosong Cuy"
                } else if (alamat.isEmpty()) {
                    binding.etAddress.error = "Alamat Tidak Boleh Kosong Cuy"
                } else if (tgl_masuk.isEmpty()) {
                    binding.etTglMsk.error = "Tanggal Masuk Tidak Boleh Kosong Cuy"
                } else if (tgl_keluar.isEmpty()) {
                    binding.etTglKlr.error = "Tanggal Keluar Tidak Boleh Kosong Cuy"
                } else {
                    presenter.updateData(
                        item?.pasienId ?: "",
                        name,
                        hp,
                        alamat,
                        convertToDatabaseDateFormat(tgl_masuk),
                        convertToDatabaseDateFormat(tgl_keluar)
                    )
                    finish()
                }
            }
        }
    }

    override fun onSuccessAdd(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorAdd(msg: String) {
    }

    override fun onSuccessUpdate(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorUpdate(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessGet(data: List<DataItem>?) {
        TODO("Not yet implemented")
    }

    override fun onFailedGet(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessDelete(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onErrorDelete(msg: String) {
        TODO("Not yet implemented")
    }

    fun showDatePickerMasuk(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate.time)
                binding.etTglMsk.setText(selectedDateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    fun showDatePickerKeluar(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate.time)
                binding.etTglKlr.setText(selectedDateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.setOnCancelListener {
            binding.etTglKlr.setText("Default Value")
        }
        datePickerDialog.show()
    }

    private fun convertToDisplayDateFormat(date: String): String {
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val displayDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return try {
            val parsedDate = databaseDateFormat.parse(date)
            displayDateFormat.format(parsedDate)
        } catch (e: Exception) {
            ""
        }
    }

    private fun convertToDatabaseDateFormat(date: String): String {
        val displayDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val parsedDate = displayDateFormat.parse(date)
            databaseDateFormat.format(parsedDate)
        } catch (e: Exception) {
            ""
        }
    }
}