package creativitysol.com.planstech.coursedetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.SliderAnimations
import creativitysol.com.planstech.R
import creativitysol.com.planstech.databinding.FragmentSingleCourseBinding
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.presentation.viewmodel.AddToFavouritesViewModel
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_single_course.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class SingleCourseFragment : Fragment() {

    lateinit var binding: FragmentSingleCourseBinding
    lateinit var v: View
    lateinit var viewModel: TrainingViewModel

    private lateinit var trainingId: String
    private val addToFavouritesViewModel by viewModel<AddToFavouritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_course, container, false
        )

        v = binding.root


        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)


        binding.model = viewModel.course

        if (arguments != null) {
            (requireActivity() as MainActivity).showProgress(true)
            arguments!!.getString("id").let {
                viewModel.getcourse(it!!)
                trainingId = it
            }
        }

        viewModel.course.observe(requireActivity(), Observer {

            if (isAdded) {
                (requireActivity() as MainActivity).showProgress(false)

                (activity as MainActivity).setTitle(it.data.title)

                var aray: ArrayList<String> = ArrayList()

                for (i in 0 until it.data.imagesGallary.size) {
                    aray.add(it.data.imagesGallary[i].image)
                }

                var adapter: SliderAdapterExample = SliderAdapterExample(requireActivity())
                v.flipper_layout.setSliderAdapter(adapter)
                adapter.renewItems(aray)

                v.flipper_layout.startAutoCycle();
                v.flipper_layout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                v.flipper_layout.indicatorSelectedColor = Color.WHITE;
                v.flipper_layout.indicatorUnselectedColor = Color.GRAY;
            }
        })

        v.img_add_remove_fav.setOnClickListener {
            addToFavouritesViewModel.addToFavourites(
                TrainingBody(
                    type = "training", trainingId = trainingId.toInt()
                )
            )
        }
        addToFavouritesViewModel.trainingResults.observe(viewLifecycleOwner, {
            Snackbar.make(v.img_add_remove_fav, it.message, Snackbar.LENGTH_SHORT).show()
            if (it.data.isNullOrEmpty())
                v.img_add_remove_fav.setImageResource(R.drawable.unsaved)
            else v.img_add_remove_fav.setImageResource(R.drawable.saved)
        })

        addToFavouritesViewModel.error.observe(viewLifecycleOwner, {
            Snackbar.make(v.img_add_remove_fav, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
        })


        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("عنوان الدورة")
    }

}
