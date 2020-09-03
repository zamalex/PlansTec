package creativitysol.com.planstech.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.register.RegisterFragment
import creativitysol.com.planstech.register.model.RegisterModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_about.view.*

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v= inflater.inflate(R.layout.fragment_about, container, false)

        var login: LoginModel = Paper.book().read("login", LoginModel())

        if (login.data.token.isEmpty())
            v.create.visibility = View.VISIBLE
        else
            v.create.visibility = View.INVISIBLE

        v.create.setOnClickListener {
            (activity as MainActivity).fragmentStack.push(
                RegisterFragment()
            )
        }
        // Inflate the layout for this fragment
        return v
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("عن التطبيق")
    }
}
