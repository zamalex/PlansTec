package creativitysol.com.planstech.myplans

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.ExpansionFragment
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.packages.model.PlanModel


class MyPlansAdapter(val context: Context,val planModel: PlanModel) : RecyclerView.Adapter<MyPlansAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mtitle : TextView = itemView.findViewById(R.id.m_title)
        var details : TextView = itemView.findViewById(R.id.m_details)
        var item : LinearLayout = itemView.findViewById(R.id.m_item)


        init {
            item.setOnClickListener {

                var b:Bundle = Bundle()
                b.putString("id",planModel.data[adapterPosition].id.toString())

                ((context as MainActivity)).fragmentStack.push(ExpansionFragment().apply {

                    arguments = b
                })
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_my_plan, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return planModel.data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       // holder.mtitle.text = planModel.data[position].title
        holder.details.text = planModel.data[position].goals

    }


}