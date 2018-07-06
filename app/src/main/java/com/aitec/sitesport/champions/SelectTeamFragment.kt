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

class SelectTeamFragment : DialogFragment(), DialogInterface.OnShowListener {
    private var btn_select: Button? = null
    private var ib_close: ImageButton? = null
    private var nump: NumberPicker? = null
    //private var callback: OnSelectTeamListener? = null

    lateinit var teams: ArrayList<String>
    var value = 0
    var entity = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teams = arguments!!.getStringArrayList(PARAM_TEAMS)
        value = arguments!!.getInt(PARAM_VALUE)
        entity = arguments!!.getString(PARAM_ENTITY)
        teams.sort()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragmente_select_distance, null)
        btn_select = view.btn_select
        ib_close = view.ib_back
        view.tv_title.text = "Seleccionar $entity"
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
                if (parentFragment is ChampionShipActivity.CalendarFragment)
                    (parentFragment as ChampionShipActivity.CalendarFragment).onTeamSelect(nump!!.displayedValues[nump!!.value], nump!!.value)
                else if (parentFragment is TablePositionsFragment) {
                    (parentFragment as TablePositionsFragment).onTeamSelect(nump!!.displayedValues[nump!!.value], nump!!.value)
                }
                dismiss()
            }
            ib_close!!.setOnClickListener {
                dismiss()
            }
        }
    }

    /*
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSelectTeamListener) {
            callback = context
        } else {
            throw  RuntimeException(context.toString()
                    + " must implement OnRecoveryPasswordListener");
        }
    }

    override fun onDetach() {
        super.onDetach();
        callback = null;
    }

*/
    companion object {
        const val PARAM_TEAMS = "TEAM"
        const val PARAM_VALUE = "VALUE"
        const val PARAM_ENTITY = "ENTIDAD"
        fun newInstance(array: ArrayList<String>, value: Int, entity: String): SelectTeamFragment {
            var fragment = SelectTeamFragment()
            val args = Bundle()
            args.putStringArrayList(PARAM_TEAMS, array)
            args.putInt(PARAM_VALUE, value)
            args.putString(PARAM_ENTITY, entity)
            fragment.arguments = args
            return fragment
        }
    }

    /*
    interface OnSelectTeamListener {
        fun onTeamSelect(team: String, valueTeam: Int)
        fun onTeamSelect(groupsString: String, value: Int)
    }
    */


}
