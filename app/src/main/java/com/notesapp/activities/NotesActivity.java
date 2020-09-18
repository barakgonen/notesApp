package com.notesapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.notesapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notesapp.model.NoteEntity;
import com.notesapp.model.NotesAdapter;
import com.notesapp.network.NotesRestServicesHandler;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class NotesActivity extends AppCompatActivity {
    RecyclerView recyclerViewNotes;
    SwipeRefreshLayout swipeRefreshLayoutNotes;

    private NotesAdapter notesAdapter;
    private List<NoteEntity> notesList = new ArrayList<>();
    private FloatingActionButton addNoteBtn;
    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        addNoteBtn = findViewById(R.id.floatingActionButtonAdd);
        recyclerViewNotes = findViewById(R.id.RecyclerViewNotes);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, 0);
            }
        });

        setupRecycler();
        fetchAllNotesFromServer();
    }

    private void fetchAllNotesFromServer() {
        notesList = NotesRestServicesHandler.fetchAllNotesFromServer();
        reDrawNotes();
    }

    private void setupRecycler() {
        new GetAllNotes().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            fetchAllNotesFromServer();
        }
    }

    private class GetAllNotes extends AsyncTask<Void, Void, List<NoteEntity>> {

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            List<NoteEntity> n = NotesRestServicesHandler.fetchAllNotesFromServer();
            return n;
        }
        
        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(List<NoteEntity> notesEntities) {
            super.onPostExecute(notesEntities);
            reDrawNotes();
        }
    }

    private void reDrawNotes() {
        notesAdapter = new NotesAdapter(notesList);

        recyclerViewNotes.setAdapter(notesAdapter);
        recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL));

        notesAdapter.setOnItemClickListener((position, id, noteContext, color, cardView) -> {
            NoteEntity toExpand = notesList.get(position);
            Intent intent = new Intent(NotesActivity.this, ShowNoteActivity.class);

            intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("title", toExpand.getTitle());
            intent.putExtra("body", toExpand.getBody());
            intent.putExtra("priority", toExpand.getPriority().toString());
            intent.putExtra("color", String.valueOf(toExpand.getBgColor()));
            startActivityForResult(intent, 0);
        });

        notesAdapter.setOnItemClickLongListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int id, String noteContext, int color, CardView cardView) {
                NoteEntity entity = notesList.get(position);
                NotesRestServicesHandler.deleteNote(entity.getId());
                fetchAllNotesFromServer();
            }
        });
    }
}
