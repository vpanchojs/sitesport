package com.aitec.sitesport.profileEnterprise.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Rate
import com.aitec.sitesport.profileEnterprise.ui.adapter.RateCourtAdapter
import kotlinx.android.synthetic.main.fragment_rate_court.view.*

class RateCourtFragment : DialogFragment() {

    private var listRatesCourt: List<Rate> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listRatesCourt = arguments!!.getParcelableArrayList("ratesCourt")
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_rate_court, null)
        builder.setView(view)

        view.ib_back.setOnClickListener {
            this.dismiss()
        }

        val adapter = RateCourtAdapter(context!!, listRatesCourt)
        view.rvRatesCourt.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        view.rvRatesCourt.adapter = adapter

        view.imgBackground.setColorFilter(ContextCompat.getColor(activity!!, R.color.icon_transparent),
                android.graphics.PorterDuff.Mode.MULTIPLY)

        view.ivSum.setColorFilter(ContextCompat.getColor(activity!!, R.color.colorWhiteTransparent),
                android.graphics.PorterDuff.Mode.MULTIPLY)

        view.ivMoon.setColorFilter(ContextCompat.getColor(activity!!, R.color.colorWhiteTransparent),
                android.graphics.PorterDuff.Mode.MULTIPLY)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    companion object {
        fun newInstance(ratesCourt: MutableList<Rate>): RateCourtFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("ratesCourt", ArrayList<Rate>(ratesCourt))
            val fragment = RateCourtFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}