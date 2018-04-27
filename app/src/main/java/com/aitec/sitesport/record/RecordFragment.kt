package com.aitec.sitesport.record

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aitec.sitesport.R
import com.aitec.sitesport.profile.ui.ProfileActivity
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvInfo.text = "Próximamente... " + String(Character.toChars(ProfileActivity.EMOTICON_EYE))
    }

    companion object {
        fun newInstance(): RecordFragment {
            val fragment = RecordFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
