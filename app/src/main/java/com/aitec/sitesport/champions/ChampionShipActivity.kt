package com.aitec.sitesport.champions

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.adapter.CalendarAdapter
import com.aitec.sitesport.champions.adapter.SportAdapter
import com.aitec.sitesport.entities.ItemCalendar
import com.aitec.sitesport.entities.Sport
import com.aitec.sitesport.entities.Team
import com.aitec.sitesport.entities.enterprise.Cancha
import kotlinx.android.synthetic.main.activity_champion_ship.*
import kotlinx.android.synthetic.main.fragment_champion_ship.*

class ChampionShipActivity : AppCompatActivity(), SportAdapter.onSelectItemSport, View.OnClickListener, SelectTeamFragment.OnSelectTeamListener {


    override fun onTeamSelect(team: String) {

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_team -> {
                val selectTeamFragment = SelectTeamFragment.newInstance(arrayOf("Todos", "5 B", "7 C "))
                selectTeamFragment.show(supportFragmentManager, "SelectTeam")
            }
        }

    }

    override fun onSelectSport(sport: Sport) {

    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
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

        setupRecyclerViewClourt(ArrayList<Cancha>())
        setupEvent()
    }


    fun setupEvent() {
        btn_team.setOnClickListener(this)
    }

    private fun setupRecyclerViewClourt(courts: List<Cancha>) {
        //  Log.e("canchas", courts.toString())
        val sporst = ArrayList<Sport>()
        sporst.add(Sport(nombre = "Baloncesto", juega = true))
        sporst.add(Sport(nombre = "Indor", juega = true))
        sporst.add(Sport(nombre = "Ecuavoley", juega = true))
        val adapter = SportAdapter(sporst, this)
        rv_sport.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_sport.adapter = adapter
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


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a CalendarFragment (defined as a static inner class below).
            return CalendarFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class CalendarFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_champion_ship, container, false)
            return rootView
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            var items = ArrayList<ItemCalendar>()
            items.add(ItemCalendar(fecha = "12 de junio", hora = "8 am", genero = "Masculino", estado = "SIN JUGAR", equipo_1 = Team(), equipo_2 = Team()))
            items.add(ItemCalendar(fecha = "12 de junio", hora = "8 am", genero = "Masculino", estado = "SIN JUGAR", equipo_1 = Team(), equipo_2 = Team()))
            setupRecyclerViewTableTime(items)
        }

        private fun setupRecyclerViewTableTime(items: List<ItemCalendar>) {
            val adapter = CalendarAdapter(items)
            rv_calendar.layoutManager = LinearLayoutManager(context)
            rv_calendar.adapter = adapter
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
