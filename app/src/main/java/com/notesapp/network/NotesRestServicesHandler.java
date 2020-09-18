package com.notesapp.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.notesapp.model.NoteEntity;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesRestServicesHandler {

    private static AsyncHttpClient client;
    private static Gson GSON;

    private NotesRestServicesHandler() {
        client = Dsl.asyncHttpClient();
        GSON = new Gson();
    }

    public static List<NoteEntity> fetchAllNotesFromServer(Context context) {
        Request getRequest = Dsl.get("http://10.0.2.2:8080/notes/").build();
        if (client == null)
            new NotesRestServicesHandler();

        ListenableFuture<Response> responseFuture = client.executeRequest(getRequest);
        try {
            return Arrays.asList(GSON.fromJson(responseFuture.get().getResponseBody(), NoteEntity[].class));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean deleteNote(Long id) {
        Request deleteRequest = Dsl.delete("http://10.0.2.2:8080/notes/" + id).build();
        ListenableFuture<Response> responseFuture = client.executeRequest(deleteRequest);
        try {
            responseFuture.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addNote(NoteEntity entityNoteToAdd) {
        Request s = new RequestBuilder(HttpConstants.Methods.POST)
                .setBody(GSON.toJson(entityNoteToAdd))
                .setUrl("http://10.0.2.2:8080/notes/postNote/")
                .build();
        s.getHeaders().add("Content-Type", "application/json");

        System.out.println(s.getHeaders());

        ListenableFuture<Response> responseFuture = client.executeRequest(s);
        return true;
    }
}
