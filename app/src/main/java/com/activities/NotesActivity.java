package com.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.app.model.NoteEntity;
import com.example.app.model.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.services.NotesSynchronizationService;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class NotesActivity extends AppCompatActivity {
    RecyclerView recyclerViewNotes;
    SwipeRefreshLayout swipeRefreshLayoutNotes;

    private NotesAdapter notesAdapter;
    private List<NoteEntity> notesList = new ArrayList<>();
    private ResponseReceiver receiver;

    private FloatingActionButton addNoteBtn;

    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        fetchAllNotesFromServer();

        // Initialize components
        addNoteBtn = findViewById(R.id.floatingActionButtonAdd);
        swipeRefreshLayoutNotes = findViewById(R.id.swipeRefreshLayoutNotes);
        recyclerViewNotes = findViewById(R.id.RecyclerViewNotes);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, 0);
//        Calendar currentDate = Calendar.getInstance();
//        NotesEntity newNote = new NotesEntity("",
//                DateConverter.calendarToDate(currentDate));
//
//        new AddNewNote().execute(newNote);
//                }
            }
        });
        setupRecycler();
        setupSwipeLayout();

    }

    private void fetchAllNotesFromServer(){
        Toast.makeText(getApplicationContext(), "need to fetch all data", Toast.LENGTH_LONG).show();
        fetchData();
    }

    private void fetchData(){
        Intent syncIntentService = new Intent(this, NotesSynchronizationService.class);
        startService(syncIntentService);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
//                LocalStorageHelper.removeAll(this);
//                new RemoveAllNotes().execute((Callable) () -> {
//                    finishAffinity();
//                    return null;
//                });
                break;
            case R.id.menu_close:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecycler() {
        new GetAllNotes().execute();
    }

    private void setupSwipeLayout() {
        swipeRefreshLayoutNotes.setColorSchemeResources(
                R.color.color_note_0_background,
                R.color.primary_dark,
                R.color.color_note_1_background,
                R.color.primary,
                R.color.color_note_5_background
        );

//        IntentFilter filter = new IntentFilter("ACTION_BROADCAST_RESPONSE");
//        filter.addCategory(Intent.CATEGORY_DEFAULT);
//        receiver = new ResponseReceiver();
//        registerReceiver(receiver, filter);

        swipeRefreshLayoutNotes.setOnRefreshListener(() -> {
            fetchData();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "refresh all notes yo", Toast.LENGTH_LONG).show();
            fetchData();
        }
    }

    private void refreshingServiceFinished() {
        swipeRefreshLayoutNotes.setRefreshing(false);
        new GetAllNotes().execute();
        swipeRefreshLayoutNotes.setRefreshing(true);
    }

    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NotesActivity.this.refreshingServiceFinished();
        }
    }

    private class GetAllNotes extends AsyncTask<Void, Void, List<NoteEntity>> {

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            List<NoteEntity> n = NotesSynchronizationService.getNotesForUpdate();
            return n;
        }

        // Suppressing error message due to the bug in latest Android Build Tools
        // More info: https://issuetracker.google.com/issues/37130193
        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(List<NoteEntity> notesEntities) {
            super.onPostExecute(notesEntities);

            notesList = notesEntities;
            notesAdapter = new NotesAdapter(NotesActivity.this, notesList);

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
                intent.putExtra("color", toExpand.getBgColor());
                startActivityForResult(intent, 0);
            });
        }
    }
}
