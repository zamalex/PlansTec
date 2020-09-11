package creativitysol.com.planstech.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import creativitysol.com.planstech.favorites.presentation.adapter.ArticlesAdapter
import creativitysol.com.planstech.favorites.presentation.viewmodel.ListFavouritesViewModel
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class FavouritesArticlesFragment : Fragment() {
    lateinit var v: View
    private val listFavouritesViewModel by viewModel<ListFavouritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_articles, container, false)
        (requireActivity() as MainActivity).showProgress(true)
        listFavouritesViewModel.getAllFavouritesByType("article")
        listFavouritesViewModel.trainingResults.observe(viewLifecycleOwner, {
            (requireActivity() as MainActivity).showProgress(false)
            v.rv_articles_full.apply {
                layoutManager = StaggeredGridLayoutManager(
                    2,
                    LinearLayoutManager.VERTICAL
                ).apply { reverseLayout = false }
                adapter = ArticlesAdapter(it)
                { articleId ->
                    val arg: Bundle = Bundle().apply { putString("id", "$articleId") }
                    (activity as MainActivity).fragmentStack.push(
                        SingleArticleFragment().apply { arguments = arg })
                }
            }
        })

        listFavouritesViewModel.error.observe(viewLifecycleOwner, {
            (requireActivity() as MainActivity).showProgress(false)
            val errorMessage = if (it is UnknownHostException)
                getString(R.string.no_interet_connection)
            else getString(R.string.general_issue)
            Snackbar.make(v.rv_articles_full, errorMessage, Snackbar.LENGTH_SHORT)
                .show()
        })
        return v
    }
}