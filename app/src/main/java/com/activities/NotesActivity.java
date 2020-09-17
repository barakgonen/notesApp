package com.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
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

        // Initialize components
        addNoteBtn = findViewById(R.id.floatingActionButtonAdd);
        swipeRefreshLayoutNotes = findViewById(R.id.swipeRefreshLayoutNotes);
        recyclerViewNotes = findViewById(R.id.RecyclerViewNotes);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                void onAddButtonClick() {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_ip:
                changeAddressDialog();
                break;
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

    private void changeAddressDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View alertView = layoutInflater.inflate(R.layout.alertdialog_simple_input, null);

        final EditText editTextInput = alertView.findViewById(R.id.editTextDialogInput);
//        editTextInput.setText(LocalStorageHelper.getServerAddress(this));

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.enter_address))
                .setView(alertView)
                .setPositiveButton(getString(R.string.set_new_address),
                        (dialog, which) -> {
                            String input = editTextInput.getText().toString();

//                            if (AddressVeryfication.verify(this, input)) {
//                                LocalStorageHelper.setServerAddress(NotesActivity.this, input);
//                            }
                        });

        builder.show();
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

        IntentFilter filter = new IntentFilter("ACTION_BROADCAST_RESPONSE");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        swipeRefreshLayoutNotes.setOnRefreshListener(() -> {
            Intent syncIntentService = new Intent(this, NotesSynchronizationService.class);
            startService(syncIntentService);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                int id = extras.getInt("NOTE_POSITION");
//                notesList.get(id).setContext(extras.getString(IntentExtras.NOTE_CONTEXT));
//                notesList.get(id).setLastEditDate(extras.getString(IntentExtras.NOTE_DATE));
                notesAdapter.notifyItemChanged(id);

            }
        }
    }

    private void refreshingServiceFinished() {
        swipeRefreshLayoutNotes.setRefreshing(false);
        new GetAllNotes().execute();
    }

    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NotesActivity.this.refreshingServiceFinished();
        }
    }

    private class AddNewNote extends AsyncTask<NoteEntity, Void, List<NoteEntity>> {

        @Override
        protected List<NoteEntity> doInBackground(NoteEntity... notesEntities) {
            if (notesEntities.length != 1) {
                return null;
            }

//            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, AppDatabase.DatabaseName)
//                    .addMigrations(AppDatabase.MIGRATION_1_2)
//                    .build();
//            database.notesDao().Insert(notesEntities[0]);
//            List<NotesEntity> listOfNotes = database.notesDao().getNotRemovedNotes();
//            database.close();

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<NoteEntity> notesEntities) {
            super.onPostExecute(notesEntities);

            notesList = notesEntities;
            notesAdapter.setNotesList(notesEntities);
            notesAdapter.notifyItemInserted(notesList.size() - 1);
        }
    }

    private class GetAllNotes extends AsyncTask<Void, Void, List<NoteEntity>> {

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
//            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, AppDatabase.DatabaseName)
//                    .addMigrations(AppDatabase.MIGRATION_1_2)
//                    .build();
//            List<NotesEntity> listOfNotes = database.notesDao().getNotRemovedNotes();
//            database.close();

            return new ArrayList<>();
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
                Intent intent = new Intent(NotesActivity.this, EditorActivity.class);

                intent.putExtra(getString(R.string.animation_transition_notes_to_editor), position);
//                intent.putExtra(IntentExtras.NOTE_POSITION, position);
//                intent.putExtra(IntentExtras.NOTE_ID, id);
//                intent.putExtra(IntentExtras.NOTE_CONTEXT, noteContext);
//                intent.putExtra(IntentExtras.NOTE_DATE, lastEditDate);
//                intent.putExtra(IntentExtras.NOTE_COLOR, color);

                Pair<View, String> p1 = Pair.create(cardView, getString(R.string.animation_transition_note_background));
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(NotesActivity.this, p1);
//                startActivityForResult(intent, REQUEST_CODE, options.toBundle());
            });
        }
    }
}
