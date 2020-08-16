package creativitysol.com.planstech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_exx.view.*

/**
 * A simple [Fragment] subclass.
 */
class ExxFragment : Fragment() {

    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_exx, container, false)


        v.tstrv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = RecyclerAdapter(requireActivity(), arrayOf("المرحلة التشخيصية","مرحلة الشخصية","مرحلة التعليم","مرحلة المهارة","مرحلة القدرات","مرحلة الريادة","مرحلة التطوير"))
        }
        // Inflate the layout for this fragment
        return v
    }

}
