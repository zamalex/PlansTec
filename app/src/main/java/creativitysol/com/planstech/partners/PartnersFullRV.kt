package creativitysol.com.planstech.partners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.partners.model.PartnerModel


class PartnersFullRV(val context: Context,val model: PartnerModel) : RecyclerView.Adapter<PartnersFullRV.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerView:RecyclerView = itemView.findViewById(R.id.single_partner_rv)
        var title:TextView = itemView.findViewById(R.id.p_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_prtner_full, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return model.data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.title.text = model.data[position].title
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=
                SinglePartnerRV(context,model.data[position])
        }
    }
}