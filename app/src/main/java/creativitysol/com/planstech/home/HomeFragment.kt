package creativitysol.com.planstech.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esotericsoftware.kryo.util.Util
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import creativitysol.com.planstech.articles.ArticleListener
import creativitysol.com.planstech.articles.ArticlesFragment
import creativitysol.com.planstech.consultation_request_questions.presentation.ConsultationRequestFragment
import creativitysol.com.planstech.coursedetails.SingleCourseFragment
import creativitysol.com.planstech.courses.CourseListener
import creativitysol.com.planstech.courses.CoursesFragment
import creativitysol.com.planstech.home.model.ArticlesModel
import creativitysol.com.planstech.home.model.ReviewsModel
import creativitysol.com.planstech.home.model.TrainingModel
import creativitysol.com.planstech.login.LoginFragment
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
     lateinit var player:SimpleExoPlayer

    lateinit var dataSourceFactory: DataSource.Factory
    lateinit var viewModel: HomeViewModel

    var v: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_home, container, false)
            var login: LoginModel = Paper.book().read("login", LoginModel())


            player = ExoPlayerFactory.newSimpleInstance(activity)
            dataSourceFactory  = DefaultDataSourceFactory(
                    activity,
            "plansTec"
            )
            v!!.vid.setPlayer(player)

           /* val fileName =
                "android.resource://" + activity!!.getPackageName().toString() + "/raw/vvv"

            val video: Uri = Uri.parse(fileName)
*/




            if (!login.data.token.isEmpty()) {
                (requireActivity() as MainActivity).setLogMenu()
            }

            viewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)


            viewModel.getSliderVideo()
            viewModel.getTrainings()
            viewModel.getArticles()
            viewModel.getReviews()
            viewModel.getStats()


            var user: RegisterModel = Paper.book().read("user", RegisterModel())



            viewModel.videoLink.observe(viewLifecycleOwner, Observer {
                if (it!=null&&it.statusCode==200&&!it.data.url.isNullOrEmpty()){
                  /*  v!!.vid.setVideoURI(Uri.parse(it.data))
                    v!!.vid.start()

                    v!!.vid.setOnClickListener {
                       /* if (!v!!.vid.isPlaying) {
                            v!!.vid.start()
                        }*/
                    }
*/


                    val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(it.data.url))

                    player.prepare(videoSource)
                    player.playWhenReady = true
                }
            })

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
                                t!!.data.trainings as ArrayList<TrainingModel.Data.Training>
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
                                t!!.data.articles as ArrayList<ArticlesModel.Data.Article>
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
                            OpinionsRV(requireActivity()).apply { setReviews(it.data.reviews as ArrayList<ReviewsModel.Data.Review>) }


                    }
                }
            })



            viewModel.stats.observe(requireActivity(), Observer {
                if (isAdded&&it!=null&&it.statusCode==200) {
                    v!!.sclents.text = it.data.clientsCount.toString() + "\nعملائنا السعداء"
                    v!!.scourses.text = it.data.teamsCount.toString() + "\nفريقنا"
                    v!!.splans.text = it.data.programsCount.toString() + "\nبرامجنا"
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
                if (login.data.token.isEmpty()){
                    Toast.makeText(activity,"سجل الدخول اولا",Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).fragmentStack.push(LoginFragment())

                    return@setOnClickListener
                }
                (activity as MainActivity).fragmentStack.push(ConsultationRequestFragment())

            }

            v!!.join_btn.setOnClickListener {
                if (login.data.token.isEmpty()){
                    Toast.makeText(activity,"سجل الدخول اولا",Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).fragmentStack.push(LoginFragment())

                    return@setOnClickListener
                }
                (activity as MainActivity).fragmentStack.push(PackagesFragment())

            }
        }





        return v
    }

    override fun onStop() {
        super.onStop()
      //  player.stop(true)
        player.playWhenReady = false

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
        (activity as MainActivity).setTitle(getString(R.string.mianfragment))


    }
}
