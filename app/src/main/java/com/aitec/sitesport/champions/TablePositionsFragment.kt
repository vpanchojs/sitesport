package com.aitec.sitesport.champions

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aitec.sitesport.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TablePositionsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TablePositionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TablePositionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_positions, container, false)
    }




    companion object {

        @JvmStatic
        fun newInstance() =
                TablePositionsFragment().apply {

                }
    }
}
