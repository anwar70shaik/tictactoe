package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true; // X starts first
    private int roundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Initialize buttons from GridLayout
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> resetGame());
    }

    // Handle button click
    private void onButtonClick(View v) {
        if (!(v instanceof Button)) return;
        Button button = (Button) v;

        if (!button.getText().toString().equals("")) {
            return; // Cell already filled
        }

        if (playerXTurn) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (playerXTurn) {
                showWinner("Player X Wins!");
            } else {
                showWinner("Player O Wins!");
            }
        } else if (roundCount == 9) {
            showWinner("It's a Draw!");
        } else {
            playerXTurn = !playerXTurn;
            tvStatus.setText(playerXTurn ? "Player X's Turn" : "Player O's Turn");
        }
    }

    // Check for win condition
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // Fill the 2D array with button texts
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true; // Row match
            }
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true; // Column match
            }
        }

        // Check diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true; // Diagonal (top-left to bottom-right)
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true; // Diagonal (top-right to bottom-left)
        }

        return false;
    }

    // Show winner or draw message
    private void showWinner(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        resetGame();
    }

    // Reset the game board
    private void resetGame() {
        roundCount = 0;
        playerXTurn = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        tvStatus.setText("Player X's Turn");
    }
}
