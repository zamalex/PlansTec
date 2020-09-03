package creativitysol.com.planstech.articledetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.databinding.FragmentSingleArticleBinding
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_single_article.view.*

/**
 * A simple [Fragment] subclass.
 */
class SingleArticleFragment : Fragment() {

    lateinit var v: View

    lateinit var binding: FragmentSingleArticleBinding

    lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_article, container, false)

        v = binding.root

        binding.lifecycleOwner=this

        viewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        binding.model=viewModel.article


        if (arguments != null) {
            (requireActivity() as MainActivity).showProgress(true)
            viewModel.getArticle(arguments!!.getString("id")!!)
        }
        viewModel.article.observe(requireActivity(), Observer {


            if (isAdded){
                (requireActivity() as MainActivity).showProgress(false)

                if (it!!.data.image!=null&&!it!!.data.image.isEmpty())
                    Picasso.get().load(it!!.data.image).fit().centerCrop().into(v.a_img)
                if (it.data!=null&&it.data.title!= null)
                    (activity as MainActivity).setTitle(it.data.title)
            }



        })
        // Inflate the layout for this fragment
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("عنوان المقال")
    }
}
