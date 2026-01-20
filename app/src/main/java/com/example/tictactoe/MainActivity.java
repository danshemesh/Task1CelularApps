package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private GameLogic gameLogic;
    private ImageView status;
    private Button resetButton;

    private final int[] cellIds = {
            R.id.topLeft, R.id.topCenter, R.id.topRight,
            R.id.middleLeft, R.id.middleCenter, R.id.middleRight,
            R.id.bottomLeft, R.id.bottomCenter, R.id.bottomRight
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gameLogic = new GameLogic();
        status = findViewById(R.id.status);
        resetButton = findViewById(R.id.resetButton);
    }

    public void playerTap(View view) {
        if (!gameLogic.isGameActive()) {
            return;
        }

        ImageView img = (ImageView) view;
        int tappedPosition = Integer.parseInt(img.getTag().toString());

        if (!gameLogic.isCellEmpty(tappedPosition)) {
            return;
        }

        gameLogic.makeMove(tappedPosition);
        animateCellTap(img);
        updateCellImage(img);
        
        int winIndex = gameLogic.checkWinner();
        if (winIndex >= 0) {
            handleWin(winIndex);
            return;
        }

        if (gameLogic.isDraw()) {
            handleDraw();
            return;
        }

        gameLogic.switchPlayer();
        updateStatusForNextPlayer();
    }

    private void animateCellTap(ImageView img) {
        img.setTranslationY(-1000f);
        img.animate().translationYBy(1000f).setDuration(300);
    }

    private void updateCellImage(ImageView img) {
        if (gameLogic.getActivePlayer() == GameLogic.PLAYER_X) {
            img.setImageResource(R.drawable.x);
        } else {
            img.setImageResource(R.drawable.o);
        }
    }

    private void updateStatusForNextPlayer() {
        if (gameLogic.getActivePlayer() == GameLogic.PLAYER_X) {
            status.setImageResource(R.drawable.xplay);
        } else {
            status.setImageResource(R.drawable.oplay);
        }
    }

    private void handleWin(int winIndex) {
        int winner = gameLogic.getWinningPlayer(winIndex);
        if (winner == GameLogic.PLAYER_X) {
            status.setImageResource(R.drawable.xwin);
        } else {
            status.setImageResource(R.drawable.owin);
        }
        showWinLine(winIndex);
        resetButton.setVisibility(View.VISIBLE);
    }

    private void showWinLine(int winIndex) {
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/mark" + winIndex, null, c.getPackageName());
        ((ImageView) findViewById(R.id.winLine)).setImageResource(id);
    }

    private void handleDraw() {
        gameLogic.setGameInactive();
        status.setImageResource(R.drawable.nowin);
        resetButton.setVisibility(View.VISIBLE);
    }

    public void gameReset(View view) {
        gameLogic.resetGame();
        clearAllCells();
        status.setImageResource(R.drawable.xplay);
        ((ImageView) findViewById(R.id.winLine)).setImageResource(R.drawable.empty);
        resetButton.setVisibility(View.INVISIBLE);
    }

    private void clearAllCells() {
        for (int cellId : cellIds) {
            ((ImageView) findViewById(cellId)).setImageResource(R.drawable.empty);
        }
    }
}
