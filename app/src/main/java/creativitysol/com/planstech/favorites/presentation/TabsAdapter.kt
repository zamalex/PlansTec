package creativitysol.com.planstech.favorites.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import creativitysol.com.planstech.articles.ArticlesFragment
import creativitysol.com.planstech.courses.CoursesFragment
import creativitysol.com.planstech.home.HomeFragment

@Deprecated("Unused")
class TabsAdapter(fm: FragmentManager?, var mNumOfTabs: Int) :
    FragmentStatePagerAdapter(fm!!) {
    override fun getCount(): Int {
        return mNumOfTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ArticlesFragment()
            }
            1 -> {
                CoursesFragment()
            }

            else -> HomeFragment()
        }
    }

}