package creativitysol.com.planstech.favorites.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import creativitysol.com.planstech.favorites.presentation.FavouritesArticlesFragment
import creativitysol.com.planstech.favorites.presentation.FavouritesTrainingFragment

class FavouritesPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavouritesArticlesFragment()
            else -> FavouritesTrainingFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "مقالات"
        else-> "دورات/ورش عمل"
        }
    }
}