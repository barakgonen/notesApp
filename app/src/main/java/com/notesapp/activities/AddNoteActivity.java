package com.notesapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.notesapp.model.NoteEntity;
import com.notesapp.model.PriorityEnum;
import com.notesapp.network.NotesRestServicesHandler;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNoteActivity extends AppCompatActivity {

    TextView titleTxt;
    TextView bodyTxt;
    TextView backGroundColorTxt;
    SeekBar seekBar;
    Button saveNoteBtsn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        titleTxt = findViewById(R.id.noteTitleEditTxt);
        bodyTxt = findViewById(R.id.noteBodyEditTxt);
        saveNoteBtsn = findViewById(R.id.saveNoteBtn);
        backGroundColorTxt = findViewById(R.id.backgroundColor);
        seekBar = findViewById(R.id.seekBar);

        saveNoteBtsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTxt.getText().toString();
                String body = bodyTxt.getText().toString();
                PriorityEnum priorityEnum = PriorityEnum.values()[seekBar.getProgress() - 1];
                String backGroundColor = backGroundColorTxt.getText().toString();
                NotesRestServicesHandler.
                        addNote(new NoteEntity(title, body, priorityEnum, false, backGroundColor));
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
