package com.jakubbilinski.stickystickynotesandroid.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.app.model.NoteEntity;
import com.jakubbilinski.stickystickynotesandroid.networking.NotesNetworking;
import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jbili on 13.11.2017.
 */

public class NotesSynchronizationService extends IntentService{
    private NotesNetworking networking;
    private int itemsToBeAdded;
    private List<NoteEntity> listOfNotesToBeUpdated;
    private List<NoteEntity> listOfNotesToBeAdded;
    private List<NoteEntity> listOfAllNotes;

    public NotesSynchronizationService() {
        super(String.valueOf((new Random()).nextInt())); // Random name generator for service
    }

    private List<NoteEntity> getNotesForUpdate() {
        List<NoteEntity> notesToBeUpdated = new ArrayList<>();

        for (NoteEntity note : listOfAllNotes) {
//            if (note.getServerId() != -1) {
//                notesToBeUpdated.add(note);
//            }
        }
        return notesToBeUpdated;
    }

    private List<NoteEntity> getNotesForAdding() {
        List<NoteEntity> notesToBeAdded = new ArrayList<>();

        for (NoteEntity note : listOfAllNotes) {
//            if (note.getServerId() == -1) {
//                notesToBeAdded.add(note);
//            }
        }
        return notesToBeAdded;
    }

    private void addNotes () {
//        for (int i = 0; i < listOfNotesToBeAdded.size(); i++) {
//            NoteEntity notesItem = new NoteEntity(listOfNotesToBeAdded.get(i));
//            networking.addNote(notesItem, listOfNotesToBeAdded.get(i), () -> {
//                itemsToBeAdded--;
//
//                if (itemsToBeAdded <= 0) {
//                    updateNotes();
//                }
//                return null;
//            });
//        }
    }

    private void updateNotes () {
        List<NotesItem> notesItemsConverted = new ArrayList<>();

        for (int i = 0; i < listOfNotesToBeUpdated.size(); i++) {
            notesItemsConverted.add(new NotesItem(listOfNotesToBeUpdated.get(i)));
        }

        networking.updateNotes(notesItemsConverted, () -> {
            getNotesFromServer();
            return null;
        });
    }

    private void getNotesFromServer() {
        networking.getNotes(() -> {
            Intent broadcastIntent = new Intent();
//            broadcastIntent.setAction(IntentExtras.ACTION_BROADCAST_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcastIntent);
            return null;
        });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // Should be read from server
//        setupDatabase();
        networking = new NotesNetworking(this);

//        listOfAllNotes = database.notesDao().getAll();
        listOfNotesToBeUpdated = getNotesForUpdate();
        listOfNotesToBeAdded = getNotesForAdding();
        itemsToBeAdded = listOfNotesToBeAdded.size();

        if (itemsToBeAdded != 0) {
            addNotes();
        }  else if (listOfNotesToBeUpdated.size() != 0) {
            updateNotes();
        } else {
            getNotesFromServer();
        }
    }
}
