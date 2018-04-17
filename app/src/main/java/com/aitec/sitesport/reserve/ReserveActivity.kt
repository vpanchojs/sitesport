package com.aitec.sitesport.reserve

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Courts
import com.aitec.sitesport.reserve.adapter.CourtAdapter
import com.aitec.sitesport.reserve.adapter.TableTimeAdapter
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.content_reserve.*
import java.util.*
import kotlin.collections.ArrayList


class ReserveActivity : AppCompatActivity(), CourtAdapter.onCourtAdapterListener, View.OnClickListener, TableTimeAdapter.onTableTimeAdapterListener {


    var fromDatePickerDialog: DatePickerDialog? = null

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_calendar -> {
                showDatePicker()
            }
        }
    }

    override fun setData(courts: Courts) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        setSupportActionBar(toolbar)
        setupRecyclerViewClourt()
        setupRecyclerViewTimeTable()
        setupEventsElements()
    }


    fun showDatePicker() {
        val c = Calendar.getInstance()
        fromDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        fromDatePickerDialog!!.show()

    }

    private fun setupEventsElements() {
        btn_calendar.setOnClickListener(this)
    }

    private fun setupRecyclerViewClourt() {
        val courtList = ArrayList<Courts>()

        courtList.add(Courts(name = "Cancha 1"))
        courtList.add(Courts(name = "Cancha 2"))
        courtList.add(Courts(name = "Cancha 3"))
        courtList.add(Courts(name = "Cancha 4"))
        courtList.add(Courts(name = "Cancha 5"))
        courtList.add(Courts(name = "Cancha 6"))

        val adapter = CourtAdapter(courtList, this)
        rv_fields.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_fields.adapter = adapter
    }

    private fun setupRecyclerViewTimeTable() {
        val hoursList = ArrayList<Hours>()
        hoursList.add(Hours("8:00", "9:00", true))
        hoursList.add(Hours("9:00", "10:00", false))
        hoursList.add(Hours("10:00", "11:00", false))
        hoursList.add(Hours("11:00", "12:00", true))
        hoursList.add(Hours("12:00", "13:00", false))
        hoursList.add(Hours("12:00", "13:00", false))
        hoursList.add(Hours("12:00", "13:00", true))
        val adapterTableTime = TableTimeAdapter(hoursList, this)
        rv_time_table.layoutManager = LinearLayoutManager(this)
        rv_time_table.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_time_table.adapter = adapterTableTime

    }


    data class Hours(var start: String, var end: String, var state: Boolean)

}
