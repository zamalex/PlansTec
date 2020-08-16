package creativitysol.com.planstech

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection


class RecyclerAdapter(val context:Context,val array: Array<String>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
    private val expansionsCollection = ExpansionLayoutCollection()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHolder {

        return RecyclerHolder(
            LayoutInflater.from(context).inflate(R.layout.lyout, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerHolder,
        position: Int
    ) {
        holder.bind()
        expansionsCollection.add(holder.expansionLayout)
        holder.txt.text = array[position]
    }

    override fun getItemCount(): Int {
        return array.size
    }


    class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var expansionLayout: ExpansionLayout
        var txt: TextView

        fun bind() {
            expansionLayout.collapse(false)
            //expansionLayout.isEnabled = false
        }


        init {
            expansionLayout = itemView.findViewById(R.id.expansionLayout)
            txt = itemView.findViewById(R.id.l_txt)
        }
    }

    init {
        expansionsCollection.openOnlyOne(false)
    }
}