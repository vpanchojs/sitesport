package com.aitec.sitesport.champions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.adapter.CalendarAdapter
import com.aitec.sitesport.champions.adapter.SportAdapter
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.ItemCalendar
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.entities.Sport
import com.aitec.sitesport.entities.Team
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.activity_champion_ship.*
import kotlinx.android.synthetic.main.fragment_champion_ship.*

class ChampionShipActivity : AppCompatActivity(), SportAdapter.onSelectItemSport, View.OnClickListener, SelectTeamFragment.OnSelectTeamListener {

    private var publication: Publication = Publication()

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

        setupToolBar()
        if(intent.extras.containsKey(Publication.PUBLICATION))
            publication.pk = intent.getStringExtra(Publication.PUBLICATION)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        setupRecyclerViewClourt(ArrayList<Cancha>())
        setupEvent()
    }


    fun setupEvent() {
        btn_team.setOnClickListener(this)
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = "Campeonato"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupRecyclerViewClourt(courts: List<Cancha>) {
        val sporst = ArrayList<Sport>()
        sporst.add(Sport(nombre = "Baloncesto", juega = true))
        sporst.add(Sport(nombre = "Indor", juega = true))
        sporst.add(Sport(nombre = "Ecuavoley", juega = true))
        val adapter = SportAdapter(sporst, this)
        rv_sport.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_sport.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_champion_ship, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_go_location -> {
                goLocationMap()
            }

            android.R.id.home -> {
                onBackPressed()
            }

            R.id.action_share -> {
                sharePublication()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goLocationMap(){
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
        BaseActivitys.buildDinamycLinkShareApp(publication.pk, BaseActivitys.LINK_PUBLICATION, object : onApiActionListener<String> {
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
            return CalendarFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return 3
        }
    }

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
