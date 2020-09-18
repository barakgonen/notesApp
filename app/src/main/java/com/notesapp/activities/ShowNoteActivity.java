package com.notesapp.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.notesapp.model.NotesAdapter;

public class ShowNoteActivity extends AppCompatActivity {

    TextView txtTitleVal;
    TextView txtBodyVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtTitleVal = findViewById(R.id.titleTxtVal);
        txtBodyVal = findViewById(R.id.bodyVal);
        Bundle bundle = getIntent().getExtras();
        initSetupFields(bundle);
        setWindowColors(bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);

        finishAfterTransition();
    }

    private void initSetupFields(Bundle bundle) {
        if (bundle != null) {
            String title = bundle.getString("title");
            String body = bundle.getString("body");
            String color = bundle.getString("color");
            txtBodyVal.setText(body);
            txtTitleVal.setText(title);
        }
    }

    private void setWindowColors(Bundle bundle) {
        if (bundle == null) {
            return;
        }

        int noteId = 1;//bundle.getInt(IntentExtras.NOTE_ID);
        int primaryColor = getPrimaryColor(noteId);
        int primaryColorDark = getPrimaryDarkColor(noteId);

        Window window = getWindow();
        window.setNavigationBarColor(primaryColor);
        window.setStatusBarColor(primaryColorDark);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        }
    }

    private int getPrimaryColor(int id) {
        switch (id % NotesAdapter.COLORS_COUNT) {
            case 0:
                return ContextCompat.getColor(this, R.color.color_note_0_primary);
            case 1:
                return ContextCompat.getColor(this, R.color.color_note_1_primary);
            case 2:
                return ContextCompat.getColor(this, R.color.color_note_2_primary);
            case 3:
                return ContextCompat.getColor(this, R.color.color_note_3_primary);
            case 4:
                return ContextCompat.getColor(this, R.color.color_note_4_primary);
            case 5:
                return ContextCompat.getColor(this, R.color.color_note_5_primary);
            case 6:
                return ContextCompat.getColor(this, R.color.color_note_6_primary);
            default:
                return 0;
        }
    }

    private int getPrimaryDarkColor(int id) {
        switch (id % NotesAdapter.COLORS_COUNT) {
            case 0:
                return ContextCompat.getColor(this, R.color.color_note_0_primary_dark);
            case 1:
                return ContextCompat.getColor(this, R.color.color_note_1_primary_dark);
            case 2:
                return ContextCompat.getColor(this, R.color.color_note_2_primary_dark);
            case 3:
                return ContextCompat.getColor(this, R.color.color_note_3_primary_dark);
            case 4:
                return ContextCompat.getColor(this, R.color.color_note_4_primary_dark);
            case 5:
                return ContextCompat.getColor(this, R.color.color_note_5_primary_dark);
            case 6:
                return ContextCompat.getColor(this, R.color.color_note_6_primary_dark);
            default:
                return 0;
        }
    }
}
