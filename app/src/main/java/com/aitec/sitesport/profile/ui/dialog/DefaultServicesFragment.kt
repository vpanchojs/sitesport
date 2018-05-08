package com.aitec.sitesport.profile.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import com.aitec.sitesport.R
import kotlinx.android.synthetic.main.fragment_default_services.view.*

class DefaultServicesFragment : DialogFragment() {

    var title: String? = ""
    var info: String? = ""
    var idIcon: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments!!.getString("title")
        info = arguments!!.getString("info")
        idIcon = arguments!!.getInt("idIcon")
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_default_services, null)
        builder.setView(view)

        view.ib_back.setOnClickListener {
            this.dismiss()
        }

        view.tvTitleService.text = title
        view.rvInfoService.text = info
        val icon = ContextCompat.getDrawable(activity!!, idIcon!!)
        view.imgBackground.setImageDrawable(icon)
        view.imgBackground.drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.icon_transparent, null), PorterDuff.Mode.SRC_IN)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }


    companion object {
        fun newInstance(title: String, info: String, idIcon: Int): DefaultServicesFragment {
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("info", info)
            bundle.putInt("idIcon", idIcon)
            val fragment = DefaultServicesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}