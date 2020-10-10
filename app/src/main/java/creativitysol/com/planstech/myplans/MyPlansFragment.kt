package creativitysol.com.planstech.myplans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.packages.PlanViewModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_my_plans.view.*


class MyPlansFragment : Fragment() {

    lateinit var v: View
    lateinit var viewModel: PlanViewModel
    val loginModel: LoginModel by lazy {
        Paper.book().read("login", LoginModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_my_plans, container, false)

        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(PlanViewModel::class.java)

        ((activity as MainActivity)).showProgress(true)
        viewModel.getMyPlans("Bearer ${loginModel.data.token}")

        viewModel.myplans.observe(viewLifecycleOwner, Observer {
            if (isAdded) {
                ((activity as MainActivity)).showProgress(false)

                if (it != null) {
                    if (it.success) {
                        v.myplansrv.apply {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = MyPlansAdapter(requireActivity(), it)
                        }
                    }
                }
            }
        })
        // Inflate the layout for this fragment
        return v
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.myplans))
    }

}