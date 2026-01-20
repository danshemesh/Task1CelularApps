package com.example.tictactoe;

public class GameLogic {
    
    public static final int PLAYER_X = 0;
    public static final int PLAYER_O = 1;
    public static final int EMPTY = 2;

    private static final int[][] WIN_POSITIONS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    private int[] gameState;
    private int activePlayer;
    private boolean gameActive;
    private int moveCount;

    public GameLogic() {
        resetGame();
    }

    public void resetGame() {
        gameState = new int[]{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
        activePlayer = PLAYER_X;
        gameActive = true;
        moveCount = 0;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public boolean isCellEmpty(int position) {
        return gameState[position] == EMPTY;
    }

    public void makeMove(int position) {
        if (!gameActive || !isCellEmpty(position)) {
            return;
        }
        gameState[position] = activePlayer;
        moveCount++;
    }

    public void switchPlayer() {
        activePlayer = (activePlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    public int checkWinner() {
        for (int i = 0; i < WIN_POSITIONS.length; i++) {
            int[] winPosition = WIN_POSITIONS[i];
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != EMPTY) {
                gameActive = false;
                return i;
            }
        }
        return -1;
    }

    public int getWinningPlayer(int winIndex) {
        if (winIndex < 0) return -1;
        return gameState[WIN_POSITIONS[winIndex][0]];
    }

    public boolean isDraw() {
        return moveCount == 9 && checkWinner() == -1;
    }

    public void setGameInactive() {
        gameActive = false;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
