package creativitysol.com.planstech.partners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.partners.model.PartnerModel


class SinglePartnerRV(val context: Context,val partner: PartnerModel.Data) : RecyclerView.Adapter<SinglePartnerRV.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img:ImageView = itemView.findViewById(R.id.single_partner_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_partner_img, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return partner.logos.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {



        if (!partner.logos[position].isEmpty())
            Picasso.get().load(partner.logos[position]).fit().centerCrop().into(holder.img)
    }
}