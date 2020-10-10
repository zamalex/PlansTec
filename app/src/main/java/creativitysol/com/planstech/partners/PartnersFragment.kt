package creativitysol.com.planstech.partners

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_partners.view.*

/**
 * A simple [Fragment] subclass.
 */
class PartnersFragment : Fragment() {

    lateinit var v: View
    lateinit var viewModel: PartnersViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_partners, container, false)

        viewModel = ViewModelProvider(this).get(PartnersViewModel::class.java)

        (requireActivity() as MainActivity).showProgress(true)

        viewModel.getPartners()


        viewModel.result.observe(requireActivity(), Observer {
            (requireActivity() as MainActivity).showProgress(false)

            v.partners_rv.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
                adapter = PartnersFullRV(
                    requireActivity(),
                    it
                )
            }
        })

        // Inflate the layout for this fragment
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.partners))
    }

}
