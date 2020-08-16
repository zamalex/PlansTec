package creativitysol.com.planstech

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.library.bubbleview.BubbleTextView


class ChatRVAdapter(val context: Context) :
    RecyclerView.Adapter<ChatRVAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var me: BubbleTextView = itemView.findViewById(R.id.me)
        var you: BubbleTextView = itemView.findViewById(R.id.you)
        var met: TextView = itemView.findViewById(R.id.met)
        var yout: TextView = itemView.findViewById(R.id.yout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 11
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (position % 2 == 0) {
            holder.me.visibility = View.VISIBLE
            holder.you.visibility = View.GONE
            holder.met.visibility = View.VISIBLE
            holder.yout.visibility = View.GONE



        } else {
            holder.me.visibility = View.GONE
            holder.you.visibility = View.VISIBLE
            holder.met.visibility = View.GONE
            holder.yout.visibility = View.VISIBLE

        }
    }
}