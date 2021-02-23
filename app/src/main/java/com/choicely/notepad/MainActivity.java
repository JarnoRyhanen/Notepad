package com.choicely.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.choicely.notepad.dp.RealmHelper;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView newNote;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNote = findViewById(R.id.main_activity_add_note);
        recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);

        noteID = (int) getIntent().getLongExtra(IntentKeys.NOTE_ID, -1);

        updateContent();

        newNote.setOnClickListener(v -> {
            addNewNote(newNote);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateContent();
    }
    private void updateContent() {
        adapter.clear();
        Realm realm = RealmHelper.getInstance().getRealm();

        RealmResults<NoteData> notes = realm.where(NoteData.class).findAll();

        for (NoteData note : notes) {
            adapter.add(note);
        }
        adapter.notifyDataSetChanged();
    }

    public void addNewNote(View view) {
        Intent intent = new Intent(this, SecondaryActivity.class);
        startActivity(intent);
    }
}