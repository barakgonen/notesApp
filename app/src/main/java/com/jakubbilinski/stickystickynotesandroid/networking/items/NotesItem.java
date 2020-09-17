package com.jakubbilinski.stickystickynotesandroid.networking.items;

import com.example.app.model.NoteEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

/**
 * Created by jbili on 10.11.2017.
 */

public class NotesItem {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Context")
    @Expose
    private String context;
    @SerializedName("LastEditDate")
    @Expose
    private String lastEditDate;
    @SerializedName("Removed")
    @Expose
    private boolean removed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public NotesItem(NoteEntity notesEntity) {
        id =  1;//notesEntity.getServerId();
        context = "2";
        lastEditDate = LocalDateTime.now().toString();
        removed = false;
    }
}
