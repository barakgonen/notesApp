package com.jakubbilinski.stickystickynotesandroid.services;

import com.example.app.model.NoteEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {

    String API_ROUTE = "posts";

    @Headers({

            "Content-type: application/json"

    })
    @POST(API_ROUTE)
    Call<NoteEntity> sendPosts(@Body NoteEntity posts);
}
