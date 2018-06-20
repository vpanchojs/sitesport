package com.aitec.sitesport.reserve.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.entities.enterprise.Dia
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity
import com.aitec.sitesport.reserve.ReservePresenter
import com.aitec.sitesport.reserve.adapter.CourtAdapter
import com.aitec.sitesport.reserve.adapter.ItemReservationAdapter
import com.aitec.sitesport.reserve.adapter.OnClickListenerCourt
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.DayOfWeek
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.bottom_sheet_resume_reserve.*
import kotlinx.android.synthetic.main.content_reserve.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ReserveActivity : AppCompatActivity(), OnClickListenerCourt, View.OnClickListener, ReserveView, ItemReservationAdapter.onItemListener {

    /*
    override fun isCheck(check: Boolean, itemReservation: ItemReservation) {
        Log.e("itemHours", itemReservation.end)

    }
    */


    override fun check(itemReservation: ItemReservation, position: Int) {
        tv_subtitle_bs2.text = getNumItemsChecked().toString()
    }

    fun getNumItemsChecked(): Int {
        return items.filter {
            it.select == true
        }.size
    }

    override fun unCheck(itemReservation: ItemReservation, position: Int) {
        if (getNumItemsChecked() > 0) {
            tv_subtitle_bs2.text = getNumItemsChecked().toString()
        } else {
            tv_subtitle_bs2.text = "Ninguno"
        }
    }


    lateinit var enterprise: Enterprise


    val application: MyApplication by lazy {
        getApplication() as MyApplication
    }

    val c: Calendar by lazy {
        Calendar.getInstance()
    }
    val items = ArrayList<ItemReservation>()
    var adapterTableTime = ItemReservationAdapter(items, this)

    var pkCourt = ""

    @Inject
    lateinit var presenter: ReservePresenter

    override fun onCheckedCourt(court: Cancha) {
        pkCourt = court.pk
        tvNumPlayers.text = court.numero_jugadores
        tvFloor.text = court.piso
        tvPriceDay.text = "$ ${court.precio_dia}"
        tvPriceNight.text = "$ ${court.precio_noche}"
        presenter.getItemsReserved(c.timeInMillis, enterprise.pk, court.pk)
        setTableTime(getTableTimeToday(getNameToday()))
    }

    private fun setupInject() {
        application.getReserveComponent(this).inject(this)
    }

    var fromDatePickerDialog: DatePickerDialog? = null
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_calendar -> {
                showDatePicker(c)

            }
            R.id.ib_day_next -> {
                addOrRemoveDaysCalendar(+1)
                presenter.getItemsReserved(c.timeInMillis, enterprise.pk, pkCourt)
                setTableTime(getTableTimeToday(getNameToday()))
              //  Log.e("dia", getNameToday())

            }

            R.id.ib_day_back -> {
                addOrRemoveDaysCalendar(-1)
                presenter.getItemsReserved(c.timeInMillis, enterprise.pk, pkCourt)
                setTableTime(getTableTimeToday(getNameToday()))
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
        enterprise = intent.getParcelableExtra(ProfileActivity.ENTERPRISE)
        setupToolbar(enterprise.nombres)
        //Log.e("canchas", enterprise.canchas.toString())
        setupRecyclerViewClourt(enterprise.canchas)
        setupTodayDate(c)
        //setupRecyclerViewTimeTable(getTableTimeToday(getNameToday()))
        setupRecyclerViewTimeTable()

        setupEventsElements()
        setupBottomSheet()
        setupInject()
        presenter.onSubscribe()
    }

    private fun setupTodayDate(c: Calendar) {
        Log.e("FECHA", c.timeInMillis.toString())
        val formateador = SimpleDateFormat("dd 'de' MMMM", Locale("ES"))
        btn_calendar.text = formateador.format(c.time)
    }

    private fun setupToolbar(nameEntrepise: String) {
        setSupportActionBar(toolbar)
        toolbar.title = "Reservaciones"
        toolbar.subtitle = nameEntrepise
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


    fun showDatePicker(c: Calendar) {
        fromDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            updateCalendar(year, monthOfYear, dayOfMonth)
            setupTodayDate(c)
            presenter.getItemsReserved(c.timeInMillis, enterprise.pk, pkCourt)
            //Log.e("picker", "fecha obtenida")

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        fromDatePickerDialog!!.show()
    }

    fun updateCalendar(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        c.set(year, monthOfYear, dayOfMonth)
    }

    fun addOrRemoveDaysCalendar(num: Int) {
        c.add(Calendar.DAY_OF_WEEK, num)
        setupTodayDate(c)
    }

    private fun setupEventsElements() {
        btn_calendar.setOnClickListener(this)
        cl_header_bs.setOnClickListener(this)
        ib_day_back.setOnClickListener(this)
        ib_day_next.setOnClickListener(this)
    }

    private fun setupRecyclerViewClourt(courts: List<Cancha>) {
        //  Log.e("canchas", courts.toString())
        val adapter = CourtAdapter(courts, this)
        rvCourts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCourts.adapter = adapter
    }


    fun setupRecyclerViewTimeTable() {
        //val adapterTableTime = ItemReservationAdapter(items, this)
        rv_time_table.setHasFixedSize(true);
        rv_time_table.layoutManager = LinearLayoutManager(this)
        rv_time_table.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_time_table.adapter = adapterTableTime
    }

    private fun setTableTime(day: Dia?) {
        items.clear()
        val arrayHoraInicio = day!!.hora_inicio.split(":")
        val arrayHoraFin = day.hora_fin.split(":")

        val inicio = arrayHoraInicio[0].toInt()
        val fin = arrayHoraFin[0].toInt()

        for (i in inicio..fin - 1) {
            items.add(ItemReservation("$i:00", "${i + 1}:00", false, false))
        }
        adapterTableTime.notifyDataSetChanged()
    }

    fun getNameToday(): String? {
        val day = c.get(Calendar.DAY_OF_WEEK)

        when (day) {
            1 -> {
                return DayOfWeek.SUNDAY.dayNumber
            }
            2 -> {
                return DayOfWeek.MONDAY.dayNumber
            }
            3 -> {
                return DayOfWeek.TUESDAY.dayNumber
            }
            4 -> {
                return DayOfWeek.WEDNESDAY.dayNumber
            }
            5 -> {
                return DayOfWeek.THURSDAY.dayNumber
            }
            6 -> {
                return DayOfWeek.FRIDAY.dayNumber
            }
            7 -> {
                return DayOfWeek.SUNDAY.dayNumber
            }
            else -> {
                return null
            }
        }
    }

    fun getTableTimeToday(day: String?): Dia? {
        return enterprise.horario.find {
            it.pk.toLowerCase() == day
        }
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
        cl_load_reservation.visibility = visible
    }

    override fun showContainerItemsReserve(visible: Int) {
        rv_time_table.visibility = visible
        btn_calendar.visibility = visible
        ib_day_next.visibility = visible
        ib_day_back.visibility = visible
        bottom_sheet.visibility = visible

    }
}

