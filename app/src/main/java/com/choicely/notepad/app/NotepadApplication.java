package com.choicely.notepad.app;

import android.app.Application;
import android.util.Log;

import com.choicely.notepad.dp.RealmHelper;

public class NotepadApplication extends Application {
    private final static String TAG = "NotepadApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App start");

        RealmHelper.init(this);


    }
}
