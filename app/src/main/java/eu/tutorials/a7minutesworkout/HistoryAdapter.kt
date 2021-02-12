package eu.tutorials.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context,val items : ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val llHistoryMainterm = view.ll_History_item_main
        val tvitem = view.tvItem
        val tvPosition = view.tv_position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).
        inflate(R.layout.item_history_row,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items[position]

        holder.tvPosition.text = (position + 1).toString()
        holder.tvitem.text = date

        if(position % 2 == 0){
            holder.llHistoryMainterm.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        }else{
            holder.llHistoryMainterm.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }

    }
}