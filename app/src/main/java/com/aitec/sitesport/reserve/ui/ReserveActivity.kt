package com.aitec.sitesport.reserve.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.reserve.ReservePresenter
import com.aitec.sitesport.reserve.adapter.*
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.bottom_sheet_resume_reserve.*
import kotlinx.android.synthetic.main.content_reserve.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ReserveActivity : AppCompatActivity(), OnClickListenerCourt, View.OnClickListener, ReserveView {

    val application: MyApplication by lazy {
        getApplication() as MyApplication
    }


    @Inject
    lateinit var presenter: ReservePresenter


    override fun onCheckedCourt(court: Cancha) {

    }

    private fun setupInject() {
        application.getReserveComponent(this).inject(this)
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
        setupInject()
        presenter.onSubscribe()
        presenter.getItemsReserved()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onUnSubscribe()
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
        var items = ArrayList<ItemReservation>()
        items.add(ItemReservation("8:00", "9:00", true))
        items.add(ItemReservation("9:00", "10:00", false))
        items.add(ItemReservation("10:00", "11:00", false))
        items.add(ItemReservation("11:00", "12:00", true))
        items.add(ItemReservation("12:00", "13:00", false))
        items.add(ItemReservation("12:00", "13:00", false))
        items.add(ItemReservation("12:00", "13:00", true))
        var adapterTableTime = ItemReservationAdapter(items)
        rv_time_table.setHasFixedSize(true);
        rv_time_table.layoutManager = LinearLayoutManager(this)
        rv_time_table.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_time_table.adapter = adapterTableTime



    }

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(this, message, Toast.LENGTH_LONG)
    }

    override fun showProgresBar(show: Int) {
        progressbar.visibility = show
    }

    override fun showButtonReserve(show: Int) {
        btn_reserve.visibility = show
    }

    override fun setItemsReserved(itemsReserved: List<ItemReservation>) {

    }

    override fun showProgresItemsReserve(visible: Int) {

    }

    override fun showContainerItemsReserve(visible: Int) {
        rv_time_table.visibility = visible
        btn_calendar.visibility = visible
    }
}

