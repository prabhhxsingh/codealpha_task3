package com.example.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion, textViewScore;
    private EditText editTextAnswer;
    private ArrayList<Flashcard> flashcards;
    private int currentCardIndex = 0;
    private int correctAnswers = 0;
    private int totalQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewScore = findViewById(R.id.textViewScore);
        editTextAnswer = findViewById(R.id.editTextAnswer);

        flashcards = loadFlashcards();
        totalQuestions = flashcards.size();

        Collections.shuffle(flashcards); // Randomize flashcards

        displayQuestion();

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private ArrayList<Flashcard> loadFlashcards() {
        return null;
    }

    private void displayQuestion() {
        if (currentCardIndex < flashcards.size()) {
            Flashcard currentCard = flashcards.get(currentCardIndex);
            textViewQuestion.setText(currentCard.getQuestion());
            editTextAnswer.setText("");
        } else {
            showScore();
        }
    }

    private void checkAnswer() {
        String userAnswer = editTextAnswer.getText().toString().trim();
        Flashcard currentCard = flashcards.get(currentCardIndex);
        String correctAnswer = currentCard.getAnswer();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            correctAnswers++;
        }

        currentCardIndex++;
        if (currentCardIndex < flashcards.size()) {
            displayQuestion();
        } else {
            showScore();
        }
    }

    private void showScore() {
        textViewQuestion.setText("Quiz completed!");
        textViewScore.setText("Score: " + correctAnswers + "/" + totalQuestions);
    }
}
