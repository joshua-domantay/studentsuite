package comp380.hanyjoshallie.studentsuite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        if(getSupportActionBar() != null) { this.getSupportActionBar().hide(); }    // Hide action bar above app

        EditText editText = findViewById(R.id.editText);

        // Fetch data that is passed from NoteFragment
        Intent intent = getIntent();

        // Accessing the data using key and value
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(NoteFragment.notes.get(noteId));
        } else {

            NoteFragment.notes.add("");
            noteId = NoteFragment.notes.size() - 1;
            NoteFragment.arrayAdapter.notifyDataSetChanged();

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NoteFragment.notes.set(noteId, String.valueOf(charSequence));
                NoteFragment.arrayAdapter.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("comp380.hanyjoshallie.studentsuite", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(NoteFragment.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });
    }
}