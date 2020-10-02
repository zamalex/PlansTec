package creativitysol.com.planstech.tradio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articles.ArticleListener
import creativitysol.com.planstech.home.model.ArticlesModel
import creativitysol.com.planstech.packages.model.PlanModel


class TRVAdapter(val context: Context, val listener: radioClick, val model: PlanModel) : RecyclerView.Adapter<TRVAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var btn : MaterialRadioButton = itemView.findViewById(R.id.radioButton)
        var price : MaterialTextView = itemView.findViewById(R.id.p_price)
        var name : TextView = itemView.findViewById(R.id.p_name)
        var details : TextView = itemView.findViewById(R.id.p_details)

        init {
            btn.setOnClickListener {
                listener.onRadio(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_package, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return model.data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.details.text = model.data[position].goals
        holder.name.text = model.data[position].title
        holder.price.text = model.data[position].priceText


    }

    interface radioClick{
        fun onRadio(position: Int)

    }
}