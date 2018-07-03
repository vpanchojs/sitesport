package com.aitec.sitesport.champions

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.adapter.CalendarAdapter
import com.aitec.sitesport.champions.adapter.SportAdapter
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.RealTimeListener
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.ItemCalendar
import com.aitec.sitesport.entities.Sport
import com.aitec.sitesport.entities.Team
import kotlinx.android.synthetic.main.activity_champion_ship.*
import kotlinx.android.synthetic.main.fragment_champion_ship.*

class ChampionShipActivity : AppCompatActivity(), SportAdapter.onSelectItemSport, View.OnClickListener, SelectTeamFragment.OnSelectTeamListener {

    private var firebaseApi: FirebaseApi? = null
    private var teamsList = ArrayList<Team>()
    lateinit var adapterSport: SportAdapter

    private var sportList = ArrayList<Sport>()

    var array = arrayListOf<String>()

    var teamSelect: Team? = null


    override fun onTeamSelect(team: String) {
        Log.e("TEAMSELEC", "team: $team")
        btn_team.text = team

        teamSelect = teamsList.find {
            it.nombre.equals(team)
        }
        Log.e("select", "ee ${teamSelect!!.deportes!!.size}")

        updateSports(teamSelect!!.deportes)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_team -> {
                val selectTeamFragment = SelectTeamFragment.newInstance(array)
                selectTeamFragment.show(supportFragmentManager, "SelectTeam")
            }
        }

    }

    override fun onSelectSport(sport: Sport) {
        Log.e("SPORT", sport.nombre)
        supportFragmentManager.fragments.forEach {
            if (it is CalendarFragment) {
                it.setFilterEncuentros(teamSelect!!, sport)
            }
        }
    }


    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_champion_ship)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        setupRecyclerViewClourt()
        setupEvent()
        setupInjection()
        getTeams()

    }

    private fun getTeams() {
        firebaseApi!!.getTeams(object : onApiActionListener<List<Team>> {
            override fun onSucces(response: List<Team>) {
                response.forEach {
                    array.add(it.nombre)
                }
                teamsList.addAll(response)
            }

            override fun onError(error: Any?) {

            }
        })
    }


    private fun setupInjection() {
        val application = getApplication() as MyApplication
        firebaseApi = application.domainModule!!.providesFirebaseApi()
    }


    fun setupEvent() {
        btn_team.setOnClickListener(this)
    }

    private fun setupRecyclerViewClourt() {
        //val sporst = ArrayList<Sport>()
        //sporst.add(Sport(nombre = "Baloncesto", grupo = ""))
        //sporst.add(Sport(nombre = "Indor", grupo = ""))
        //sporst.add(Sport(nombre = "Ecuavoley", grupo = ""))
        adapterSport = SportAdapter(sportList, this)
        rv_sport.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_sport.adapter = adapterSport
    }

    fun updateSports(sports: List<Sport>?) {
        sportList.clear()
        sportList.addAll(sports!!)
        adapterSport.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_champion_ship, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
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
    class CalendarFragment : Fragment() {

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

        private fun getEncuentros() {
            firebaseApi!!.getEncuentros(object : RealTimeListener<ItemCalendar> {
                override fun addDocument(response: ItemCalendar) {
                    Log.e("encuentros", "res ${response.fecha}")
                    itemCalentarList.add(response)
                    data.add(response)
                    adapterCalendar.notifyDataSetChanged()
                }

                override fun removeDocument(response: ItemCalendar) {

                }

                override fun updateDocument(response: ItemCalendar) {
                    val item = getItemCalenter(response.pk)
                    if (item != null) {
                        item.estado = response.estado
                        item.equipo2 = response.equipo2
                        item.equipo1 = response.equipo1
                    }
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
                it.equals(pk)
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


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupRecyclerViewTableTime()
            getEncuentros()
        }

        private fun setupRecyclerViewTableTime() {
            adapterCalendar = CalendarAdapter(data)
            rv_calendar.layoutManager = LinearLayoutManager(context)
            rv_calendar.adapter = adapterCalendar
        }


        fun updateAdapter(items: ArrayList<ItemCalendar>) {

            data.clear()
            data.addAll(items)
            adapterCalendar.notifyDataSetChanged()
            if (items.size <= 0) {
                tv_message.visibility = View.VISIBLE
            } else {
                tv_message.visibility = View.GONE
            }
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
}
