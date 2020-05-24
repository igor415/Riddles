package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.models.Riddle
import com.varivoda.igor.zagonetke.models.ScoresEntry

class LevelAdapter(private val riddles: List<Riddle>,private val scoresEntry: ScoresEntry): RecyclerView.Adapter<LevelAdapter.MyViewHolder>() {

    private var listener: OnItemClickListener? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var numberOfLevel: TextView = itemView.findViewById(R.id.numberOfLevel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.level_item,parent,false))
    }

    override fun getItemCount(): Int = riddles.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.numberOfLevel.text = holder.itemView.context.getString(R.string.position_resource,(position+1).toString())
        holder.numberOfLevel.typeface = Typeface.createFromAsset(holder.itemView.context.assets, "fonts/spicy.otf")
        if(scoresEntry.number>position){
            holder.numberOfLevel.setTextColor(Color.WHITE)
            holder.numberOfLevel.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_check,0)
            holder.itemView.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onClick(riddles[position])
                }
            }
        }else if(scoresEntry.number==position){
            holder.numberOfLevel.setTextColor(Color.WHITE)
            holder.numberOfLevel.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            holder.itemView.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onClick(riddles[position])
                }
            }
        }

        holder.setIsRecyclable(false)
    }

    interface OnItemClickListener {
        fun onClick(riddle: Riddle)
    }

    fun setOnItemClickListener(lis: OnItemClickListener) {
        listener = lis
    }
}