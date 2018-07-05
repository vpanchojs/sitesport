package com.aitec.sitesport.champions

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import android.widget.NumberPicker
import com.aitec.sitesport.R
import kotlinx.android.synthetic.main.fragmente_select_distance.view.*
import java.util.*

class SelectDateFragment : DialogFragment(), DialogInterface.OnShowListener {
    private var btn_select: Button? = null
    private var ib_close: ImageButton? = null
    private var nump: NumberPicker? = null

    lateinit var teams: ArrayList<String>
    //var dateEspanol = ArrayList<String>()
    var value = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teams = arguments!!.getStringArrayList(PARAM_TEAMS)
        value = arguments!!.getInt(PARAM_VALUE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragmente_select_date, null)
        btn_select = view.btn_select
        ib_close = view.ib_back
        nump = view.np
        nump!!.displayedValues = teams.toTypedArray()
        nump!!.minValue = 0
        nump!!.maxValue = teams.size - 1
        nump!!.value = value
        nump!!.isClickable = false
        builder.setView(view)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setOnShowListener(this)

        return dialog
    }


    override fun onShow(dialog: DialogInterface?) {
        val dialogo = getDialog() as AlertDialog
        if (dialogo != null) {
            btn_select!!.setOnClickListener {
                (parentFragment as ChampionShipActivity.CalendarFragment).onDateSelect(teams[nump!!.value], nump!!.value)
                dismiss()
            }
            ib_close!!.setOnClickListener {
                dismiss()
            }
        }
    }


    companion object {
        const val PARAM_TEAMS = "TEAM"
        const val PARAM_VALUE = "VALUE"
        fun newInstance(array: ArrayList<String>, value: Int): SelectDateFragment {
            var fragment = SelectDateFragment()
            val args = Bundle()
            args.putStringArrayList(PARAM_TEAMS, array)
            args.putInt(PARAM_VALUE, value)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnSelectTeamListener {
        fun onTeamSelect(team: String, value: Int)
    }


}
