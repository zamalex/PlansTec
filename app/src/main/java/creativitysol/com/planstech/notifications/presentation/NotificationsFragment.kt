package creativitysol.com.planstech.notifications.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import creativitysol.com.planstech.EndlessRecyclerViewScrollListener
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class NotificationsFragment : Fragment() {
    var page = 1
    private val notificationsViewModel by viewModel<NotificationsViewModel>()
    lateinit var  notificationsAdapter: NotificationsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).showProgress(true)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onStart() {
        page=1
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.notifications))
        (activity as MainActivity).showNot(false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        notificationsAdapter = NotificationsAdapter {  }
        rec_notifications.apply {
            adapter = notificationsAdapter
        }
       rec_notifications.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
            override fun getLayoutManager(): RecyclerView.LayoutManager {
                return rec_notifications.layoutManager!!

            }

            override fun onLoadMore() {
                if (page >= 1) {
                    page++
                    notificationsViewModel.getNotification(page)

                }
            }
        })

        notificationsViewModel.notifications = MutableLiveData()
        notificationsAdapter.notificationList.clear()
        notificationsViewModel.getNotification(1)
        notificationsViewModel.notifications.observe(viewLifecycleOwner, Observer {
            if (it.data.notifications.isNullOrEmpty())
                Snackbar.make(rec_notifications, "No Data Found", Snackbar.LENGTH_SHORT).show()
            else {
                notificationsAdapter.addNotifications(it.data.notifications)

            }
            (activity as MainActivity).showProgress(false)
        })

        notificationsViewModel.error.observe(viewLifecycleOwner, Observer {
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
