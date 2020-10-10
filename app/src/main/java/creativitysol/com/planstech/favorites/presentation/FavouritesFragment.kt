package creativitysol.com.planstech.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import creativitysol.com.planstech.R
import creativitysol.com.planstech.favorites.presentation.adapter.FavouritesPagerAdapter
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_favourites.view.*

class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_favourites, container, false)
        val favouritesPagerAdapter = FavouritesPagerAdapter(childFragmentManager)
        v.tab_layout.setupWithViewPager(v.view_pager)
        v.view_pager.adapter = favouritesPagerAdapter
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.favs))
    }
}
