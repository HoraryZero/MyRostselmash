package ru.nowandroid.youtube.rostselmash.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.card_state.view.*
import ru.nowandroid.youtube.rostselmash.R
import ru.nowandroid.youtube.rostselmash.models.State

class StateListAdapter(options: FirebaseRecyclerOptions<State>, val onItemClick: (State) -> Unit) : FirebaseRecyclerAdapter<State, StateListAdapter.ViewHolder>(options) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int, state: State) {
        state.id = this.getRef(position).key.toString()
        holder.bindNoteItem(state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_state, parent, false)
        return ViewHolder(v, onItemClick)
    }

    class ViewHolder(v: View, val onItemClick: (State) -> Unit): RecyclerView.ViewHolder(v) {
        fun bindNoteItem(state: State) {
            with(state) {
                itemView.titleView.text = state.title
                itemView.contentView.text = state.content
            }
            itemView.setOnClickListener { onItemClick(state)  }
        }
    }
}
