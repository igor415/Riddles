package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.highscore

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.models.ScoresEntry

const val current_user = "#ebf0f7"

class HighScoreAdapter(private val highScoreList: List<ScoresEntry>,private val user: String) : RecyclerView.Adapter<HighScoreAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val username: TextView = itemView.findViewById(R.id.username)
        val score: TextView = itemView.findViewById(R.id.score)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.highscore_item,parent,false))
    }

    override fun getItemCount(): Int = highScoreList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text = highScoreList[position].username
        holder.score.text = highScoreList[position].score.toString()
        if(position<3) {
            setMedals(holder, position)
        }
        if(holder.username.text == user){
            holder.username.setTypeface(holder.username.typeface, Typeface.BOLD_ITALIC)
            holder.score.setTypeface(holder.username.typeface, Typeface.BOLD_ITALIC)
            holder.itemView.setBackgroundColor(Color.parseColor(current_user))
        }
    }

    private fun setMedals(holder: MyViewHolder, position: Int) {
        when(position){
            0 -> holder.image.setImageResource(R.drawable.ic_gold)
            1 -> holder.image.setImageResource(R.drawable.ic_silver)
            2 -> holder.image.setImageResource(R.drawable.ic_bronze)
        }
    }


}