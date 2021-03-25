package creativitysol.com.planstech.articles

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
import creativitysol.com.planstech.home.model.ArticlesModel


class ArticleFullRV(val context: Context,val listener: ArticleListener) : RecyclerView.Adapter<ArticleFullRV.Holder>() {

    var articlesModel: ArrayList<ArticlesModel.Data.Article> = ArrayList()
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img :ImageView = itemView.findViewById(R.id.img_article)
        var title : TextView = itemView.findViewById(R.id.title_full)
        var article_card:CardView = itemView.findViewById(R.id.article_card)

        init {
            article_card.setOnClickListener {
                listener.onArticleClick(articlesModel[adapterPosition].id.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_full_article, parent, false)
        )
    }

    fun setArtiocles(articlesModel: ArrayList<ArticlesModel.Data.Article>){
        this.articlesModel.addAll(articlesModel)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        if (articlesModel==null)
            return 0
        return articlesModel!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (position%2==0){

            val scale: Float = context.getResources().getDisplayMetrics().density
            val pixels = (300 * scale + 0.5f).toInt()

            holder.img.requestLayout();

            holder.img.getLayoutParams().height = pixels.toInt()

        }else{

            val scale: Float = context.getResources().getDisplayMetrics().density
            val pixels = (150 * scale + 0.5f).toInt()

            holder.img.requestLayout();

            holder.img.getLayoutParams().height = pixels.toInt()
        }

        var o:ArticlesModel.Data.Article = articlesModel[position]

        holder.title.text = o.title
        if (o.image!=null&&!o.image.isEmpty())
            Picasso.get().load(o.image).fit().centerCrop().into(holder.img)
    }
}