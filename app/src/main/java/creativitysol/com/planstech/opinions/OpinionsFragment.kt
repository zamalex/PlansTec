package creativitysol.com.planstech.opinions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.EndlessRecyclerViewScrollListener
import creativitysol.com.planstech.R
import creativitysol.com.planstech.home.model.ReviewsModel
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class OpinionsFragment : Fragment() {
    var page = 1
    lateinit var v:View
    lateinit var viewModel: ReviewsViewModel

    lateinit var adappter: OpinionsRV

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v=inflater.inflate(R.layout.fragment_opinions, container, false)

        viewModel = ViewModelProvider(this).get(ReviewsViewModel::class.java)

        (requireActivity() as MainActivity).showProgress(true)



        adappter = OpinionsRV(requireActivity())

        v.rv_opinions.apply {
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false)
            adapter= this@OpinionsFragment.adappter
        }

        v.rv_opinions.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
            override fun getLayoutManager(): RecyclerView.LayoutManager {
                return v.rv_opinions.layoutManager!!

            }

            override fun onLoadMore() {
                if (page >= 1) {
                    page++
                    viewModel.getReviews(page)

                }
            }
        })

        viewModel.getReviews(page)

        viewModel.reviews.observe(requireActivity(), Observer {
            (requireActivity() as MainActivity).showProgress(false)

            adappter.setReviews(it.data.reviews as ArrayList<ReviewsModel.Data.Review>)
            Log.e("errorrrr",it.toString())

        })



        return v
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.reviewa))
    }
}
