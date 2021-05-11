package creativitysol.com.planstech.packages

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articles.ArticlesFragment
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.packages.model.PlanModel
import creativitysol.com.planstech.payment.OnlinePaymentFragment
import creativitysol.com.planstech.payment.PaymentOptionsFragment
import creativitysol.com.planstech.register.model.RegisterModel
import creativitysol.com.planstech.tradio.TRVAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_packages.view.*
import kotlinx.android.synthetic.main.fragment_packages.view.trv
import kotlinx.android.synthetic.main.fragment_t_radio.view.*
import kotlinx.android.synthetic.main.start_dialog.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * A simple [Fragment] subclass.
 */
class PackagesFragment : Fragment(), TRVAdapter.radioClick {
    var map:HashMap<String, RequestBody> = HashMap<String, RequestBody>()

    lateinit var dialog : Dialog
    lateinit var v: View
    lateinit var adapter: TRVAdapter
    lateinit var selectedPlan: PlanModel.Data
     var selectedID: Int=0
    lateinit var viewModel: PlanViewModel
    val loginModel: LoginModel by lazy {
        Paper.book().read("login", LoginModel())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_packages, container, false)


        var log: LoginModel = Paper.book().read("login", LoginModel())

        dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.start_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window? = dialog.getWindow()
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)

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
           if (selectedPlan.is_subscribed==1){
               Toast.makeText(requireActivity(),"already subscribed", Toast.LENGTH_SHORT).show()

              return@setOnClickListener

           }

            dialog.show()

            dialog.done.setOnClickListener {
                dialog.dismiss()
                if (selectedPlan.is_free){
                    val payment_method: RequestBody = RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        "online")


                    val selected: RequestBody = RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        selectedID.toString())




                    map.put("payment_method", payment_method)
                    map.put("item_id", selected)


                    (activity as MainActivity).showProgress(true)


                    viewModel.subscribeToPackage("Bearer ${loginModel.data.token}",null,map)


                    return@setOnClickListener
                }

                val b:Bundle = Bundle()
                b.putInt("id",selectedID)
                (activity as MainActivity).fragmentStack.push(PaymentOptionsFragment().apply {
                    arguments = b
                })
            }



        }


        viewModel.subscribeResponse.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showProgress(false)

            if (it!=null){
                if (it.success) {

                    Toast.makeText(requireActivity()," subscribed", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).fragmentStack.pop()
                }
            }
        })


        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.planss))
    }

    override fun onRadio(position: Int, model: PlanModel.Data) {

        selectedPlan = model
        selectedID = model.id

    }

}
