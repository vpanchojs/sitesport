package com.aitec.sitesport.profile.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Dia
import com.aitec.sitesport.entities.enterprise.Horario
import kotlinx.android.synthetic.main.fragment_table_time.view.*

class TableTimeFragment : DialogFragment(), DialogInterface.OnShowListener {

    var listTableTime: List<Dia>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arg : List<Horario> = arguments!!.getParcelableArrayList("tableTime")!!
        listTableTime = arg[0].dias
        Log.e("TableTimeFragment","listTableTime = " + listTableTime.toString())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_table_time, null)
        builder.setView(view)

        view.ib_back.setOnClickListener {
            this.dismiss()
        }

        view.imgBackground.setColorFilter(ContextCompat.getColor(activity!!, R.color.icon_transparent),
                android.graphics.PorterDuff.Mode.MULTIPLY)

        val adapter = AdapterTableTime(listTableTime!!)
        view.rvInfoService.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        view.rvInfoService.adapter = adapter

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setOnShowListener(this)
        return dialog
    }

    override fun onShow(dialog: DialogInterface?) {
        val dialogo = getDialog() as AlertDialog
    }

    companion object {
        fun newInstance(tableTime: List<Horario>): TableTimeFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("tableTime", ArrayList(tableTime))
            val fragment = TableTimeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}