package ru.nowandroid.youtube.rostselmash.activities

import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
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

    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = ""
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    lateinit var mDb: DatabaseReference
    val noteList: MutableList<State> = ArrayList()
    lateinit var adapter: StateListAdapter
    private val userIDFB = "IZ07eK5azYXsLMupuWWMumYCbsj1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_state)

        // Проверка на наличие соеднинения
        if (checkConnect()) {

            Toast.makeText(context, toastConnectedInfo, duration)
        } else {

            Toast.makeText(context, toastDisconnectedInfo, duration).show()
            this.finish()
        }

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

    private fun checkConnect() : Boolean {

        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(connectivity != null) {

            connectInfo = connectivity!!.activeNetworkInfo

            if (connectInfo != null) {

                if (connectInfo!!.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
}
