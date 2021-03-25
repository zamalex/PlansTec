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
import creativitysol.com.planstech.courses.CourseListener
import creativitysol.com.planstech.home.model.TrainingModel


class CoursesRV(val context: Context,val listener: CourseListener,val trainingModel: ArrayList<TrainingModel.Data.Training>) : RecyclerView.Adapter<CoursesRV.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var course_card:CardView = itemView.findViewById(R.id.course_card)
        var title:TextView = itemView.findViewById(R.id.course_title)
        var price:TextView = itemView.findViewById(R.id.course_price)
        var ava:TextView = itemView.findViewById(R.id.course_ava)
        var ava_img:ImageView = itemView.findViewById(R.id.course_ava_img)
        var course_img:ImageView = itemView.findViewById(R.id.course_img)


        init {
            course_card.setOnClickListener {
                listener.onCourseClick(trainingModel[adapterPosition].id.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_course, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return trainingModel.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var obj:TrainingModel.Data.Training = trainingModel[position]

        holder.title.text=obj.title
        holder.price.text="${obj.price} ريال "
        holder.ava.text=obj.status

        if (obj.status.equals("available")) {
            holder.ava_img.setImageResource(R.drawable.status)
            holder.ava.text = "متاح"
        } else if (obj.status.equals("non available")) {
            holder.ava.text = "غير متاح"

            holder.ava_img.setImageResource(R.drawable.red)
        } else if (obj.status.equals("limited")) {
            holder.ava.text = "محدود"

            holder.ava_img.setImageResource(R.drawable.pink)
        } else {
            holder.ava.text = "مكتمل"

            holder.ava_img.setImageResource(R.drawable.red)
        }



        if (obj.image!=null&&!obj.image.isEmpty())
            Picasso.get().load(obj.image).fit().centerCrop().into(holder.course_img)


    }
}