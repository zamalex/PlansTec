package creativitysol.com.planstech.main

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.kaopiz.kprogresshud.KProgressHUD
import creativitysol.com.planstech.CustomizedExceptionHandler
import creativitysol.com.planstech.R
import creativitysol.com.planstech.about.AboutFragment
import creativitysol.com.planstech.articledetails.SingleArticleFragment
import creativitysol.com.planstech.articledetails.model.SingleArticle
import creativitysol.com.planstech.conschat.presentation.ConsultationChatFragment
import creativitysol.com.planstech.coursedetails.SingleCourseFragment
import creativitysol.com.planstech.courses.CoursesFragment
import creativitysol.com.planstech.favorites.presentation.FavouritesFragment
import creativitysol.com.planstech.follow.FollowFragment
import creativitysol.com.planstech.gladtoserve.GladToServeFragment
import creativitysol.com.planstech.helpers.FragmentStack
import creativitysol.com.planstech.home.HomeFragment
import creativitysol.com.planstech.login.LoginFragment
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.myplans.MyPlansFragment
import creativitysol.com.planstech.notifications.presentation.NotificationsFragment
import creativitysol.com.planstech.packages.PackagesFragment
import creativitysol.com.planstech.partners.PartnersFragment
import creativitysol.com.planstech.profile.ProfileFragment
import creativitysol.com.planstech.register.model.RegisterModel
import creativitysol.com.planstech.stagemissions.MissionsFragment
import creativitysol.com.planstech.terms.TermsFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(),
    MenuRV.mainClick {

    lateinit var fragmentStack: FragmentStack;
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    lateinit var loading: KProgressHUD
    lateinit var loglist: ArrayList<String>

    var isLogged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLang("ar")
        setSupportActionBar(toolbar)
        Thread.setDefaultUncaughtExceptionHandler(
            CustomizedExceptionHandler(
                "/mnt/sdcard/"
            )
        )

        fragmentStack = FragmentStack(
            this, supportFragmentManager,
            R.id.main_container
        )

        val nullString: String? = null
        println(nullString.toString())
        setContentView(R.layout.activity_main)
        loading = KProgressHUD.create(this@MainActivity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link




                //    Toast.makeText(this,deepLink!!.getQueryParameter("cid")+" and ${deepLink!!.getQueryParameter("aid")}", Toast.LENGTH_LONG).show()

                    if (deepLink!!.getQueryParameter("cid")!!.toInt()==0){
                        fragmentStack.push(SingleArticleFragment().apply { arguments = Bundle().apply { putString("id",deepLink!!.getQueryParameter("aid")) } })
                    }
                    else{
                        fragmentStack.push(SingleCourseFragment().apply { arguments = Bundle().apply { putString("id",deepLink!!.getQueryParameter("cid")) } })

                    }
                    //Toast.makeText(this,deepLink.toString(),Toast.LENGTH_LONG).show()

                    println("ddd${pendingDynamicLinkData.extensions.toString()}")
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

                // ...
            }
            .addOnFailureListener(this) {
                println("dlink error")
            }



        supportActionBar?.title = ""
        drawer = findViewById(R.id.drawer_layout)
        toggle =
            ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.app_name,
                R.string.app_name
            )

        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //   showToggle(true)



        fragmentStack.replace(HomeFragment())

        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayout.VERTICAL
        )

        logout.setOnClickListener {

            Paper.book().delete("user")
            Paper.book().delete("login")


            this.finish()

            startActivity(Intent(this, SplashScreenActivity::class.java))

        }




        var list: ArrayList<String> = ArrayList()
        loglist = ArrayList()
        loglist.add(getString(R.string.main))
        loglist.add(getString(R.string.favs))
        loglist.add(getString(R.string.plan))
        loglist.add(getString(R.string.consults))
        loglist.add(getString(R.string.candshops))
        loglist.add(getString(R.string.happy))
        loglist.add(getString(R.string.about))


        var login: LoginModel = Paper.book().read("login", LoginModel())





        profile_img.setOnClickListener {
            if (!login.data.token.isEmpty()){
                drawer.closeDrawers()

                fragmentStack.push(ProfileFragment())
            }
        }
        if (login.data.token.isEmpty()) {
            isLogged = false
            logout.visibility = View.GONE
            list.add(getString(R.string.main))
            list.add(getString(R.string.favs))
            list.add(getString(R.string.about))
            list.add(getString(R.string.plan))
            list.add(getString(R.string.consults))
            list.add(getString(R.string.candshops))
            list.add(getString(R.string.successsh))
            list.add(getString(R.string.terms))
            list.add(getString(R.string.happy))
            list.add(getString(R.string.follow))
        } else {
            logout.visibility = View.VISIBLE

            isLogged = true
            list.add(getString(R.string.main))
            list.add(getString(R.string.favs))
            list.add(getString(R.string.plan))
            list.add(getString(R.string.consults))
            list.add(getString(R.string.candshops))
            list.add(getString(R.string.happy))
            list.add(getString(R.string.about))
        }

        drawer_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuRV(
                this@MainActivity,
                list,
                this@MainActivity,
                isLogged
            )


            itemAnimator = DefaultItemAnimator()
        }


        drawer_rv.addItemDecoration(dividerItemDecoration)


        tog.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }


        img_notifications.setOnClickListener {
            fragmentStack.push(NotificationsFragment())
        }
    }

    fun setLang(lang: String) {
        val languageToLoad = lang // your language

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // toggle.syncState()
        //my_toolbar.setNavigationIcon(android.R.drawable.ic_menu_today)

    }

    fun showToggle(show: Boolean) {
        if (show) {
            toolbar.setNavigationIcon(R.drawable.menu)
            // back_btn.visibility = View.GONE
        } else {
            toolbar.setNavigationIcon(null)
            //  back_btn.visibility = View.VISIBLE

        }
    }

    fun showNot(boolean: Boolean) {
        if (boolean)
            img_notifications.visibility = View.VISIBLE
        else
            img_notifications.visibility = View.INVISIBLE

    }

    override fun onMainClick(position: Int) {
        if (isLogged) {

            when (position) {
                0 -> fragmentStack.replace(HomeFragment())
                1 -> fragmentStack.push(FavouritesFragment())
                2 -> fragmentStack.push(MyPlansFragment())
                3 -> fragmentStack.push(ConsultationChatFragment())
                4 -> fragmentStack.push(CoursesFragment())
                5 -> fragmentStack.push(GladToServeFragment())
                6 -> fragmentStack.push(AboutFragment())


            }
        } else {
            when (position) {
                0 -> fragmentStack.replace(HomeFragment())
                1 -> fragmentStack.push(FavouritesFragment())
                2 -> fragmentStack.push(AboutFragment())
                3 -> fragmentStack.push(LoginFragment())
                4 -> fragmentStack.push(LoginFragment())
                5 -> fragmentStack.push(CoursesFragment())
                6 -> fragmentStack.push(PartnersFragment())
                7 -> fragmentStack.push(TermsFragment())
                8 -> fragmentStack.push(GladToServeFragment())
                9 -> fragmentStack.push(FollowFragment())


            }
        }

        drawer.closeDrawers()
    }

    fun setTitle(t: String) {
        title_txt.text = t
    }

    fun showProgress(show: Boolean) {
        if (show)
            loading.show()
        else
            loading.dismiss()

    }

    fun setLogMenu() {
        logout.visibility = View.VISIBLE
        isLogged = true
        drawer_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuRV(
                this@MainActivity,
                loglist,
                this@MainActivity,
                isLogged
            )
        }

    }

}
