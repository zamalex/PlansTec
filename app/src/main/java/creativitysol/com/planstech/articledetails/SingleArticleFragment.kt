package creativitysol.com.planstech.articledetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.databinding.FragmentSingleArticleBinding
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.presentation.viewmodel.AddToFavouritesViewModel
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_single_article.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SingleArticleFragment : Fragment() {

    lateinit var v: View
    lateinit var binding: FragmentSingleArticleBinding
    lateinit var viewModel: ArticleViewModel
    private lateinit var articleId: String
    private val addToFavouritesViewModel by viewModel<AddToFavouritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_article, container, false
        )

        v = binding.root

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        binding.model = viewModel.article


        if (arguments != null) {
            (requireActivity() as MainActivity).showProgress(true)
            arguments!!.getString("id").let { articleId ->
                viewModel.getArticle(articleId!!)
                this.articleId = articleId

            }
        }
        viewModel.article.observe(requireActivity(), Observer {


            if (isAdded) {

                if (it.data.fav.equals("1"))
                    v.img_add_remove_fav.setImageResource(R.drawable.saved)
                else v.img_add_remove_fav.setImageResource(R.drawable.unsaved)


                (requireActivity() as MainActivity).showProgress(false)

                if (it!!.data.image != null && !it!!.data.image.isEmpty())
                    Picasso.get().load(it!!.data.image).fit().centerCrop().into(v.a_img)
                if (it.data != null && it.data.title != null)
                    (activity as MainActivity).setTitle(it.data.title)
            }
        })

        v.img_add_remove_fav.setOnClickListener {
            addToFavouritesViewModel.addToFavourites(
                TrainingBody(
                    type = "article", trainingId = articleId.toInt()
                )
            )
        }
        addToFavouritesViewModel.trainingResults.observe(viewLifecycleOwner, {
            Snackbar.make(v.img_add_remove_fav, it.message, Snackbar.LENGTH_SHORT).show()
            if (it.data.fav.equals("1"))
                v.img_add_remove_fav.setImageResource(R.drawable.saved)
            else v.img_add_remove_fav.setImageResource(R.drawable.unsaved)
        })

        addToFavouritesViewModel.error.observe(viewLifecycleOwner, {
            Snackbar.make(v.img_add_remove_fav, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
        })

        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("عنوان المقال")
    }
}
