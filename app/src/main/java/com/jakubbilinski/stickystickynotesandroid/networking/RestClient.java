package com.jakubbilinski.stickystickynotesandroid.networking;

import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;
import com.jakubbilinski.stickystickynotesandroid.networking.items.ResultItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by jbili on 07.11.2017.
 */

public interface RestClient {
    @PUT("/api/notes")
    Call<Integer> createNewNote(@Body NotesItem note);

    @POST("/api/notes")
    Call<ResultItem> updateNotes(@Body List<NotesItem> note);

    @GET("/api/notes")
    Call<List<NotesItem>> getAllNotes();
}
