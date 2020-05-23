package ru.nowandroid.youtube.rostselmash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_state.*
import ru.nowandroid.youtube.rostselmash.R
import ru.nowandroid.youtube.rostselmash.models.State

class EditStateActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NOTE = "state"
    }

    lateinit var state: State
    lateinit var mDb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_state)
        if (!intent.hasExtra(EXTRA_NOTE)) finish()

        state = intent.getSerializableExtra(EXTRA_NOTE) as State
        stateTitle.text?.append(state.title)
        stateContent.text?.append(state.content)

        mDb = FirebaseDatabase.getInstance().reference
        saveBtn.setOnClickListener { save() }
    }

    fun save() {
        // TODO: validate fields
        state.title = stateTitle.text.toString()
        state.content = stateContent.text.toString()

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val path = "users/" + uid + "/state"
        val key = if (state.id.equals("")) mDb.child(path).push().key else state.id
        val childUpdates: MutableMap<String, Any> = HashMap()

        childUpdates[path + "/" + key] = state.toMap()
        mDb.updateChildren(childUpdates).addOnCompleteListener { onSaveComplete() }
    }

    fun onSaveComplete() {
        Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
        finish()
    }
}
