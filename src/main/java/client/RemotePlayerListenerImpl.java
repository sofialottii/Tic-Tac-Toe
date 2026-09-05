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
    public void opponentJoined() throws RemoteException {
        System.out.println("Opponent found. The match is about to start.");
    }

    @Override
    public void onGameUpdate(String[] board, boolean isYourTurn) throws RemoteException {
        if (gui != null) {
            String activePlayerName = isYourTurn ? playerName : "Avversario";

            //passiamo l'aggiornamento al thread della GUI
            SwingUtilities.invokeLater(() -> gui.updateGameState(board, activePlayerName, null));
        }
    }

    @Override
    public String onGameOver() throws RemoteException {
        System.out.println("GAME FINISHED");
        if (gui != null) {
            SwingUtilities.invokeLater(() -> gui.updateGameState(new String[9],
                    "", "Fine Partita")); //da modificare TODO
        }
        return "";
    }

    @Override
    public String getName() throws RemoteException {
        return this.playerName;
    }

}
