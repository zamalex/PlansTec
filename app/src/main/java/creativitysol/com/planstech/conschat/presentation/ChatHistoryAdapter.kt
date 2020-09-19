package creativitysol.com.planstech.conschat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.conschat.data.model.ChatHistory
import kotlinx.android.synthetic.main.consultant_chat_item.view.*
import kotlinx.android.synthetic.main.me_chat_item.view.*

class ChatHistoryAdapter(
    private val chatHistoryList: List<ChatHistory>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //  first time, sender will be the consultant..
    private val consultantChatId = chatHistoryList[0].senderId
    //  first time, receiver will be me..
    private val meChatId = chatHistoryList[0].receiverId

    override fun getItemViewType(position: Int): Int {
        return if (chatHistoryList[position].senderId != meChatId)
            CONSULTANT_VIEW_TYPE
        else
            ME_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == ME_VIEW_TYPE)
            MeViewHolder(inflater.inflate(R.layout.me_chat_item, parent, false))
        else
            ConsultantViewHolder(inflater.inflate(R.layout.consultant_chat_item, parent, false))
    }

    class MeViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class ConsultantViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatItem = chatHistoryList[position]
        when (holder) {
            is MeViewHolder -> {
                holder.itemView.txt_me.text = chatItem.messages
                holder.itemView.txt_me_time.text = chatItem.createdAt
            }
            is ConsultantViewHolder -> {
                holder.itemView.txt_consultant.text = chatItem.messages
                holder.itemView.txt_consultant_time.text = chatItem.createdAt
            }
        }
    }

    override fun getItemCount(): Int {
        return chatHistoryList.size
    }

    fun getReceiverId() = consultantChatId

    companion object {
        const val ME_VIEW_TYPE = 1
        const val CONSULTANT_VIEW_TYPE = 2
    }
}