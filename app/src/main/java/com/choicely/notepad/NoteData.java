package com.choicely.notepad;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NoteData extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private String noteText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }


}
