package com.choicely.notepad;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.choicely.notepad.dp.RealmHelper;

import io.realm.Realm;
import io.realm.Sort;

public class SecondaryActivity extends AppCompatActivity {

    private final static String TAG = "SecondaryActivity";

    private Button saveButton;
    private EditText title;
    private EditText text;
    private int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity);

        saveButton = findViewById(R.id.secondary_activity_save_button);
        title = findViewById(R.id.secondary_activity_title);
        text = findViewById(R.id.secondary_activity_text);

        noteID = (int) getIntent().getLongExtra(IntentKeys.NOTE_ID, -1);

        if (noteID == -1) {
            newNote();
        } else {
            loadNote();
        }

        saveButton.setOnClickListener(v -> {
            saveNote();
        });


    }

    private void newNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData lastNote = realm.where(NoteData.class).sort("id", Sort.DESCENDING).findFirst();
        if (lastNote != null) {
            noteID = (int) (lastNote.getId() + 1);
        } else {
            noteID = 0;
        }
    }

    private void loadNote() {
        Realm realm = RealmHelper.getInstance().getRealm();

        NoteData note = realm.where(NoteData.class).equalTo("id", noteID).findFirst();
        title.setText(note.getTitle());
        text.setText(note.getNoteText());


    }

    private void saveNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData noteData = new NoteData();

        Log.d(TAG, "set title: " + title.getText());
        Log.d(TAG, "set text: " + text.getText());

        noteData.setId(noteID);
        noteData.setTitle(String.valueOf(title.getText()));
        noteData.setNoteText(String.valueOf(text.getText()));


        realm.executeTransaction(realm1 -> {
            realm.insertOrUpdate(noteData);
        });
        Log.d(TAG, "Note saved with the id:" + noteData.getId());


    }
}

