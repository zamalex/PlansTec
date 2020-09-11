package creativitysol.com.planstech.notifications.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.notifications.data.model.Notifications
import kotlinx.android.synthetic.main.item_notification.view.*


class NotificationsAdapter(
    private val notificationList: List<Notifications>,
    private val onItemClick: (Notifications) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return object : RecyclerView.ViewHolder(rootView) {}
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notificationItem = notificationList[position]
        holder.itemView.txt_title.text = notificationItem.title
        holder.itemView.txt_created_at.text = notificationItem.createdAt

        holder.itemView.setOnClickListener {
            onItemClick.invoke(notificationItem)
        }
    }
}