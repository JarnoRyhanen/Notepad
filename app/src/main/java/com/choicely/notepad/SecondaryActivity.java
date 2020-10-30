package com.choicely.notepad;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.choicely.notepad.dp.RealmHelper;

import io.realm.Realm;
import io.realm.Sort;

public class SecondaryActivity extends AppCompatActivity {

    private final static String TAG = "SecondaryActivity";

    private Button saveButton;
    private Button red;
    private Button yellow;
    private Button green;
    private Button blue;
    private Button white;
    private EditText title;
    private EditText text;
    private int noteID;
    private int color;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity);

        saveButton = findViewById(R.id.secondary_activity_save_button);
        red = findViewById(R.id.secondary_activity_red_button);
        yellow = findViewById(R.id.secondary_activity_yellow_button);
        green = findViewById(R.id.secondary_activity_green_button);
        blue = findViewById(R.id.secondary_activity_blue_button);
        white = findViewById(R.id.secondary_activity_white_button);
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

        red.setOnClickListener(v -> {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
            text.getBackground().setAlpha(150);
            title.getBackground().setAlpha(150);
            color = Color.RED;
        });
        yellow.setOnClickListener(v -> {
            getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            text.getBackground().setAlpha(150);
            title.getBackground().setAlpha(150);
            color = Color.YELLOW;
        });
        green.setOnClickListener(v -> {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            text.getBackground().setAlpha(150);
            title.getBackground().setAlpha(150);
            color = Color.GREEN;
        });
        blue.setOnClickListener(v -> {
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            text.getBackground().setAlpha(150);
            title.getBackground().setAlpha(150);
            color = Color.BLUE;
        });
        white.setOnClickListener(v -> {
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            text.getBackground().setAlpha(150);
            title.getBackground().setAlpha(150);
            color = Color.WHITE;
        });
    }

    private void newNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData lastNote = realm.where(NoteData.class).sort("id", Sort.DESCENDING).findFirst();
        if (lastNote != null) {
            noteID = (int) (lastNote.getId() + 1);
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            text.getBackground().setAlpha(150);
            title.getBackground().setAlpha(150);
            color = Color.WHITE;
        } else {
            noteID = 0;
        }
    }

    private void loadNote() {
        Realm realm = RealmHelper.getInstance().getRealm();

        NoteData note = realm.where(NoteData.class).equalTo("id", noteID).findFirst();
        title.setText(note.getTitle());
        text.setText(note.getNoteText());
        Log.d(TAG, "color is: " + color);
        getWindow().getDecorView().setBackgroundColor(note.getColor());
        color = note.getColor();
        text.getBackground().setAlpha(150);
        title.getBackground().setAlpha(150);

    }

    private void saveNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData noteData = new NoteData();

        Log.d(TAG, "set title: " + title.getText());
        Log.d(TAG, "set text: " + text.getText());

        noteData.setId(noteID);
        noteData.setColor(color);
        noteData.setTitle(String.valueOf(title.getText()));
        noteData.setNoteText(String.valueOf(text.getText()));


        realm.executeTransaction(realm1 -> {
            realm.insertOrUpdate(noteData);
        });
        Log.d(TAG, "Note saved with the id:" + noteData.getId());


    }
}

