package creativitysol.com.planstech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.stages.StagesViewModel
import creativitysol.com.planstech.stages.model.StagesModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_expansion.view.*


class ExpansionFragment : Fragment() {

    lateinit var v:View

    lateinit var viewModel:StagesViewModel

    val loginModel by lazy {
        Paper.book().read("login",LoginModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v=inflater.inflate(R.layout.fragment_expansion, container, false)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(StagesViewModel::class.java)


        (activity as MainActivity).showProgress(true)
        viewModel.getStagesOfPackage("Bearer ${loginModel.data.token}",arguments!!.getString("id")!!)


        viewModel.stagesResponse.observe(viewLifecycleOwner, Observer {
            if (isAdded){
                (activity as MainActivity).showProgress(false)

                if (it!=null){
                    if (it.success){
                        v.expansion_rv.apply {
                            layoutManager = LinearLayoutManager(requireActivity())
                            adapter = ExpansionAdapter(requireActivity(),
                                it.data as ArrayList<StagesModel.Data>
                            )
                        }
                    }
                }
            }

        })


        // Inflate the layout for this fragment
        return v
    }


}