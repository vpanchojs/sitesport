package com.aitec.sitesport.profile.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Hora
import kotlinx.android.synthetic.main.fragment_business_hours.view.*

class BusinessHoursFragment : DialogFragment(), DialogInterface.OnShowListener {
    private var tvLunesToViernes : TextView? = null
    private var tvSabadoToDomingo : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_business_hours, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setOnShowListener(this)
        view.ib_back.setOnClickListener {
            this.dismiss()
        }
        view.imgBackground.setColorFilter(ContextCompat.getColor(activity!!, R.color.icon_transparent),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        //view.tvDayTitle.text = (arguments!!.getParcelableArrayList<Horario>("horario"))[0].nombre
        view.tvHourStartEnd.text = ((arguments!!.getParcelableArrayList<Hora>("hora"))[0].inicio
                + " - "  + (arguments!!.getParcelableArrayList<Hora>("hora"))[0].fin)

        return dialog
    }

    private fun setTimeLunesToViernes(time : String){
        tvLunesToViernes?.text = time
    }

    private fun setTimeSabadoToDomingo(time : String){
        tvSabadoToDomingo?.text = time
    }

    override fun onShow(dialog: DialogInterface?) {
        val dialogo = getDialog() as AlertDialog
    }

    companion object {
        /*fun newInstance(hora: List<Hora>, horario: List<Horario>): BusinessHoursFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("hora", ArrayList(hora))
            bundle.putParcelableArrayList("horario", ArrayList(horario))
            val fragment = BusinessHoursFragment()
            fragment.arguments = bundle
            return fragment
        }*/
    }

}