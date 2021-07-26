package com.example.trainingproject.components

import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.models.Videos
import java.util.zip.Inflater

class HowToVideoAdapter(val context: Context, var listVideos : List<Videos> ) :
    RecyclerView.Adapter<HowToVideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater? = LayoutInflater.from(parent.context)
        var view : View = inflater!!.inflate(R.layout.item_how_to_videos, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVideoKey : TextView =itemView.findViewById(R.id.txt_video_key)
        var txtVideoValue : TextView =itemView.findViewById(R.id.txt_video_value)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var video : Videos = listVideos.get(position)
        holder.txtVideoKey.text = video.key
        holder.txtVideoValue.text = video.value
        holder.txtVideoValue.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun getItemCount(): Int {
        return listVideos.size
    }

}