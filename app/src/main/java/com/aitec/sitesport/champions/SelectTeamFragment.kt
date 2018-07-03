package com.aitec.sitesport.champions

import android.app.Dialog
import android.content.Context
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
    private var callback: OnSelectTeamListener? = null

    lateinit var teams: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teams = arguments!!.getStringArrayList(PARAM_TEAMS)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragmente_select_distance, null)
        btn_select = view.btn_select
        ib_close = view.ib_back
        nump = view.np
        nump!!.displayedValues = teams.toTypedArray()
        nump!!.minValue = 0
        nump!!.maxValue = teams.size - 1

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
                callback!!.onTeamSelect(nump!!.displayedValues[nump!!.value])
                dismiss()
            }
            ib_close!!.setOnClickListener {
                dismiss()
            }
        }
    }

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

    companion object {
        const val PARAM_TEAMS = "TEAM"
        fun newInstance(array: ArrayList<String>): SelectTeamFragment {
            var fragment = SelectTeamFragment()
            val args = Bundle()
            args.putStringArrayList(PARAM_TEAMS, array)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnSelectTeamListener {
        fun onTeamSelect(team: String)
    }


}
