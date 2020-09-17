package com.jakubbilinski.stickystickynotesandroid.networking;

import android.content.Context;
import android.os.AsyncTask;

import com.example.app.model.NoteEntity;
import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jbili on 23.11.2017.
 */


// this can be network adapter
public class NotesNetworking {

    private Context context;

    public NotesNetworking(Context context/*, AppDatabase lastKnownDatabase*/) {
        this.context = context;
//        this.database = lastKnownDatabase;
    }

    public void addNote(NotesItem notesItem, NoteEntity notesEntity, Callable<Void> after) {
//        RestClient restClient = RestSystem.buildWithAuthentication(context);
//        Call<Integer> call = restClient.createNewNote(notesItem);
//
//        call.enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                try {
//                    if (response.isSuccessful() && response.body() != null) {
////                        notesEntity.setServerId(response.body());
//                        new UpdateNoteWithId().execute(notesEntity);
//                    }
//
//                    after.call();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//                try {
//                    after.call();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void updateNotes(List<NotesItem> notesItems, Callable<Void> after) {
//        RestClient restClient = RestSystem.buildWithAuthentication(context);
//        Call<ResultItem> call = restClient.updateNotes(notesItems);
//
//        call.enqueue(new Callback<ResultItem>() {
//            @Override
//            public void onResponse(Call<ResultItem> call, Response<ResultItem> response) {
//                try {
//                    after.call();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResultItem> call, Throwable t) {
//                try {
//                    after.call();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void getNotes(Callable<Void> after) {
//        RestClient restClient = RestSystem.buildWithAuthentication(context);
//        Call<List<NotesItem>> call = restClient.getAllNotes();
//
//        call.enqueue(new Callback<List<NotesItem>>() {
//            @Override
//            public void onResponse(Call<List<NotesItem>> call, Response<List<NotesItem>> response) {
//                if (response.isSuccessful()) {
//                    NotesItemListWithCallableContainer container = new NotesItemListWithCallableContainer();
//                    container.list = response.body();
//                    container.callable = after;
//
//                    new UpdateLocalNotes().execute(container);
//                } else {
//                    try {
//                        after.call();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<NotesItem>> call, Throwable t) {
//                try {
//                    after.call();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private class UpdateLocalNotes extends AsyncTask<NotesItemListWithCallableContainer, Void, Void> {

        @Override
        protected Void doInBackground(NotesItemListWithCallableContainer[] lists) {
            if (lists.length != 1) {
                return null;
            }

//            List<NotesItem> listOfItemsFromServer = lists[0].list;
//            List<NoteEntity> listOfLocalItems = database.notesDao().getAll();
//
//            List<NoteEntity> listOfNotesToBeUpdated = new ArrayList<>();
//            List<NoteEntity> listOfNotesToBeAdded = new ArrayList<>();
//
//            for (NotesItem notesItem : listOfItemsFromServer) {
//                boolean foundItemWithId = false;
//                NoteEntity foundEntity = null;
//
//                for (NoteEntity notesEntity : listOfLocalItems) {
//                    if (notesItem.getId() == notesEntity.getServerId()) {
//                        foundEntity = notesEntity;
////                        notesEntity.setContext(notesItem.getContext());
////                        notesEntity.setLastEditDate(notesItem.getLastEditDate());
////                        notesEntity.setRemoved(notesItem.isRemoved());
////                        foundItemWithId = true;
//                        break;
//                    }
//                }
//
//                if (foundItemWithId) {
//                    listOfNotesToBeUpdated.add(foundEntity);
//                } else {
//                    listOfNotesToBeAdded.add(new NoteEntity(notesItem));
//                }
//            }
//
//            database.notesDao().Insert(listOfNotesToBeAdded);
//            database.notesDao().Update(listOfNotesToBeUpdated);
//
//            try {
//                lists[0].callable.call();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            return null;
        }
    }

    private class UpdateNoteWithId extends AsyncTask<NoteEntity, Void, Void> {

        @Override
        protected Void doInBackground(NoteEntity... notesEntities) {
            if (notesEntities.length != 1) {
                return null;
            }

//            database.notesDao().Update(notesEntities[0]);
            return null;
        }
    }

    private class NotesItemListWithCallableContainer {
        List<NotesItem> list;
        Callable<Void> callable;
    }
}
