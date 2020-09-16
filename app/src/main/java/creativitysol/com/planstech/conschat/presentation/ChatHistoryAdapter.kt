package creativitysol.com.planstech.conschat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.conschat.data.model.ChatHistory
import kotlinx.android.synthetic.main.item_receiver_item.view.*
import kotlinx.android.synthetic.main.item_sender_item.view.*

class ChatHistoryAdapter(
    private val chatHistoryList: List<ChatHistory>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (chatHistoryList[position].receiverId.toInt() == RECEIVER_ID)
            RECEIVER_VIEW_TYPE
        else
            SENDER_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == SENDER_VIEW_TYPE)
            SenderViewHolder(inflater.inflate(R.layout.item_sender_item, parent, false))
        else
            ReceiverViewHolder(inflater.inflate(R.layout.item_receiver_item, parent, false))
    }

    class SenderViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatItem = chatHistoryList[position]
        when (holder) {
            is SenderViewHolder -> {
                holder.itemView.txt_sender.text = chatItem.messages
                holder.itemView.txt_sender_time.text = chatItem.createdAt
            }
            is ReceiverViewHolder -> {
                holder.itemView.txt_receiver.text = chatItem.messages
                holder.itemView.txt_receiver_time.text = chatItem.createdAt
            }
        }
    }

    override fun getItemCount(): Int {
        return chatHistoryList.size
    }

    companion object {
        const val SENDER_VIEW_TYPE = 1
        const val RECEIVER_VIEW_TYPE = 2
        const val RECEIVER_ID = 39
    }
}