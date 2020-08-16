package creativitysol.com.planstech.coursedetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.smarteist.autoimageslider.SliderAnimations
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.databinding.FragmentSingleCourseBinding
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_single_course.*
import kotlinx.android.synthetic.main.fragment_single_course.view.*


/**
 * A simple [Fragment] subclass.
 */
class SingleCourseFragment : Fragment() {

    lateinit var binding: FragmentSingleCourseBinding


    lateinit var v:View

    lateinit var viewModel: TrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_course, container, false)

        v = binding.root

        binding.lifecycleOwner=this

        viewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)


        binding.model = viewModel.course

        if (arguments!=null)
        viewModel.getcourse(arguments!!.getString("id")!!)
        // Inflate the layout for this fragment


        viewModel.course.observe(requireActivity(), Observer {

            (activity as MainActivity).setTitle(it.data.title)

            var aray:ArrayList<String> = ArrayList()

            for (i in 0 until it.data.imagesGallary.size){
                aray.add(it.data.imagesGallary[i].image)
            }

            var adapter:SliderAdapterExample = SliderAdapterExample(requireActivity())
            v.flipper_layout.setSliderAdapter(adapter)
            adapter.renewItems(aray)

            v.flipper_layout.startAutoCycle();
            v.flipper_layout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            v.flipper_layout.setIndicatorSelectedColor(Color.WHITE);
            v.flipper_layout.setIndicatorUnselectedColor(Color.GRAY);



        })

        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("عنوان الدورة")
    }

}
