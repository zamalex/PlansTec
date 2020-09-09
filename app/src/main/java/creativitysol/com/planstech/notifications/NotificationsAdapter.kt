package creativitysol.com.planstech.notifications

import android.app.Notification
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R



class NotificationsAdapter(
    val notificationList : List<Notification>
) : RecyclerView.Adapter<NotificationsAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var txt = itemView.findViewById<TextView>(R.id.not_txt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.txt.text = Html.fromHtml("باقي من الوقت علي انتهاء اشتراكك بباقة عالمي 15 يوم، " + "<font color=\"#96B9FF\"><u>" + "أكمل تقدمك" + "</u></font>")
    }
}