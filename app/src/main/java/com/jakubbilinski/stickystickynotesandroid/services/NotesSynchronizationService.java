package com.jakubbilinski.stickystickynotesandroid.services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.app.model.NoteEntity;
import com.jakubbilinski.stickystickynotesandroid.networking.NotesNetworking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jbili on 13.11.2017.
 */

public class NotesSynchronizationService extends IntentService{
    private NotesNetworking networking;
    private static ArrayList<NoteEntity> recentPolledNotes;
    private static ArrayList<NoteEntity> currentNotesList;
    private PostService postsService;

    public NotesSynchronizationService() {
        super(String.valueOf((new Random()).nextInt())); // Random name generator for service
        currentNotesList = new ArrayList<>();
        recentPolledNotes = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postsService = retrofit.create(PostService.class);
    }

    public static List<NoteEntity> getNotesForUpdate() {
        return recentPolledNotes;
    }

    private void fetchAllNotesFromServer() {
        recentPolledNotes.clear();
        Call<NoteEntity[]> call = postsService.getAllNotes();
        call.enqueue(new Callback<NoteEntity[]>() {
                @Override
                public void onResponse(Call<NoteEntity[]> call, Response<NoteEntity[]> response) {
                    for (NoteEntity entity : response.body()){
                        recentPolledNotes.add(entity);
                    }
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<NoteEntity[]> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        networking = new NotesNetworking(this);
        fetchAllNotesFromServer();

        // remove from recent polled notes, every note that already had drawn
        for (NoteEntity noteEntity : recentPolledNotes)
            if (currentNotesList.indexOf(noteEntity) != -1)
                recentPolledNotes.remove(noteEntity);
        currentNotesList.addAll(recentPolledNotes);
    }
}
