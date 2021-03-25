package creativitysol.com.planstech.courses

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
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import creativitysol.com.planstech.coursedetails.SingleCourseFragment
import creativitysol.com.planstech.home.model.TrainingModel
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class CoursesFragment : Fragment(),
    CourseListener {
    var page=1
    lateinit var v:View
    lateinit var adapter:CoursesFullRV
    lateinit var viewModel: CourseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v=  inflater.inflate(R.layout.fragment_courses, container, false)
        page =1

        viewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        viewModel.trainings = MutableLiveData()

        (requireActivity() as MainActivity).showProgress(true)


        adapter = CoursesFullRV(requireActivity(),this)
        v.rv_courses.apply {
            layoutManager = StaggeredGridLayoutManager(2,
                LinearLayoutManager.VERTICAL).apply {
                reverseLayout=false
            }
            adapter= this@CoursesFragment.adapter
        }

        v.rv_courses.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
            override fun getLayoutManager(): RecyclerView.LayoutManager {
                return v.rv_courses.layoutManager!!

            }

            override fun onLoadMore() {
                if (page >= 1) {
                    page++
                    viewModel.getArticles(page)

                }
            }
        })

        viewModel.getArticles(page)

        viewModel.trainings.observe(requireActivity(), Observer {
            if (isAdded){
                (requireActivity() as MainActivity).showProgress(false)

                adapter.setList(it.data.trainings as ArrayList<TrainingModel.Data.Training>)

            }


        })


        return v
    }

    override fun onCourseClick(id: String) {

        var arg:Bundle = Bundle().apply {
            putString("id",id)
        }

        (activity as MainActivity).fragmentStack.push(
            SingleCourseFragment().apply { arguments=arg }
        )



    }

    override fun onStart() {
        super.onStart()
      //  (activity as MainActivity).setTitle("دورات/ورش عمل")
    }

}
