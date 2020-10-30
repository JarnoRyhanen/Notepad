package com.choicely.notepad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static final String TAG = "NoteAdapter";
    private final Context context;
    private final List<NoteData> list = new ArrayList<>();

    public NoteAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteData note = list.get(position);

        holder.noteID = note.getId();

        holder.title.setText(note.getTitle());
        holder.title.setTextColor(Color.BLACK);
        holder.title.setBackgroundColor(note.getColor());

        holder.text.setText(note.getNoteText());
        holder.text.setTextColor(Color.BLACK);
        holder.text.setBackgroundColor(note.getColor());
        holder.text.getBackground().setAlpha(128);




    }

    public void add(NoteData note) {
        list.add(note);
    }

    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        long noteID;
        TextView title;
        TextView text;

        public NoteViewHolder(@NonNull View view) {
            super(view);
            view.setOnClickListener(onRowClick);

            title = view.findViewById(R.id.note_list_row_title);
            text = view.findViewById(R.id.note_list_row_text);


        }

        private View.OnClickListener onRowClick = v -> {
            Context ctx = itemView.getContext();
            Intent intent = new Intent(ctx, SecondaryActivity.class);
            intent.putExtra(IntentKeys.NOTE_ID, noteID);
            ctx.startActivity(intent);
        };

    }

}

