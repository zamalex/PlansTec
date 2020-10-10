package creativitysol.com.planstech.courses

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
import creativitysol.com.planstech.home.model.TrainingModel
import org.koin.ext.quoted

class CoursesFullRV(val context: Context, val listener: CourseListener) :
    RecyclerView.Adapter<CoursesFullRV.Holder>() {

    var trainingModel: TrainingModel? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.course_img_full)

        var statusImg: ImageView = itemView.findViewById(R.id.course_ava_img)
        var c_ava: TextView = itemView.findViewById(R.id.c_ava)

        var course_card: CardView = itemView.findViewById(R.id.course_card)
        var title: TextView = itemView.findViewById(R.id.c_title)
        var price: TextView = itemView.findViewById(R.id.c_price)

        init {
            course_card.setOnClickListener {
                listener.onCourseClick(trainingModel!!.data[adapterPosition].id.toString())
            }
        }

    }

    fun setList(trainingModel: TrainingModel) {
        this.trainingModel = trainingModel

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_full_course, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (trainingModel == null)
            return 0
        return trainingModel!!.data.size
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (position % 2 == 0) {

            val scale: Float = context.getResources().getDisplayMetrics().density
            val pixels = (300 * scale + 0.5f).toInt()

            holder.img.requestLayout();

            holder.img.getLayoutParams().height = pixels.toInt()

        } else {

            val scale: Float = context.getResources().getDisplayMetrics().density
            val pixels = (150 * scale + 0.5f).toInt()

            holder.img.requestLayout();

            holder.img.getLayoutParams().height = pixels.toInt()
        }

        var o: TrainingModel.Data = trainingModel!!.data[position]

        holder.title.text = o.title
        holder.price.text = "${o.price} ريال "

        if (o.image != null && !o.image.isEmpty())
            Picasso.get().load(o.image).fit().centerCrop().into(holder.img)


        if (o.status.equals("available")) {
            holder.statusImg.setImageResource(R.drawable.status)
            holder.c_ava.text = "متاح"
        } else if (o.status.equals("non available")) {
            holder.statusImg.setImageResource(R.drawable.red)
            holder.c_ava.text = "غير متاح"

        } else if (o.status.equals("limited")) {
            holder.statusImg.setImageResource(R.drawable.pink)
            holder.c_ava.text = "محدود"

        }
        else {
            holder.statusImg.setImageResource(R.drawable.red)
            holder.c_ava.text = "مكتمل"

        }


    }
}