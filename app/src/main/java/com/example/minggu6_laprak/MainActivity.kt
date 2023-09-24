package com.example.minggu6_laprak

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.example.minggu6_laprak.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("Hadir Tepat Waktu", "Sakit", "Terlambat", "Izin")
        val ad = ArrayAdapter(this@MainActivity, R.layout.spinner_item, items)
        val calendar = Calendar.getInstance()
        var selectedDate = SimpleDateFormat("MMMM dd, yyyy",Locale.getDefault()).format(calendar.time)
        var selectedTime = decideTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))


        with(binding) {

            spinner.adapter = ad
            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selected = spinner.selectedItem.toString()
                    if(selected == items[1] || selected == items[2] || selected == items[3]) {
                        kurara.visibility = View.VISIBLE
                    } else {
                        kurara.visibility = View.GONE
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            datePicker2.setOnDateChangeListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                selectedDate = dateFormat.format(calendar.time)
            }

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                selectedTime = decideTime(hourOfDay, minute)
            }
            suparogu.setOnClickListener {
                val selected = spinner.selectedItem.toString()
                if(selected == items[1] || selected == items[2] || selected == items[3]) {
                    if(kurara.text.toString() == "") {
                        Snackbar.make(binding.root, "Mohon isi kolom keterangan", Snackbar.LENGTH_SHORT).setAnchorView(suparogu).show()
                    } else {
                        Snackbar.make(binding.root, "Presensi berhasil $selectedDate jam $selectedTime", Snackbar.LENGTH_SHORT).setAnchorView(suparogu).show()
                    }
                } else {
                    Snackbar.make(binding.root, "Presensi berhasil $selectedDate jam $selectedTime", Snackbar.LENGTH_SHORT).setAnchorView(suparogu).show()
                }
            }
        }
    }

    private fun decideTime(hourOfDay:Int, minute:Int): String {
        var selectedTime = ""
        if(hourOfDay < 12) {
            selectedTime = "$hourOfDay:$minute AM"
        } else if(hourOfDay > 12) {
            selectedTime = "${hourOfDay % 12}:$minute PM"
        }  else{
            if(minute > 0) {
                selectedTime = "${hourOfDay % 12}:$minute PM"
            } else if(minute == 0) {
                selectedTime = "$hourOfDay:$minute AM"
            }
        }
        return selectedTime
    }

}