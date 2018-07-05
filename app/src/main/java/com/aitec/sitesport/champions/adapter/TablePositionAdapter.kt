package com.aitec.sitesport.champions.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Group
import kotlinx.android.synthetic.main.item_position_gender.view.*
import kotlinx.android.synthetic.main.item_position_group.view.*
import kotlinx.android.synthetic.main.item_position_rv.view.*
import kotlinx.android.synthetic.main.item_position_team.view.*

class TablePositionAdapter(var groups: List<Group>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //var sports: ArrayList<Sport> = ArrayList(groupsString.deportes)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            Group.TEAM_ITEM -> {
                val view = inflater.inflate(R.layout.item_position_rv, parent, false) as ViewGroup
                TeamViewHolder(view)
            }
            Group.GROUP_ITEM -> {
                val view = inflater.inflate(R.layout.item_position_group, parent, false) as ViewGroup
                GroupViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_position_gender, parent, false) as ViewGroup
                GenderViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return groups[position].type
    }


    override fun getItemCount(): Int {
        return  groups.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val group = groups[position]
        if (group.type == Group.TEAM_ITEM) {
            val h = holder as TeamViewHolder
            val adapterTeams = TeamTablePositionAdapter(group.teams)
            h.view.rvTeam.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            h.view.rvTeam.adapter = adapterTeams
        } else if (group.type == Group.GENDER_ITEM) {
            val h = holder as GenderViewHolder
            h.view.tvPositionGender.text = group.genero
        }else if (group.type == Group.GROUP_ITEM) {
            val h = holder as GroupViewHolder
            h.view.tvPositionGroup.text = group.grupo
        }
    }

    class TeamViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}
    class GenderViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}
    class GroupViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}

}