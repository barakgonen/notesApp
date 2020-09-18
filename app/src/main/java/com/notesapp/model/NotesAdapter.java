package com.notesapp.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jakubbilinski.stickystickynotesandroid.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<NoteEntity> notesList;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener onItemClickLongListener;

    public NotesAdapter(List<NoteEntity> notesList) {
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
            holder.cardViewNote.setCardBackgroundColor(item.getBgColor());

            holder.cardViewNote.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(
                            holder.getAdapterPosition(),
                            idFromList,
                            contextMessage,
                            item.getBgColor(),
                            holder.cardViewNote);
                }
            });

            holder.cardViewNote.setOnLongClickListener(view -> {
                if (onItemClickLongListener != null) {
                    onItemClickLongListener.onClick(
                            holder.getAdapterPosition(),
                            idFromList,
                            contextMessage,
                            item.getBgColor(),
                            holder.cardViewNote);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        try {
            return notesList.size();
        } catch (Exception e) {
            System.out.println("EXCEPTION BRO" + e.getMessage());
            return 0;
        }
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
