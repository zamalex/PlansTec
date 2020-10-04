package creativitysol.com.planstech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_expansion.view.*


class ExpansionFragment : Fragment() {

    lateinit var v:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v=inflater.inflate(R.layout.fragment_expansion, container, false)


        v.expansion_rv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = ExpansionAdapter(requireActivity())
        }
        // Inflate the layout for this fragment
        return v
    }


}