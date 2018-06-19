package com.aitec.sitesport.profileEnterprise.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Dia
import com.aitec.sitesport.profileEnterprise.ui.adapter.TableTimeAdapter
import kotlinx.android.synthetic.main.fragment_table_time.view.*


class TableTimeFragment : DialogFragment(){

    var listTableTime: List<Dia> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listTableTime = arguments!!.getParcelableArrayList("tableTime")!!
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        var view: View = activity!!.layoutInflater.inflate(R.layout.layout_info_textview, null)

        if(listTableTime.isNotEmpty()) {
            view = activity!!.layoutInflater.inflate(R.layout.fragment_table_time, null)
            builder.setView(view)
            val adapter = TableTimeAdapter(listTableTime)
            view.rvInfoService.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
            view.rvInfoService.adapter = adapter
            view.imgBackground.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.icon_transparent),
                    android.graphics.PorterDuff.Mode.MULTIPLY)
        }

        builder.setView(view)

        view.ib_back.setOnClickListener {
            this.dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    companion object {
        fun newInstance(tableTime: List<Dia>): TableTimeFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("tableTime", ArrayList(tableTime))
            val fragment = TableTimeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}