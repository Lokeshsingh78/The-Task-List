package com.example.newnotes;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noTasksTextView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        noTasksTextView = findViewById(R.id.noTasksTextView);

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, noteList, new NoteAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Note note) {
                deleteNote(note);
            }
        });
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNoteDialog();
            }
        });
    }

    private void showAddNoteDialog() {
        // Implement a dialog to add a new note
        // For example, use an AlertDialog with a custom layout to input the note details
        // After adding the note, call addNote() to update the list
    }

    private void addNote(Note newNote) {
        noteList.add(newNote);
        noteAdapter.notifyItemInserted(noteList.size() - 1);
        updateUI();
    }

    private void deleteNote(Note note) {
        int position = noteList.indexOf(note);
        if (position != -1) {
            noteList.remove(position);
            noteAdapter.notifyItemRemoved(position);
            updateUI();
        }
    }

    private void updateUI() {
        if (noteList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noTasksTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noTasksTextView.setVisibility(View.GONE);
        }
    }
}
