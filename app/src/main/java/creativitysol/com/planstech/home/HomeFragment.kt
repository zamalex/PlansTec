package creativitysol.com.planstech.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.*
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import creativitysol.com.planstech.articles.ArticleListener
import creativitysol.com.planstech.articles.ArticlesFragment
import creativitysol.com.planstech.consultation_request_questions.ConsRequestFragment
import creativitysol.com.planstech.coursedetails.SingleCourseFragment
import creativitysol.com.planstech.courses.CourseListener
import creativitysol.com.planstech.courses.CoursesFragment
import creativitysol.com.planstech.home.model.ArticlesModel
import creativitysol.com.planstech.home.model.TrainingModel
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.opinions.OpinionsFragment
import creativitysol.com.planstech.opinions.OpinionsRV
import creativitysol.com.planstech.packages.PackagesFragment
import creativitysol.com.planstech.register.model.RegisterModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(),
    ArticleListener, CourseListener {

    lateinit var viewModel: HomeViewModel

    var v: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_home, container, false)
            var login: LoginModel = Paper.book().read("login", LoginModel())

            if (!login.data.token.isEmpty()){
                (requireActivity() as MainActivity).setLogMenu()
            }

            viewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

            viewModel.getTrainings()
            viewModel.getArticles()
            viewModel.getReviews()
            viewModel.getStats()


            var user: RegisterModel = Paper.book().read("user", RegisterModel())



            viewModel.trainings.observe(requireActivity(), object : Observer<TrainingModel> {
                override fun onChanged(t: TrainingModel?) {

                    if (isAdded) {
                        v!!.rv_courses.apply {
                            layoutManager =
                                LinearLayoutManager(
                                    requireActivity(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            adapter = CoursesRV(
                                requireActivity(),
                                this@HomeFragment,
                                t!!
                            )
                        }
                    }

                }
            })



            viewModel.articles.observe(requireActivity(), object : Observer<ArticlesModel> {
                override fun onChanged(t: ArticlesModel?) {
                    if (isAdded) {
                        v!!.rv_articles.apply {
                            layoutManager =
                                LinearLayoutManager(
                                    requireActivity(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            adapter = ArticlesRV(
                                requireActivity(),
                                this@HomeFragment,
                                t!!
                            )
                        }
                    }
                }
            })



            viewModel.reviews.observe(requireActivity(), Observer {
                if (isAdded) {
                    v!!.rv_opinions.apply {
                        layoutManager =
                            LinearLayoutManager(
                                requireActivity(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        adapter =
                            OpinionsRV(requireActivity()).apply { setReviews(it) }


                    }
                }
            })



            viewModel.stats.observe(requireActivity(), Observer {
                if (isAdded) {
                    v!!.sclents.text = it.data.clientsCount.toString() + "\nعميل"
                    v!!.scourses.text = it.data.courcesCount.toString() + "\nدورة"
                    v!!.splans.text = it.data.plansCount.toString() + "\nخطة"
                }

            })




            v!!.all_articles.setOnClickListener {
                (activity as MainActivity).fragmentStack.push(ArticlesFragment())
            }
            v!!.all_courses.setOnClickListener {
                (activity as MainActivity).fragmentStack.push(CoursesFragment())
            }
            v!!.all_opinions.setOnClickListener {
                (activity as MainActivity).fragmentStack.push(OpinionsFragment())
            }

            v!!.request_btn.setOnClickListener {
                (activity as MainActivity).fragmentStack.push(ConsRequestFragment())

            }

            v!!.join_btn.setOnClickListener {
                (activity as MainActivity).fragmentStack.push(PackagesFragment())

            }
        }





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

    override fun onCourseClick(id: String) {

        var arg: Bundle = Bundle().apply {
            putString("id", id)
        }

        (activity as MainActivity).fragmentStack.push(
            SingleCourseFragment().apply { arguments = arg }
        )


    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("الرئيسية")
    }
}
