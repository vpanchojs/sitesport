package com.aitec.sitesport.profile.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.aitec.sitesport.R
import kotlinx.android.synthetic.main.fragment_business_hours.view.*
import kotlinx.android.synthetic.main.fragment_rate_night.*

/**
 * Created by Yavac on 9/3/2018.
 */
class RateNightFragment: DialogFragment(), DialogInterface.OnShowListener {

    /*private var tvStartTime : TextView? = null
    private var tvEndTime : TextView? = null
    private var tvPriceForHour : TextView? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_rate_night, null)
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
        fun newInstance(): RateNightFragment {
            val fragment = RateNightFragment()
            return fragment
        }
    }
}