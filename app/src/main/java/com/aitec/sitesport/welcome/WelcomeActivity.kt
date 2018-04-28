package com.aitec.sitesport.welcome

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.main.ui.MainActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_welcome)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter

        tabs.setupWithViewPager(container)
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        container.addOnPageChangeListener(this)

        btn_omit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return PlaceholderFragment.newInstance("Encuentra", "Sitios deportivos mediante filtros para una busqueda eficiente", R.drawable.ic_map_black_24dp)
                }
                1 -> {
                    return PlaceholderFragment.newInstance("Visualiza", "Información relevante sobre el sitio deportivo de tu preferencia", R.drawable.ic_visibility_black_24dp)
                }
                2 -> {
                    return PlaceholderFragment.newInstance("Reserva", "En el centro deportivo de preferencia mediante pagos electrónicos (PROXIMAMENTE).", R.drawable.ic_credit_card_black_24dp)
                }
                else -> {
                    return PlaceholderFragment.newInstance("Encuentra", "Sitios deportivos mediante filtros para una buesqueda eficiente", R.drawable.ic_map_black_24dp)
                }
            }

        }

        override fun getCount(): Int {
            return 3
        }
    }

    class PlaceholderFragment : Fragment() {


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_welcome, container, false)

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            tv_title.text = arguments!!.getString(ARG_TITLE)
            tv_message.text = arguments!!.getString(ARG_MESSAGE)
            iv_icon.setImageDrawable(ContextCompat.getDrawable(context!!, arguments!!.getInt(ARG_ICONO)))
        }

        companion object {

            const val ARG_TITLE = "titulo"
            const val ARG_MESSAGE = "mensaje"
            const val ARG_ICONO = "icono"


            fun newInstance(title: String, message: String, icono: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putString(ARG_TITLE, title)
                args.putString(ARG_MESSAGE, message)
                args.putInt(ARG_ICONO, icono)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
