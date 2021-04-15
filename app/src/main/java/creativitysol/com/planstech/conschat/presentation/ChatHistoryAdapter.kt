package creativitysol.com.planstech.conschat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.conschat.data.model.ChatMessages
import kotlinx.android.synthetic.main.consultant_chat_item.view.*
import kotlinx.android.synthetic.main.me_chat_item.view.*

class ChatHistoryAdapter(
    private val chatHistoryList: ChatMessages
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //  first time, sender will be the consultant..
    private val consultantChatId = chatHistoryList.data.otherUser.id
    var messages = ArrayList<ChatMessages.Data.Message>()

    fun addMessages(messages: ArrayList<ChatMessages.Data.Message>){
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }
    //  first time, receiver will be me..

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender.id == consultantChatId)
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
        val chatItem = messages[position]
        when (holder) {
            is MeViewHolder -> {
                holder.itemView.txt_me.text = chatItem.message
                holder.itemView.txt_me_time.text = chatItem.createdAt
            }
            is ConsultantViewHolder -> {
                holder.itemView.txt_consultant.text = chatItem.message
                holder.itemView.txt_consultant_time.text = chatItem.createdAt
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun getReceiverId() = consultantChatId

    companion object {
        const val ME_VIEW_TYPE = 1
        const val CONSULTANT_VIEW_TYPE = 2
    }
}