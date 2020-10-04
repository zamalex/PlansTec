package creativitysol.com.planstech

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class ExpansionAdapter(val context: Context) : RecyclerView.Adapter<ExpansionAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var questions_panel : ConstraintLayout = itemView.findViewById(R.id.questions_panel)
        var missions_panel : ConstraintLayout = itemView.findViewById(R.id.missionis_panel)
        var q_status : ImageView = itemView.findViewById(R.id.q_status)
        var m_status : ImageView = itemView.findViewById(R.id.m_status)

        init {
            questions_panel.setOnClickListener {
                Toast.makeText(context,"questions ${adapterPosition}",Toast.LENGTH_SHORT).show()
            }
            missions_panel.setOnClickListener {
                Toast.makeText(context,"mission ${adapterPosition}",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.expansion_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        if (position==0){
            holder.q_status.setImageResource(R.drawable.done)
            holder.m_status.setImageResource(R.drawable.arr)
        }
        else{
            holder.q_status.setImageResource(R.drawable.lock)
            holder.m_status.setImageResource(R.drawable.lock)
        }



    }


}