package creativitysol.com.planstech.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import creativitysol.com.planstech.EndlessRecyclerViewScrollListener
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articledetails.ArticleViewModel
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import creativitysol.com.planstech.home.model.ArticlesModel
import kotlinx.android.synthetic.main.fragment_articles.view.rv_articles_full
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class ArticlesFragment : Fragment(),
    ArticleListener {
    lateinit var v: View
    var page =1
    lateinit var viewModel: ArticlesViewModel
    lateinit var adapter: ArticleFullRV


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_articles, container, false)
        page=1
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        viewModel.articles = MutableLiveData()

        (requireActivity() as MainActivity).showProgress(true)


        adapter = ArticleFullRV(
            requireActivity(),
            this@ArticlesFragment
        )

        v.rv_articles_full.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                LinearLayoutManager.VERTICAL
            ).apply {
                reverseLayout = false
            }

            adapter = this@ArticlesFragment.adapter

        }
        adapter.articlesModel.clear()
        v.rv_articles_full.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
            override fun getLayoutManager(): RecyclerView.LayoutManager {
                return v.rv_articles_full.layoutManager!!

            }

            override fun onLoadMore() {
                if (page >= 1) {
                    page++
                    viewModel.getArticles(page)

                }
            }
        })

        viewModel.getArticles(page)

        viewModel.articles.observe(requireActivity(), Observer {
            if (isAdded) {
                (requireActivity() as MainActivity).showProgress(false)

                adapter.setArtiocles(it.data.articles as ArrayList<ArticlesModel.Data.Article>)
            }
        })


        // Inflate the layout for this fragment
        return v
    }

    override fun onArticleClick(id: String) {

        var arg: Bundle = Bundle().apply {
            putString("id", id)
        }

        (activity as MainActivity).fragmentStack.push(
            SingleArticleFragment().apply { arguments = arg }
        )

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.articles))

    }

}
