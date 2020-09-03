package creativitysol.com.planstech.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articledetails.ArticleViewModel
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import kotlinx.android.synthetic.main.fragment_articles.view.rv_articles_full

/**
 * A simple [Fragment] subclass.
 */
class ArticlesFragment : Fragment(),
    ArticleListener {
    lateinit var v: View

    lateinit var viewModel: ArticlesViewModel
    lateinit var adapter: ArticleFullRV


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_articles, container, false)

        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)


        (requireActivity() as MainActivity).showProgress(true)

        viewModel.getArticles()

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


        viewModel.articles.observe(requireActivity(), Observer {
            if (isAdded) {
                (requireActivity() as MainActivity).showProgress(false)

                adapter.setArtiocles(it)
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

}
