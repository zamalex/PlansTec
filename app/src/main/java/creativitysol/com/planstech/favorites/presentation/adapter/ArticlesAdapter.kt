package creativitysol.com.planstech.favorites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.favorites.data.model.TrainingResult
import kotlinx.android.synthetic.main.item_full_article.view.*

class ArticlesAdapter(
    private val trainingResult: TrainingResult,
    private val onArticleItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_full_article, parent, false)
        return object : RecyclerView.ViewHolder(root) {}
    }

    override fun getItemCount(): Int {
        return if (trainingResult.data.isNullOrEmpty())
            0
        else trainingResult.data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position % 2 == 0) {
            val scale: Float = holder.itemView.context.resources.displayMetrics.density
            val pixels = (300 * scale + 0.5f).toInt()
            holder.itemView.img_article.requestLayout()
            holder.itemView.img_article.layoutParams.height = pixels
        } else {
            val scale: Float = holder.itemView.context.getResources().getDisplayMetrics().density
            val pixels = (150 * scale + 0.5f).toInt()
            holder.itemView.img_article.requestLayout();
            holder.itemView.img_article.layoutParams.height = pixels
        }
        trainingResult.data?.get(position).let { articleItem ->
            holder.itemView.title_full.text = articleItem?.title
            Picasso.get().load(articleItem?.image).fit().centerCrop()
                .into(holder.itemView.img_article)
        }
        holder.itemView.setOnClickListener {
            trainingResult.data?.get(position)?.id?.let { articleId ->
                onArticleItemClick.invoke(
                    articleId
                )
            }
        }
    }
}