package creativitysol.com.planstech.stagemissions

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import creativitysol.com.planstech.R
import creativitysol.com.planstech.stagemissions.model.MissionsModel


class MissionsAdapter(val context: Context, val listener: MyPickListener,var list:ArrayList<MissionsModel.Data>) : RecyclerView.Adapter<MissionsAdapter.Holder>() {




    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var pick : Button = itemView.findViewById(R.id.pick_img)
        var img : ImageView = itemView.findViewById(R.id.imageView4)
        var answer_et : EditText = itemView.findViewById(R.id.answer_et)
        var download : TextView = itemView.findViewById(R.id.downlaod)
        var mission_title : TextView = itemView.findViewById(R.id.mission_title)


    init {
        answer_et.setImeOptions(EditorInfo.IME_ACTION_DONE)
        download.setPaintFlags( Paint.UNDERLINE_TEXT_FLAG)



    }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_mission, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        var  item:MissionsModel.Data = list[position]

        holder.mission_title.text = item.title
        if (item.type.equals("training")){
            holder.img.visibility = View.GONE
            holder.download.visibility = View.GONE
            holder.pick.visibility = View.GONE
            holder.answer_et.visibility = View.VISIBLE

            holder.answer_et.addTextChangedListener {
                listener.sendTxt(position, it.toString())
            }
        }

        else{
            holder.img.visibility = View.VISIBLE
            holder.download.visibility = View.VISIBLE
            holder.pick.visibility = View.VISIBLE
            holder.answer_et.visibility = View.GONE

            if (item.selectedFile!=null){
                holder.pick.setHint(item.selectedFile!!.name)
            }

            if (item.file!=null){
                holder.download.visibility = View.VISIBLE
                holder.img.visibility = View.VISIBLE
            }else{
                holder.download.visibility = View.INVISIBLE
                holder.img.visibility = View.INVISIBLE

            }
        }

        holder.pick.setOnClickListener {
            listener.onMyPick(position)
        }

    }



    interface MyPickListener{
        fun sendTxt(pos: Int, s: String)
        fun onMyPick(pos: Int)
    }

}