package com.aitec.sitesport.champions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.adapter.SportAdapter
import com.aitec.sitesport.champions.adapter.TablePositionAdapterGenero
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Group
import com.aitec.sitesport.entities.Sport
import com.aitec.sitesport.entities.Team
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_table_positions.*

class TablePositionsFragment : Fragment(), SportAdapter.onSelectItemSport {

    private var sportList = arrayListOf<Sport>()
    lateinit var adapterSport: SportAdapter
    lateinit var adapterTablePositionHombres: TablePositionAdapterGenero
    lateinit var adapterTablePositionMujeres: TablePositionAdapterGenero


    private var groups = ArrayList<Group>()

    private var teamsHombres = ArrayList<Team>()
    private var teamsMujeres = ArrayList<Team>()

    var groupsString = arrayListOf<String>("GA", "GB", "GC", "GD")
    var groupSelect: String? = null
    var sportSelect: String = "Baloncesto"
    var indexGroup: Int = 0

    private var firebaseApi: FirebaseApi? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_table_positions, container, false)
    }

    private fun setupInjection() {
        val application = (context as ChampionShipActivity).application as MyApplication
        firebaseApi = application.domainModule!!.providesFirebaseApi()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewSport()
        setupRecyclerViewTablePositionsHombres()
        setupRecyclerViewTablePositionsMujeres()
        btn_team.setOnClickListener {
            val selectTeamFragment = SelectTeamFragment.newInstance(groupsString, indexGroup,"Grupo")
            selectTeamFragment.show(childFragmentManager, "SelectTeam")
        }

        getGrupos()
    }

    private fun getGrupos() {
        firebaseApi!!.getGroups(object : onApiActionListener<QuerySnapshot> {
            override fun onSucces(response: QuerySnapshot) {
                tv_message_sport.text = "Seleccione un grupo para cargar la tabla de posiciones"
                btn_team.isEnabled = true
                response.forEach {
                    val group = it.toObject(Group::class.java)
                    groups.add(group)
                }
            }

            override fun onError(error: Any?) {

            }
        })
    }

    override fun onSelectSport(sport: Sport) {
        sportSelect = sport.nombre
        filterTeams(groupSelect, sport.nombre)
    }

    private fun filterTeams(groupSelect: String?, nombre: String) {
        teamsHombres.clear()
        teamsMujeres.clear()
        val auxMujeres = arrayListOf<Team>()
        val auxHombres = arrayListOf<Team>()
        Log.e("filtros", "grupo: $groupSelect deporte: $nombre")
        groups.forEach {

            if (it.grupo.equals(groupSelect) and it.disciplina.equals(nombre) and it.genero.toLowerCase().equals("femenino")) {
                auxMujeres.addAll(it.teams)
            }

            if (it.grupo.equals(groupSelect) and it.disciplina.equals(nombre) and it.genero.toLowerCase().equals("masculino")) {
                auxHombres.addAll(it.teams)
            }
        }

        if (auxMujeres.size > 0) {
            cl_mujeres.visibility = View.VISIBLE

        } else {
            cl_mujeres.visibility = View.GONE
        }

        if (auxHombres.size > 0) {
            cl_hombres.visibility = View.VISIBLE

        } else {
            cl_hombres.visibility = View.GONE
        }

        auxMujeres.sortedWith(
                compareBy({ it.pu }, { it.dif })
        ).reversed().toCollection(teamsMujeres)


        auxHombres.sortedWith(
                compareBy({ it.pu }, { it.dif })
        ).reversed().toCollection(teamsHombres)



        adapterTablePositionMujeres.notifyDataSetChanged()
        adapterTablePositionHombres.notifyDataSetChanged()

    }

    fun onTeamSelect(team: String, valu: Int) {
        groupSelect = team
        tv_message_sport.visibility = View.GONE
        rv_sport.visibility = View.VISIBLE
        Log.e("TEAMSELEC", "groupsString: $team deporte: $sportSelect ")
        btn_team.text = team
        indexGroup = valu
        filterTeams(groupSelect, sportSelect!!)
    }

    fun updateSports() {
        adapterSport.notifyDataSetChanged()
    }

    private fun setupRecyclerViewSport() {
        sportList.clear()
        var sport = Sport()
        sport.nombre = "Baloncesto"
        var sport2 = Sport()
        sport2.nombre = "Indor"
        var sport3 = Sport()
        sport3.nombre = "Ecuavoley"
        sportList.add(sport)
        sportList.add(sport2)
        sportList.add(sport3)
        adapterSport = SportAdapter(sportList, this)
        rv_sport.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_sport.adapter = adapterSport
        rv_sport.visibility = View.GONE
    }

    private fun setupRecyclerViewTablePositionsHombres() {
        adapterTablePositionHombres = TablePositionAdapterGenero(teamsHombres)
        rvPositions_h.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPositions_h.adapter = adapterTablePositionHombres
    }

    private fun setupRecyclerViewTablePositionsMujeres() {
        adapterTablePositionMujeres = TablePositionAdapterGenero(teamsMujeres)
        rvPositions_m.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPositions_m.adapter = adapterTablePositionMujeres
    }


    companion object {
        @JvmStatic
        fun newInstance() = TablePositionsFragment().apply {}
    }
}
