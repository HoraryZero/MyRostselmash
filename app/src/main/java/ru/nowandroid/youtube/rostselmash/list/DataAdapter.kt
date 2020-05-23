package ru.nowandroid.youtube.rostselmash.list

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nowandroid.youtube.rostselmash.R

class DataAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    private val listNews = mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(listNews[pos])
    }

    fun set(list: MutableList<News>) {
        this.listNews.clear()
        this.listNews.addAll(list)
        notifyDataSetChanged()
    }
}