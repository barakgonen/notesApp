package com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.model.NoteEntity;
import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.services.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNoteActivity extends AppCompatActivity {

    TextView txtView;
    TextView titleTxt;
    TextView bodyTxt;
    TextView priorityTxt;
    Button saveNoteBtsn;
    private PostService postsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        titleTxt = findViewById(R.id.noteTitleEditTxt);
        bodyTxt = findViewById(R.id.noteBodyEditTxt);
        priorityTxt = findViewById(R.id.notePriorityEditTxt);
        saveNoteBtsn = findViewById(R.id.saveNoteBtn);
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
                String prioritty = priorityTxt.getText().toString();
                Toast.makeText(getApplicationContext(), "Values: Title: " + title + ", body: " + body + ", priority: " + prioritty, Toast.LENGTH_SHORT).show();

                String url = "http://10.0.2.2:8080/notes";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                sendPost();
            }
        });
    }

    private void sendPost() {
//        NoteEntity post = new NoteEntity("blabla", "170820");
//
//        Call<NoteEntity> call = postsService.sendPosts(post);
//        call.enqueue(new Callback<NoteEntity>() {
//            @Override
//            public void onResponse(Call<NoteEntity> call, Response<NoteEntity> response) {
////                lblresponse.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<NoteEntity> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        });
    }
}
