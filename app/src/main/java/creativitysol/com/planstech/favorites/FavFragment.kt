package creativitysol.com.planstech.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_fav.view.*

/**
 * A simple [Fragment] subclass.
 */
class FavFragment : Fragment() {
    lateinit var v:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        v = inflater.inflate(R.layout.fragment_fav, container, false)
        // Inflate the layout for this fragment



        v.tab_layout.addTab( v.tab_layout.newTab().setText("مقالات"))
        v.tab_layout.addTab( v.tab_layout.newTab().setText("دورات/ورش عمل"))

        v.tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val tabsAdapter =
            TabsAdapter(
                childFragmentManager,
                v.tab_layout.tabCount
            )
        v.view_pager.adapter = tabsAdapter
        v.view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener( v.tab_layout))
        v.tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                v.view_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("مفضلاتي")
    }
}
