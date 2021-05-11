package creativitysol.com.planstech.notifications.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.notifications.data.model.NotificationResponse
import creativitysol.com.planstech.notifications.data.model.Notifications
import kotlinx.android.synthetic.main.item_notification.view.*


class NotificationsAdapter(

    private val onItemClick: (NotificationResponse.Data.NotificationContent) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var notificationList: ArrayList<NotificationResponse.Data.NotificationContent> = ArrayList()

    fun addNotifications( notificationList: List<NotificationResponse.Data.NotificationContent>){
        this.notificationList.addAll(notificationList)
        notifyDataSetChanged()
    }
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
        holder.itemView.txt_title.text = notificationItem.content
        holder.itemView.txt_created_at.text = notificationItem.createdAt

        holder.itemView.setOnClickListener {
            onItemClick.invoke(notificationItem)
        }
    }
}