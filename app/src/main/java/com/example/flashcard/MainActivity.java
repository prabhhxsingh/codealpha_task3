package com.example.flashcard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    private EditText editTextQuestion, editTextAnswer;
    private ArrayList<Flashcard> flashcards;
    private SharedPreferences sharedPreferences;
    private static final String FLASHCARD_KEY = "flashcards";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);

        sharedPreferences = getSharedPreferences("FlashcardPreferences", Context.MODE_PRIVATE);
        flashcards = loadFlashcards();

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString().trim();
                String answer = editTextAnswer.getText().toString().trim();
                if (!question.isEmpty() && !answer.isEmpty()) {
                    flashcards.add(new Flashcard(question, answer));
                    saveFlashcards(flashcards);
                    Toast.makeText(MainActivity.this, "Flashcard added", Toast.LENGTH_SHORT).show();
                    editTextQuestion.setText("");
                    editTextAnswer.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFlashcards(ArrayList<Flashcard> flashcards) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(flashcards);
        editor.putString(FLASHCARD_KEY, json);
        editor.apply();
    }

    private ArrayList<Flashcard> loadFlashcards() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FLASHCARD_KEY, null);
        Type type = new TypeToken<ArrayList<Flashcard>>() {
            public Type getType() {
                return null;
            }
        }.getType();
        if (json == null) {
            return new ArrayList<>();
        } else {
            return gson.fromJson(json, type);
        }
    }
}
