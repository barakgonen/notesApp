package com.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.model.NoteEntity;
import com.example.app.model.PriorityEnum;
import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.services.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNoteActivity extends AppCompatActivity {

    TextView titleTxt;
    TextView bodyTxt;
    TextView backGroundColorTxt;
    SeekBar seekBar;
    Button saveNoteBtsn;
    private PostService postsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        titleTxt = findViewById(R.id.noteTitleEditTxt);
        bodyTxt = findViewById(R.id.noteBodyEditTxt);
        saveNoteBtsn = findViewById(R.id.saveNoteBtn);
        backGroundColorTxt = findViewById(R.id.backgroundColor);
        seekBar = findViewById(R.id.seekBar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/notes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postsService = retrofit.create(PostService.class);

        saveNoteBtsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTxt.getText().toString();
                String body = bodyTxt.getText().toString();
                PriorityEnum priorityEnum = PriorityEnum.values()[seekBar.getProgress() - 1];
                String backGroundColor = backGroundColorTxt.getText().toString();
                Toast.makeText(getApplicationContext(), "Values: Title: " + title + ", body: " + body + ", priority: " + priorityEnum.toString(), Toast.LENGTH_SHORT).show();

                String url = "http://10.0.2.2:8080/notes/";
                sendPost(title, body, priorityEnum, backGroundColor);
                Intent returnIntent = new Intent();
//                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void sendPost(String title, String body, PriorityEnum priorityEnum, String color) {
        NoteEntity post = new NoteEntity(title, body, priorityEnum, false, color);

        Call<NoteEntity> call = postsService.sendPosts(post);
        call.enqueue(new Callback<NoteEntity>() {
            @Override
            public void onResponse(Call<NoteEntity> call, Response<NoteEntity> response) {
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NoteEntity> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
