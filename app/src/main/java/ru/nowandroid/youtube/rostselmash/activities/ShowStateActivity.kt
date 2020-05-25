package ru.nowandroid.youtube.rostselmash.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_show_state.*
import ru.nowandroid.youtube.rostselmash.R
import ru.nowandroid.youtube.rostselmash.adapters.StateListAdapter
import ru.nowandroid.youtube.rostselmash.models.State

class ShowStateActivity : AppCompatActivity() {

    lateinit var mDb: DatabaseReference
    val noteList: MutableList<State> = ArrayList()
    lateinit var adapter: StateListAdapter
    private val userIDFB = "IZ07eK5azYXsLMupuWWMumYCbsj1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_state)

        addStateFAB.setOnClickListener { addNote() }
        mDb = FirebaseDatabase.getInstance().reference

        loadNotes()
    }

    fun addNote() {
        val intent = Intent(this, EditStateActivity::class.java)
        intent.putExtra(EditStateActivity.EXTRA_NOTE, State())
        startActivity(intent)
    }

    fun loadNotes() {
        val path = "users/$userIDFB/state"
        val query = mDb.child(path).limitToFirst(20)
        val options = FirebaseRecyclerOptions.Builder<State>()
                .setQuery(query, State::class.java)
                .build()
        adapter = StateListAdapter(options, this::editNote)
        stateListRecycler.layoutManager = LinearLayoutManager(this)
        stateListRecycler.adapter = adapter
    }

    fun editNote(state: State) {
        val i = Intent(this, EditStateActivity::class.java)
        i.putExtra(EditStateActivity.EXTRA_NOTE, state)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
