package creativitysol.com.planstech.favorites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.favorites.data.model.TrainingResult
import kotlinx.android.synthetic.main.item_full_course.view.*

class TrainingAdapter(
    private val trainingResult: TrainingResult,
    private val onTrainingItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_full_course, parent, false)
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
            holder.itemView.course_img_full.requestLayout()
            holder.itemView.course_img_full.layoutParams.height = pixels
        } else {
            val scale: Float = holder.itemView.context.resources.displayMetrics.density
            val pixels = (150 * scale + 0.5f).toInt()
            holder.itemView.course_img_full.requestLayout()
            holder.itemView.course_img_full.layoutParams.height = pixels
        }
        trainingResult.data?.get(position)
            .let { trainingDataItem ->
                holder.itemView.c_title.text = trainingDataItem?.title
                holder.itemView.c_price.text = "${trainingDataItem?.price} ريال "
                Picasso.get().load(trainingDataItem?.image).fit().centerCrop()
                    .into(holder.itemView.course_img_full)

                if (trainingDataItem?.status.equals("available")) {
                    holder.itemView.course_ava_img.setImageResource(R.drawable.status)
                    holder.itemView.c_ava.text = "متاح"
                } else if (trainingDataItem?.status.equals("non available")) {
                    holder.itemView.course_ava_img.setImageResource(R.drawable.red)
                    holder.itemView.c_ava.text = "غير متاح"

                } else if (trainingDataItem?.status.equals("limited")) {
                    holder.itemView.course_ava_img.setImageResource(R.drawable.pink)
                    holder.itemView.c_ava.text = "محدود"

                }
                else {
                    holder.itemView.course_ava_img.setImageResource(R.drawable.red)
                    holder.itemView.c_ava.text = "مكتمل"

                }
            }
        holder.itemView.setOnClickListener {
            trainingResult.data?.get(position)?.id?.let { trainingId ->
                onTrainingItemClick.invoke(
                    trainingId
                )
            }
        }
    }

}