package com.notesapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakubbilinski.stickystickynotesandroid.R;
import com.notesapp.model.NoteEntity;
import com.notesapp.model.PriorityEnum;
import com.notesapp.network.NotesRestServicesHandler;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddNoteActivity extends AppCompatActivity {

    private TextView titleTxt;
    private TextView bodyTxt;
    private SeekBar seekBar;
    private Button saveNoteBtsn;
    private FloatingActionButton selectColorBtn;
    private static int chosenColor;
    private ArrayList<String> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        colors = new ArrayList<>();
        titleTxt = findViewById(R.id.noteTitleEditTxt);
        bodyTxt = findViewById(R.id.noteBodyEditTxt);
        saveNoteBtsn = findViewById(R.id.saveNoteBtn);
        seekBar = findViewById(R.id.seekBar);

        selectColorBtn = findViewById(R.id.colorPickerButton);
        if (selectColorBtn != null) {
            selectColorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ColorPicker colorPicker = new ColorPicker(AddNoteActivity.this);
                    colorPicker
                            .setDefaultColorButton(ContextCompat.getColor(getApplicationContext(), R.color.window_background))
                            .setColumns(5)
                            .setRoundColorButton(true)
                            .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                                @Override
                                public void onChooseColor(int position, int color) {
                                    chosenColor = color;
                                }

                                @Override
                                public void onCancel() {
                                    chosenColor = ContextCompat.getColor(getApplicationContext(), R.color.window_background);
                                    ;
                                }
                            })
                            .show();
                }
            });
        }

        saveNoteBtsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTxt.getText().toString();
                String body = bodyTxt.getText().toString();
                PriorityEnum priorityEnum = PriorityEnum.values()[seekBar.getProgress() - 1];

                NotesRestServicesHandler.
                        addNote(new NoteEntity(title, body, priorityEnum, false, chosenColor));
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                chosenColor = ContextCompat.getColor(getApplicationContext(), R.color.window_background);
                finish();
            }
        });
    }
}
