package eu.tutorials.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class Exercise(val items : ArrayList<ExerciseModel>, val context : Context) : RecyclerView.Adapter<Exercise.ViewHolder>(){


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvitem = view.tvitem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).
        inflate(R.layout.item_exercise_status,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val model : ExerciseModel = items[position]

        holder.tvitem.text = model.getId().toString()

        if(model.getIsSelected()){
            holder.tvitem.background = ContextCompat.
            getDrawable(context,R.drawable.item_circular_thin_color_accent_border)
            holder.tvitem.setTextColor(Color.parseColor("#212121"))
        }else if(model.isGetCompleted()){

                holder.tvitem.background = ContextCompat.
                getDrawable(context,R.drawable.item_circular_color_accent_background)
                holder.tvitem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{

                holder.tvitem.background = ContextCompat.
                getDrawable(context,R.drawable.item_circular_color_gray_background)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))

        }

    }
}