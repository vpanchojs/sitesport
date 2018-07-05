package com.aitec.sitesport.champions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.adapter.SportAdapter
import com.aitec.sitesport.champions.adapter.TablePositionAdapter
import com.aitec.sitesport.entities.Group
import com.aitec.sitesport.entities.Sport
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.fragment_table_positions.*

class TablePositionsFragment : Fragment(), SportAdapter.onSelectItemSport {

    private var sportList = ArrayList<Sport>()
    lateinit var adapterSport: SportAdapter
    lateinit var adapterTablePosition: TablePositionAdapter

    private var groups = ArrayList<Group>()
    var groupsString = arrayListOf<String>()
    var groupSelect: Group? = null
    var indexGroup: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_table_positions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewSport()
        setupRecyclerViewTablePositions()
        groupsString.add("grupos")
        btn_team.setOnClickListener {
            val selectTeamFragment = SelectTeamFragment.newInstance(groupsString, indexGroup)
            selectTeamFragment.show(childFragmentManager, "SelectTeam")
        }
    }

    override fun onSelectSport(sport: Sport) {
        BaseActivitys.showToastMessage(activity!!, sport.nombre, Toast.LENGTH_SHORT)
    }

    fun onTeamSelect(team: String, valu: Int) {
        tv_message_sport.visibility = View.GONE
        Log.e("TEAMSELEC", "groupsString: $team")
        btn_team.text = team
        indexGroup = valu

        groupSelect = groups.find {
            it.grupo.equals(team)
        }
        //Log.e("select", "ee ${groupSelect!!.deportes!!.size}")

        //updateSports(groupSelect!!.deportes)
    }

    fun updateSports(sports: List<Sport>?) {
        sportList.clear()
        sportList.addAll(sports!!)
        adapterSport.notifyDataSetChanged()
    }


    private fun setupRecyclerViewSport() {
        adapterSport = SportAdapter(sportList, this)
        rv_sport.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_sport.adapter = adapterSport
    }

    private fun setupRecyclerViewTablePositions() {
        adapterTablePosition = TablePositionAdapter(groups, activity!!)
        rvPositions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPositions.adapter = adapterTablePosition
    }


    companion object {
        @JvmStatic
        fun newInstance() = TablePositionsFragment().apply {}
    }
}
