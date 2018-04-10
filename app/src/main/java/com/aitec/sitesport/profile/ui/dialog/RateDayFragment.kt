package com.aitec.sitesport.profile.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Precio
import kotlinx.android.synthetic.main.fragment_rate_day.*
import kotlinx.android.synthetic.main.fragment_rate_day.view.*

/**
 * Created by Yavac on 9/3/2018.
 */
class RateDayFragment : DialogFragment(), DialogInterface.OnShowListener {

    /*private var tvStartTime : TextView? = null
    private var tvEndTime : TextView? = null
    private var tvPriceForHour : TextView? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_rate_day, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setOnShowListener(this)
        view.ib_back.setOnClickListener {
            this.dismiss()
        }
        //DrawableCompat.setTint(view.imgBackground.getDrawable(), ContextCompat.getColor(activity!!, R.color.icon_transparent));
        view.imgBackground.setColorFilter(ContextCompat.getColor(activity!!, R.color.icon_transparent),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        view.tvPriceForHour.text = ("$ " + (arguments!!.getParcelableArrayList<Precio>("precio"))[0].dia)

        return dialog
    }

    private fun setTextStarTime(time : String){
        tvStartTime.text = time
    }

    private fun setTextEndTime(time : String){
        tvEndTime.text = time
    }

    private fun setTextPriceForHour(price : String){
        tvPriceForHour.text = price
    }

    override fun onShow(dialog: DialogInterface?) {
        val dialogo = getDialog() as AlertDialog
    }

    companion object {
        fun newInstance(precio: List<Precio>): RateDayFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("precio", ArrayList(precio))
            val fragment = RateDayFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}