package com.yboberskiy.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // activePlayer = 3 (cross), activePlayer = 4 (zero)
    // 11 means unplayed

    int activePlayer = 3;
    ImageView dropPointImageView;
    int [] gameState = {11,11,11,11,11,11,11,11,11};
    int [][] winStates = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn (View view) {
            dropPointImageView = (ImageView) view;
            int tappedChip = Integer.parseInt(dropPointImageView.getTag().toString());
            updateGameState(tappedChip, activePlayer);
            if (checkWinCondition() == 9) {
                Toast.makeText(this, " X - WIN!", Toast.LENGTH_SHORT).show();
            }
            if (checkWinCondition() == 12) {
                Toast.makeText(this, " O - WIN!", Toast.LENGTH_SHORT).show();
            }
    }

    public void switchPlayer() {
        if (activePlayer == 4) {
            dropPointImageView.setImageResource(R.drawable.tictactoe_o);
            activePlayer = 3;
        } else if (activePlayer == 3) {
            dropPointImageView.setImageResource(R.drawable.tictactoe_x);
            activePlayer = 4;
        }
    }

    public int checkWinCondition () {
        int sum = 0;
        for (int i = 0; i < 8; i ++) {
            sum = 0;
            for (int k = 0; k < 3; k ++) {
                sum += gameState[winStates[i][k]];
                if (sum == 12 || sum == 9) {
                    return sum;
                }
            }
        }
        return sum;
    }

    public void updateGameState (int tappedChip, int activePlayer) {
        if (gameState[tappedChip] == 11) {
            dropPointImageView.setTranslationX(-1000f);
            gameState[tappedChip] = activePlayer;
            dropPointImageView.animate().translationXBy(1000f).setDuration(300);
            switchPlayer();
        }
    }
}
