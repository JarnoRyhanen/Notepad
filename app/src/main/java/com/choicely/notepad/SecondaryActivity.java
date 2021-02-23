package com.choicely.notepad;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.choicely.notepad.dp.RealmHelper;

import io.realm.Realm;
import io.realm.Sort;

public class SecondaryActivity extends AppCompatActivity {

    private final static String TAG = "SecondaryActivity";
    private LinearLayout linearLayout;
    private Button redButton;
    private Button yellowButton;
    private Button greenButton;
    private Button blueButton;
    private Button whiteButton;
    private EditText editTextTitle;
    private EditText editTextContent;
    private int noteID;
    private int color;

    private int redColor;
    private int greenColor;
    private int blueColor;
    private int yellowColor;
    private int whiteColor;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity);
        linearLayout = findViewById(R.id.secondary_activity_linear_layout);
        redButton = findViewById(R.id.secondary_activity_red_button);
        yellowButton = findViewById(R.id.secondary_activity_yellow_button);
        greenButton = findViewById(R.id.secondary_activity_green_button);
        blueButton = findViewById(R.id.secondary_activity_blue_button);
        whiteButton = findViewById(R.id.secondary_activity_white_button);
        editTextTitle = findViewById(R.id.secondary_activity_title);
        editTextContent = findViewById(R.id.secondary_activity_text);

        noteID = (int) getIntent().getLongExtra(IntentKeys.NOTE_ID, -1);
        getColors();

        if (noteID == -1) {
            newNote();
        } else {
            loadNote();
        }
    }

    private void getColors() {
        redColor = getResources().getColor(R.color.red);
        greenColor = getResources().getColor(R.color.green);
        blueColor = getResources().getColor(R.color.blue);
        yellowColor = getResources().getColor(R.color.yellow);
        whiteColor = getResources().getColor(R.color.white);
    }

    public void onClick(View view) {
        if (view == redButton) {
            updateScreen(redColor);
        } else if (view == greenButton) {
            updateScreen(greenColor);
        } else if (view == blueButton) {
            updateScreen(blueColor);
        } else if (view == yellowButton) {
            updateScreen(yellowColor);
        } else if (view == whiteButton) {
            updateScreen(whiteColor);
        }
    }

    private void updateScreen(int color) {
        linearLayout.setBackgroundColor(color);
        updateBackgroundAlpha();
        this.color = color;
    }

    private void updateBackgroundAlpha() {
        editTextContent.getBackground().setAlpha(150);
        editTextTitle.getBackground().setAlpha(150);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNote();
    }

    private void newNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData lastNote = realm.where(NoteData.class).sort("id", Sort.DESCENDING).findFirst();
        if (lastNote != null) {
            noteID = (int) (lastNote.getId() + 1);
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            editTextContent.getBackground().setAlpha(150);
            editTextTitle.getBackground().setAlpha(150);
            color = Color.WHITE;
        } else {
            noteID = 0;
        }
    }

    private void loadNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData note = realm.where(NoteData.class).equalTo("id", noteID).findFirst();

        editTextTitle.setText(note.getTitle());
        editTextContent.setText(note.getNoteText());
        Log.d(TAG, "color is: " + color);
        getWindow().getDecorView().setBackgroundColor(note.getColor());
        color = note.getColor();
        editTextContent.getBackground().setAlpha(150);
        editTextTitle.getBackground().setAlpha(150);

    }

    private void saveNote() {
        Realm realm = RealmHelper.getInstance().getRealm();
        NoteData noteData = new NoteData();

        Log.d(TAG, "set title: " + editTextTitle.getText());
        Log.d(TAG, "set text: " + editTextContent.getText());

        noteData.setId(noteID);
        noteData.setColor(color);
        noteData.setTitle(String.valueOf(editTextTitle.getText()));
        noteData.setNoteText(String.valueOf(editTextContent.getText()));


        realm.executeTransaction(realm1 -> {
            realm.insertOrUpdate(noteData);
        });
        Log.d(TAG, "Note saved with the id:" + noteData.getId());
    }
}
