package creativitysol.com.planstech.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_notifications.view.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : Fragment() {

    lateinit var v :View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v=inflater.inflate(R.layout.fragment_notifications, container, false)

        v.notifications_rv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = NotificationsAdapter(requireActivity())
        }
        // Inflate the layout for this fragment
        return v
    }
    override fun onStart() {
        super.onStart()

        (activity as MainActivity).setTitle("الاشعارات")
        (activity as MainActivity).showNot(false)

    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showNot(true)



    }
}
