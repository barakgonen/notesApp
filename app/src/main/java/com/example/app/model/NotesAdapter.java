package com.example.app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbili on 21.11.2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    public static final int COLORS_COUNT = 7;

    private List<NoteEntity> notesList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener onItemClickLongListener;

    public NotesAdapter(Context context, List<NoteEntity> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    public void setNotesList(List<NoteEntity> notesList) {
        this.notesList = notesList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickLongListener(OnItemClickListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, int id, String noteContext, int color, CardView cardView);
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycleritem_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        final String contextMessage = "TMP DATA";/*notesList.get(position).getContext();*/
        final int idFromList = 1;/*notesList.get(holder.getAdapterPosition()).getId();*/
        NoteEntity item = notesList.get(position);

        if (item != null) {
            holder.textViewNoteContext.setText(item.getTitle());
        } else {
            holder.textViewNoteContext.setText(context.getString(R.string.empty_note_message));
        }
        holder.cardViewNote.setCardBackgroundColor(generateColor(2));

        holder.cardViewNote.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(
                        holder.getAdapterPosition(),
                        idFromList,
                        contextMessage,
                        generateColor(2),
                        holder.cardViewNote);
            }
        });

        holder.cardViewNote.setOnLongClickListener(view -> {
            if (onItemClickLongListener != null) {
                onItemClickLongListener.onClick(
                        holder.getAdapterPosition(),
                        idFromList,
                        contextMessage,
                        -1, // Color is irrelevant, so it's set to -1 value
                        holder.cardViewNote);
            }

            return true;
        });
    }

    public int generateColor(int id) {
        switch (id % COLORS_COUNT) {
            case 0:
                return ContextCompat.getColor(context, R.color.color_note_0_background);
            case 1:
                return ContextCompat.getColor(context, R.color.color_note_1_background);
            case 2:
                return ContextCompat.getColor(context, R.color.color_note_2_background);
            case 3:
                return ContextCompat.getColor(context, R.color.color_note_3_background);
            case 4:
                return ContextCompat.getColor(context, R.color.color_note_4_background);
            case 5:
                return ContextCompat.getColor(context, R.color.color_note_5_background);
            case 6:
                return ContextCompat.getColor(context, R.color.color_note_6_background);
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewNote;
        TextView textViewNoteContext;

        NotesViewHolder(View itemView) {
            super(itemView);

            cardViewNote = itemView.findViewById(R.id.cardViewNote);
            textViewNoteContext = itemView.findViewById(R.id.textViewNoteContext);
        }
    }
}
