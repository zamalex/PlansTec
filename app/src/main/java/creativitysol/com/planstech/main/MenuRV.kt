package creativitysol.com.planstech.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R


class MenuRV(val context: Context, val list: ArrayList<String>,val clicker: mainClick,val isLogged:Boolean) :
    RecyclerView.Adapter<MenuRV.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt: TextView = itemView.findViewById(R.id.menu_txt)

        init {
            txt.setOnClickListener {
                clicker.onMainClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.drawer_txt, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (list == null)
            return 0
        else
            return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.txt.text = list[position]
    }


     interface mainClick{

        fun onMainClick(position: Int)
    }
}