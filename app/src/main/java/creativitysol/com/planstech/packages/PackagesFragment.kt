package creativitysol.com.planstech.packages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articles.ArticlesFragment
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.packages.model.PlanModel
import creativitysol.com.planstech.payment.PaymentOptionsFragment
import creativitysol.com.planstech.register.model.RegisterModel
import creativitysol.com.planstech.tradio.TRVAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_packages.view.*
import kotlinx.android.synthetic.main.fragment_packages.view.trv
import kotlinx.android.synthetic.main.fragment_t_radio.view.*

/**
 * A simple [Fragment] subclass.
 */
class PackagesFragment : Fragment(), TRVAdapter.radioClick {

    lateinit var v: View
    lateinit var adapter: TRVAdapter
    lateinit var selectedPlan: PlanModel.Data
     var selectedID: Int=0
    lateinit var viewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_packages, container, false)


        var log: LoginModel = Paper.book().read("login", LoginModel())


        viewModel = ViewModelProvider(this).get(PlanViewModel::class.java)

        if (!log.data.token.isEmpty()) {
            (requireActivity() as MainActivity).showProgress(true)

            viewModel.getTrainings("Bearer " + log.data.token)
        }

        viewModel.plans.observe(requireActivity(), Observer {
            if (isAdded) {

                (requireActivity() as MainActivity).showProgress(false)

                adapter = TRVAdapter(requireActivity(), this, it)

                v.trv.apply {
                    adapter = this@PackagesFragment.adapter
                    layoutManager = LinearLayoutManager(requireActivity())
                }
            }

        })


        v.join_now.setOnClickListener {
            if (selectedID==0){
                return@setOnClickListener
            }
            val b:Bundle = Bundle()
            b.putInt("id",selectedID)
            (activity as MainActivity).fragmentStack.push(PaymentOptionsFragment().apply {
                arguments = b
            })

        }

        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("الخطة/الاشتراك")
    }

    override fun onRadio(position: Int, model: PlanModel.Data) {
        selectedPlan = model
        selectedID = model.id

    }

}
