package main.java;

import main.java.client.RemotePlayerListener;

import java.rmi.RemoteException;
import java.util.Objects;

public class GameSessionImpl implements GameSession{

    private static final int[][] winningCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //riga 1, 2 e 3
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //colonna 1, 2 e 3
            {0, 4, 8}, {2, 4, 6}  //diagonali
    };

    private final String gameName;
    private final RemotePlayerListener player1;
    private RemotePlayerListener player2;
    private String[] board;
    private boolean isPlayer1Turn;
    private int moveCount;

    public GameSessionImpl(String gameName, RemotePlayerListener player1) throws RemoteException {
        super();
        this.gameName = gameName;
        this.player1 = player1;
        this.board = new String[9];
        for(int i = 0; i < 9; i++) {
            board[i] = "";
        }
        this.isPlayer1Turn = true;  //poi diventerà random se fede lo farà TODO
        this.moveCount = 0;
    }

    public void addSecondPlayer(RemotePlayerListener player2) throws RemoteException {
        this.player2 = player2;

        player1.opponentJoined(player2.getName()); //si sveglia l'avversario 1

        player1.onGameUpdate(this.board, player1.getName());
        player2.onGameUpdate(this.board, player1.getName());

    }

    @Override
    public void makeMove(int pos) throws RemoteException {

        final String move = isPlayer1Turn ? "X" : "O";

        board[pos] = move;

        moveCount++;

        if (checkWin(move)) {
            String winner = isPlayer1Turn ? this.player1.getName() : this.player2.getName();
            System.out.println("The winner is " + winner);
            player1.onGameOver(board, winner);
            player2.onGameOver(board, winner);
        } else if (moveCount == 9) {
            System.out.println("PARI");
            player1.onGameOver(board, "Draw");
            player2.onGameOver(board, "Draw");
        } else {
            isPlayer1Turn = !isPlayer1Turn;
            String activeName = isPlayer1Turn ? player1.getName() : player2.getName();
            player1.onGameUpdate(board, activeName);
            player2.onGameUpdate(board, activeName);
        }

    }

    private boolean checkWin(String symbol) {

        for (int[] combination : winningCombinations) {
            //controlliamo se le 3 celle della combinazione contengono tutte lo stesso simbolo
            //simbolo è X oppure O
            if (Objects.equals(board[combination[0]], symbol) &&
                    Objects.equals(board[combination[1]], symbol) &&
                    Objects.equals(board[combination[2]], symbol)) {
                return true;
            }
        }

        return false;
    }

    public String getGameName() {
        return this.gameName;
    }

    @Override
    public String getPlayer1Name() throws RemoteException {
        return this.player1.getName();
    }

    @Override
    public String getPlayer2Name() throws RemoteException {
        if (this.player2 == null) {
            return "Waiting...";
        }
        return this.player2.getName();
    }

    @Override
    public String[] getBoard() throws RemoteException {
        return this.board;
    }

    @Override
    public boolean isPlayer1Turn() throws RemoteException {
        return this.isPlayer1Turn;
    }

}
