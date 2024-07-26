package com.example.newnotes

import android.os.Bundle
import android.text.InputType
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private val notes = mutableListOf<Note>()
    private var noteId = 0

    private val fabOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.fab_open) }
    private val fabClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.fab_close) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noteAdapter = NoteAdapter(this, notes, object : NoteAdapter.OnDeleteClickListener {
            override fun onDeleteClick(note: Note?) {
                note?.let { deleteNote(it) }
            }
        })
        recyclerView.adapter = noteAdapter

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            showAddNoteDialog()
            it.startAnimation(fabOpen)
        }
    }

    private fun showAddNoteDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle("Add TASK")
            .setView(input)
            .setPositiveButton("Add") { dialog, _ ->
                val noteText = input.text.toString()
                if (noteText.isNotEmpty()) {
                    addNote(Note(noteId++, noteText))
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun addNote(note: Note) {
        notes.add(note)
        noteAdapter.notifyItemInserted(notes.size - 1)
        updateUI()
    }

    private fun deleteNote(note: Note) {
        val position = notes.indexOf(note)
        if (position != -1) {
            notes.removeAt(position)
            noteAdapter.notifyItemRemoved(position)
            Toast.makeText(this, "Task completed", Toast.LENGTH_SHORT).show()
            updateUI()
        }
    }

    private fun updateUI() {
        // Implement any UI updates if needed
    }
}
