package com.aitec.sitesport.main.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import com.aitec.sitesport.R
import kotlinx.android.synthetic.main.fragmente_select_distance.view.*

class SelectDistanceFragment : DialogFragment(), DialogInterface.OnShowListener {
    private var btn_recovery: Button? = null
    private var ib_close: ImageButton? = null
    private var tie_email: TextInputEditText? = null
    private var callback: OnSelectDistanceListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        var view = activity!!.layoutInflater.inflate(R.layout.fragmente_select_distance, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setOnShowListener(this)
        view.np.minValue = 1
        view.np.maxValue = 10
        return dialog
    }

    private fun setupFieldsValidation() {
        var fields = ArrayList<TextInputEditText>()
        fields.add(tie_email!!)
    }


    override fun onShow(dialog: DialogInterface?) {
        val dialogo = getDialog() as AlertDialog
        if (dialogo != null) {
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSelectDistanceListener) {
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
        fun newInstance(): SelectDistanceFragment {
            val fragment = SelectDistanceFragment()
            return fragment
        }
    }

    interface OnSelectDistanceListener {
        fun onRecoveryPassword(email: String)
    }


}
