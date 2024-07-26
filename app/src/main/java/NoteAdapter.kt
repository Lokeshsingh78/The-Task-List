package com.example.newnotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val context: Context,
    private val notes: List<Note>,
    private val onDeleteClickListener: OnDeleteClickListener
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    interface OnDeleteClickListener {
        fun onDeleteClick(note: Note?)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(note: Note) {
            noteTextView.text = note.text
            deleteButton.setOnClickListener { v: View? ->
                onDeleteClickListener.onDeleteClick(
                    note
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}