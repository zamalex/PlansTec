package creativitysol.com.planstech.notifications.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class NotificationsFragment : Fragment() {

    private val notificationsViewModel by viewModel<NotificationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).showProgress(true)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("الاشعارات")
        (activity as MainActivity).showNot(false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationsViewModel.notifications.observe(viewLifecycleOwner, {
            if (it.data.isNullOrEmpty())
                Snackbar.make(rec_notifications, "No Data Found", Snackbar.LENGTH_SHORT).show()
            else {
                rec_notifications.apply {
                    adapter = NotificationsAdapter(it.data) {
                        // onItem clicking
                    }
                }
            }
            (activity as MainActivity).showProgress(false)
        })

        notificationsViewModel.error.observe(viewLifecycleOwner, {
            val errorMessage = if (it is UnknownHostException)
                getString(R.string.no_interet_connection)
            else getString(R.string.general_issue)
            Snackbar.make(rec_notifications, errorMessage, Snackbar.LENGTH_SHORT).show()
            (activity as MainActivity).showProgress(false)
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showNot(true)
    }
}
