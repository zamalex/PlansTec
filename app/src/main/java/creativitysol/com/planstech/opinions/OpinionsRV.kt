package creativitysol.com.planstech.opinions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.home.model.ReviewsModel


class OpinionsRV(val context: Context) : RecyclerView.Adapter<OpinionsRV.Holder>() {

    var reviewsModel: ReviewsModel? = null
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name:TextView = itemView.findViewById(R.id.rev_name)
        var txt:TextView = itemView.findViewById(R.id.rev_txt)
        var img:ImageView = itemView.findViewById(R.id.rev_img)
        var rate:RatingBar = itemView.findViewById(R.id.ratingBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_opinion, parent, false)
        )
    }

    fun setReviews(reviewsModel: ReviewsModel){
        this.reviewsModel = reviewsModel
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (reviewsModel== null)
            return 0
        return reviewsModel!!.data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        var o:ReviewsModel.Data = reviewsModel!!.data[position]

        holder.name.text = o.userName
        holder.txt.text = o.content

        holder.rate.rating=o.stars.toFloat()

        if (o.avatar!=null&&!o.avatar.isEmpty())
            Picasso.get().load(o.avatar).fit().centerCrop().into(holder.img)

    }
}