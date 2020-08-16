package creativitysol.com.planstech.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class MyAccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("حسابي")
    }
}
