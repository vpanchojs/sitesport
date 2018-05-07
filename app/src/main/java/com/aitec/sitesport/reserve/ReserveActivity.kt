package com.aitec.sitesport.reserve

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.reserve.adapter.CourtAdapter
import com.aitec.sitesport.reserve.adapter.OnClickListenerCourt
import com.aitec.sitesport.reserve.adapter.TableTimeAdapter
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.bottom_sheet_resume_reserve.*
import kotlinx.android.synthetic.main.content_reserve.*
import java.util.*
import kotlin.collections.ArrayList


class ReserveActivity : AppCompatActivity(), OnClickListenerCourt, View.OnClickListener, TableTimeAdapter.onTableTimeAdapterListener {

    override fun onCheckedCourt(court: Cancha) {

    }


    var fromDatePickerDialog: DatePickerDialog? = null
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_calendar -> {
                showDatePicker()
            }
            R.id.cl_header_bs -> {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        setSupportActionBar(toolbar)
        setupRecyclerViewClourt()
        setupRecyclerViewTimeTable()
        setupEventsElements()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        iv_icon_open.rotation = 0F
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        iv_icon_open.rotation = 180F

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }


    fun showDatePicker() {
        val c = Calendar.getInstance()
        fromDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        fromDatePickerDialog!!.show()

    }

    private fun setupEventsElements() {
        btn_calendar.setOnClickListener(this)
        cl_header_bs.setOnClickListener(this)
    }


    private fun setupRecyclerViewClourt() {
        val courtList = ArrayList<Cancha>()

        courtList.add(Cancha())
        courtList.add(Cancha())

        val adapter = CourtAdapter(courtList, this)
        rv_fields_profile.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_fields_profile.adapter = adapter
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
