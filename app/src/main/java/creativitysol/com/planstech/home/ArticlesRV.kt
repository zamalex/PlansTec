package creativitysol.com.planstech.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.articles.ArticleListener
import creativitysol.com.planstech.home.model.ArticlesModel

class ArticlesRV(val context: Context,val listener: ArticleListener,val articlesModel: ArticlesModel) : RecyclerView.Adapter<ArticlesRV.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contain : CardView = itemView.findViewById(R.id.article_card)
        var title : TextView = itemView.findViewById(R.id.article_title)
        var img : ImageView = itemView.findViewById(R.id.article_img)

        init {
            contain.setOnClickListener {
                listener.onArticleClick(articlesModel.data[adapterPosition].id.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return articlesModel.data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var o:ArticlesModel.Data = articlesModel.data[position]

        holder.title.text = o.title
        if (o.image!=null&&!o.image.isEmpty())
            Picasso.get().load(o.image).fit().centerCrop().into(holder.img)

    }
}