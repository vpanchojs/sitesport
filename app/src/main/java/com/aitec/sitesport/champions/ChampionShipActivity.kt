package com.aitec.sitesport.champions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.adapter.CalendarAdapter
import com.aitec.sitesport.champions.adapter.SportAdapter
import com.aitec.sitesport.champions.adapter.onCalendarAdapterListener
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.RealTimeListener
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.ItemCalendar
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.entities.Sport
import com.aitec.sitesport.entities.Team
import com.aitec.sitesport.team.TeamActivity
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.activity_champion_ship.*
import kotlinx.android.synthetic.main.fragment_champion_ship.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class ChampionShipActivity : AppCompatActivity() {

    private var firebaseApi: FirebaseApi? = null

    private var publication: Publication = Publication()


    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_champion_ship)
        setupInjection()
        setupToolBar()

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        publication.pk = intent.getStringExtra(Publication.PUBLICATION)
        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


        firebaseApi!!.getPublication(publication.pk, object : onApiActionListener<Publication> {
            override fun onError(error: Any?) {}

            override fun onSucces(response: Publication) {
                response.pk = publication.pk
                publication = response
                loadImage()
                toolbar.title = publication.titulo
            }

        })
    }


    private fun setupInjection() {
        val application = getApplication() as MyApplication
        firebaseApi = application.domainModule!!.providesFirebaseApi()
    }


    private fun loadImage() {
        if(this != null && imgToolbar!= null && publication.foto.isNotBlank()) {
            GlideApp.with(this)
                    .load(URL(publication.foto).toString())
                    .placeholder(R.drawable.ic_bg_balon)
                    .centerCrop()
                    .error(R.drawable.ic_bg_balon)
                    .into(imgToolbar)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_champion_ship, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.action_share -> {
                sharePublication()
            }
            R.id.action_go_location -> {
                goLocationMap()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goLocationMap() {
        val gmmIntentUri =
                Uri.parse("google.navigation:q=" +
                        -4.010622 + "," +
                        -79.2005194)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else
            BaseActivitys.showToastMessage(this, "No se encontró Google Maps", Toast.LENGTH_SHORT)
    }

    private fun sharePublication() {
        BaseActivitys.showToastMessage(this, "Obteniendo aplicaciones...", Toast.LENGTH_LONG)
        BaseActivitys.buildDinamycLinkShareApp(publication.pk, Publication.LINK_PUBLICATION_EVENT, object : onApiActionListener<String> {
            override fun onSucces(response: String) {
                intentShared(response)
            }

            override fun onError(error: Any?) {
                intentShared(null)
            }
        })
    }

    private fun intentShared(link: String?) {
        var auxLink = " ${resources.getString(R.string.url_play_store)}"
        if (link != null) auxLink = " $link"
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name)
        i.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.textShareChampionship) + auxLink)
        startActivity(Intent.createChooser(i, "Compartir mediante..."))
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return CalendarFragment.newInstance(position + 1)
                }
                1 -> {
                    return TablePositionsFragment.newInstance()
                }
                else -> {
                    return TablePositionsFragment.newInstance()
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    class CalendarFragment : Fragment(), onCalendarAdapterListener, SportAdapter.onSelectItemSport,
            View.OnClickListener {


        private var teamsList = ArrayList<Team>()
        lateinit var adapterSport: SportAdapter

        private var sportList = ArrayList<Sport>()

        var array = arrayListOf<String>()

        var teamSelect: Team? = null
        var sportSelect: Sport? = null
        var dateSelect: String? = null
        var valueTeam: Int = 0
        var valueDate: Int = 0


        override fun onSelectSport(sport: Sport) {
            Log.e("SPORT", sport.nombre)
            sportSelect = sport

            if (dateSelect == null) {
                setFilterEncuentros(teamSelect!!, sport)
            } else {
                setFilterEncuentros(teamSelect!!, sport, dateSelect)
            }

        }


        fun onTeamSelect(team: String, valu: Int) {
            tv_message_sport.visibility = View.GONE
            Log.e("TEAMSELEC", "team: $team")
            btn_team.text = team
            valueTeam = valu

            teamSelect = teamsList.find {
                it.nombre.equals(team)
            }
            Log.e("select", "ee ${teamSelect!!.deportes!!.size}")

            updateSports(teamSelect!!.deportes)

        }


        private fun getTeams() {
            btn_team.isEnabled = false
            tv_message_sport.text = "Obteniendo lista de equipos"

            firebaseApi!!.getTeams(object : onApiActionListener<List<Team>> {
                override fun onSucces(response: List<Team>) {
                    response.forEach {
                        array.add(it.nombre)
                    }
                    teamsList.addAll(response)
                    btn_team.isEnabled = true
                    tv_message_sport.text = "Seleccione un equipo para cargar los deportes"

                }

                override fun onError(error: Any?) {

                }
            })
        }

        private fun setupRecyclerViewClourt() {
            adapterSport = SportAdapter(sportList, this)
            rv_sport.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rv_sport.adapter = adapterSport
        }

        fun updateSports(sports: List<Sport>?) {
            rv_sport.adapter = null

            adapterSport = SportAdapter(sports!!, this)
            //sportList.addAll(sports!!)
            rv_sport.adapter = adapterSport
            //adapterSport.notifyDataSetChanged()
        }

        override fun onClick(p0: View?) {
            when (p0!!.id) {

                R.id.cl_date -> {
                    val dataAvaliable = getDateAvaliable()
                    if (dataAvaliable.size > 0) {
                        val selectDateFragment = SelectDateFragment.newInstance(dataAvaliable, valueDate)
                        selectDateFragment.show(childFragmentManager, "SelectDate")
                        getDateAvaliable()
                    } else {
                        Toast.makeText(context, "Obteniendo fechas, intentelo nuevamente", Toast.LENGTH_LONG).show()
                    }

                }
                R.id.btn_team -> {
                    val selectTeamFragment = SelectTeamFragment.newInstance(array, valueTeam, "Equipo")
                    selectTeamFragment.show(childFragmentManager, "SelectTeam")
                    //firebaseApi!!.setTablaPositions()
                }
            }
        }

        override fun navigatioTeam(team: Team) {

            if (!team.pk.equals("")) {
                val i = Intent(activity!!, TeamActivity::class.java)
                i.putExtra("team", team)
                startActivity(i)
            } else {
                Toast.makeText(context, "No tenemos información sobre ese equipo", Toast.LENGTH_LONG).show()
            }

        }


        private var firebaseApi: FirebaseApi? = null
        var itemCalentarList = ArrayList<ItemCalendar>()
        lateinit var adapterCalendar: CalendarAdapter

        var data = ArrayList<ItemCalendar>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setupInjection()
        }

        private fun setupInjection() {
            val application = (context as ChampionShipActivity).application as MyApplication
            firebaseApi = application.domainModule!!.providesFirebaseApi()

        }


        fun getDateAvaliable(): ArrayList<String> {
            val dates = arrayListOf<String>()
            val dateDate = arrayListOf<Date>()
            itemCalentarList.forEach {

                val date = dates.find { d ->
                    d.equals(it.fecha)
                }

                if (date == null) {
                    dates.add(it.fecha)
                    dateDate.add(it.date)
                }
            }

            dateDate.sort()
            dates.clear()

            dateDate.forEach {
                Log.e("dia", it.toString())
                dates.add(SimpleDateFormat("EEE dd 'de' MMMM", Locale("ES")).format(it))
            }

            return dates
        }

        private fun orderDate() {
            fun selector(item: ItemCalendar): Long = item.date.time
            val orderDateList = ArrayList(data.sortedBy { selector(it as ItemCalendar) })
            data.clear()
            data.addAll(orderDateList)
            adapterCalendar.notifyDataSetChanged()
        }

        private fun getEncuentros() {
            progressbar.visibility = View.VISIBLE
            firebaseApi!!.getEncuentros(object : RealTimeListener<ItemCalendar> {
                override fun addDocument(response: ItemCalendar) {
                    itemCalentarList.add(response)
                    orderDate()
                    data.add(response)
                    adapterCalendar.notifyDataSetChanged()
                    progressbar.visibility = View.GONE
                }

                override fun removeDocument(response: ItemCalendar) {

                }

                override fun updateDocument(response: ItemCalendar) {
                    Log.e(TAG, "${response.estado}")
                    val item = getItemCalenter(response.pk)
                    if (item != null) {
                        Log.e(TAG, "se actualizo")
                        item.estado = response.estado
                        item.equipo2 = response.equipo2
                        item.equipo1 = response.equipo1
                    }

                    adapterCalendar.notifyDataSetChanged()

                }

                override fun omError(error: Any) {
                    Log.e("encuentros", error.toString())
                    //Snackbar.make(main_content, error.toString(), Snackbar.LENGTH_LONG).show()
                }

                override fun emptyNode(msg: Any) {

                }
            })
        }


        fun getItemCalenter(pk: String): ItemCalendar? {
            return itemCalentarList.find {
                it.pk.equals(pk)
            }
        }


        fun setFilterEncuentros(team: Team, sport: Sport) {
            val list = itemCalentarList.filter {
                (it.equipo1.nombre.toLowerCase().equals(team.nombre.toLowerCase()) or it.equipo2.nombre.toLowerCase().equals(team.nombre.toLowerCase())) and it.deporte.toLowerCase().equals(sport.nombre.toLowerCase())
            }

            Log.e("frag", list.size.toString())
            updateAdapter(list as ArrayList<ItemCalendar>)

        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_champion_ship, container, false)

            return rootView
        }


        fun setupEvent() {
            cl_date.setOnClickListener(this)
            btn_team.setOnClickListener(this)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupRecyclerViewClourt()
            setupEvent()
            setupRecyclerViewTableTime()
            getTeams()
            getEncuentros()
        }

        private fun setupRecyclerViewTableTime() {
            adapterCalendar = CalendarAdapter(data, this)
            rv_calendar.layoutManager = LinearLayoutManager(context)
            rv_calendar.adapter = adapterCalendar
        }


        fun updateAdapter(items: ArrayList<ItemCalendar>) {

            data.clear()
            data.addAll(items)
            adapterCalendar.notifyDataSetChanged()
            orderDate()
            if (items.size <= 0) {
                tv_message.visibility = View.VISIBLE
            } else {
                tv_message.visibility = View.GONE
            }
        }

        fun onDateSelect(date: String, value: Int) {
            dateSelect = date
            tv_date_select.text = date
            valueDate = value

            //Log.e("select", "ee ${teamSelect!!.deportes!!.size}")
            if ((teamSelect != null) and (sportSelect != null)) {
                setFilterEncuentros(teamSelect!!, sportSelect!!, date)
            } else {
                setFilterEncuentros(date)
            }


        }

        private fun setFilterEncuentros(date: String) {
            val list = itemCalentarList.filter {
                it.fecha.equals(date)
            }

            Log.e("frag", list.size.toString())
            updateAdapter(list as ArrayList<ItemCalendar>)
        }

        fun setFilterEncuentros(team: Team, sport: Sport, date: String?) {
            val list = itemCalentarList.filter {
                (it.fecha.equals(date)) and (it.equipo1.nombre.toLowerCase().equals(team.nombre.toLowerCase()) or it.equipo2.nombre.toLowerCase().equals(team.nombre.toLowerCase())) and (it.deporte.toLowerCase().equals(sport.nombre.toLowerCase()))
            }

            Log.e("frag", list.size.toString())
            updateAdapter(list as ArrayList<ItemCalendar>)

        }

        companion object {

            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): CalendarFragment {
                val fragment = CalendarFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    companion object {
        const val TAG = "ChampionShipActivity"
    }
}

