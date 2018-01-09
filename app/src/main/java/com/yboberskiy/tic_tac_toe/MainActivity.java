package com.yboberskiy.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // activePlayer = 3 (cross), activePlayer = 4 (zero)
    // 11 means unplayed
    int activePlayer = 3;
    int [] gameState = {11,11,11,11,11,11,11,11,11};

    // game state 0 - means active, state 1 - means ended
    boolean gameIsActive = true;

    // track number of moves
    int numberOfMoves = 0;

    // win position combinations in the array gameState
    int [][] winStates = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}};

    ImageView dropPointImageView;
    Button startAgainButton;
    TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAgainButton = (Button) findViewById(R.id.startAgainButton);
        infoTextView = (TextView) findViewById(R.id.infoTextView);

        startAgainButton.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);

    }

    public void dropIn (View view) {
            dropPointImageView = (ImageView) view;
            int tappedChip = Integer.parseInt(dropPointImageView.getTag().toString());
            if (gameIsActive) {
                updateGameState(tappedChip, activePlayer);
                numberOfMoves ++;
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
            for (int k = 0; k < 3; k ++) {
                sum += gameState[winStates[i][k]];
                if (sum == 12 || sum == 9) {
                    return sum;
                }
            }
            sum = 0;
        }
        return sum;
    }

    public void updateGameState (int tappedChip, int activePlayer) {
        if (gameState[tappedChip] == 11) {
            dropPointImageView.setTranslationX(-100f);
            gameState[tappedChip] = activePlayer;
            dropPointImageView.animate().translationXBy(100f).setDuration(50);
            switchPlayer();
            int winCondition = checkWinCondition();
            if ((winCondition == 9 || winCondition == 12) ||
                    winCondition == 0 && numberOfMoves == 8) {
                gameIsActive = false;
                showControls(winCondition);
            }
        }
    }

    public void showControls(int winner) {
        startAgainButton.setVisibility(View.VISIBLE);
        infoTextView.setVisibility(View.VISIBLE);

        if (winner == 9) {
            infoTextView.setText("X won! \n Press continue to play again!");
        } else if (winner == 12) {
            infoTextView.setText("O won! \n Press continue to play again!");
        } else {
            infoTextView.setText("Standoff! \n Press continue to play again!");
        }
    }

    public void hideControls () {
        startAgainButton.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);
    }

    public void resetTheGame (View view) {
        gameIsActive = true;
        hideControls ();
        int [] resetGameState = {11,11,11,11,11,11,11,11,11};
        gameState = resetGameState;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        int childCount = gridLayout.getChildCount();
        infoTextView.setText("");

        for (int i = 0; i < childCount; i ++) {
            ImageView childView = (ImageView) gridLayout.getChildAt(i);
            if (childView != null) {
                childView.setImageResource(0);
            }
        }
    }


}
