package main.java.client;

import javax.swing.*;
import java.rmi.RemoteException;

public class RemotePlayerListenerImpl implements RemotePlayerListener {

    private final String playerName;
    private ClientGUI gui;

    public RemotePlayerListenerImpl(String playerName) {
        this.playerName = playerName;
    }

    public void setGUI(ClientGUI gui) {
        this.gui = gui;
    }

    @Override
    public void opponentJoined(String player2Name) throws RemoteException {
        if (gui != null) {
            SwingUtilities.invokeLater(() -> gui.updatePlayer2Name(player2Name));
        }
    }

    @Override
    public void onGameUpdate(String[] board, String activePlayerName) throws RemoteException {
        if (gui != null) {
            SwingUtilities.invokeLater(() -> gui.updateGameState(board, activePlayerName, null));
        }
    }


    @Override
    public void onGameOver(String[] board, String winnerName) throws RemoteException {
        System.out.println("GAME FINISHED");
        if (gui != null) {
            SwingUtilities.invokeLater(() -> gui.updateGameState(
                    board, "", winnerName));
        }
    }

    @Override
    public String getName() throws RemoteException {
        return this.playerName;
    }

}
