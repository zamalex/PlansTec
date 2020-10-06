package creativitysol.com.planstech

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.expansionpanel.ExpansionHeader
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.stagequestions.QuestionsFragment
import creativitysol.com.planstech.stages.model.StagesModel
import kotlin.math.sin


class ExpansionAdapter(val context: Context,val stages:ArrayList<StagesModel.Data>) : RecyclerView.Adapter<ExpansionAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var questions_panel : ConstraintLayout = itemView.findViewById(R.id.questions_panel)
        var ex_header : ExpansionHeader = itemView.findViewById(R.id.ex_header)
        var headerIndicator : ImageView = itemView.findViewById(R.id.headerIndicator)
        var missions_panel : ConstraintLayout = itemView.findViewById(R.id.missionis_panel)
        var q_status : ImageView = itemView.findViewById(R.id.q_status)
        var m_status : ImageView = itemView.findViewById(R.id.m_status)
        var title : TextView = itemView.findViewById(R.id.title)

        init {
            questions_panel.setOnClickListener {
               // Toast.makeText(context,"questions ${adapterPosition}",Toast.LENGTH_SHORT).show()

                var b:Bundle = Bundle()
                b.putString("id",stages[adapterPosition].stageId.toString())

                (context as MainActivity).fragmentStack.push(QuestionsFragment().apply {
                   arguments = b
                })
            }
            missions_panel.setOnClickListener {
                //Toast.makeText(context,"mission ${adapterPosition}",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.expansion_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (stages==null)
        return 0
        return stages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        var single:StagesModel.Data =  stages[position]

        holder.title.text = single.stageTitle

        if (single.isPassed==1){
            holder.q_status.setImageResource(R.drawable.done)
            holder.m_status.setImageResource(R.drawable.done)
        }
        else{
            if (single.passed_questions==1){
                holder.q_status.setImageResource(R.drawable.done)
                holder.m_status.setImageResource(R.drawable.arr)
            }else{
                holder.q_status.setImageResource(R.drawable.arr)
                holder.m_status.setImageResource(R.drawable.arr)
            }


        }




    }


}