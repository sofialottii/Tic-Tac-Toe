package main.java.client;

import java.rmi.RemoteException;

public class RemotePlayerListenerImpl implements RemotePlayerListener {

    private final String playerName;

    public RemotePlayerListenerImpl(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void opponentJoined() throws RemoteException {
        System.out.println("Avversario unito. La partita sta per cominciare.");
    }

    @Override
    public void onGameUpdate(String[] board, boolean isYourTurn) throws RemoteException {


    }

    @Override
    public String onGameOver() throws RemoteException {
        System.out.println("PARTITA FINITA");
        return "";
    }

    public String getPlayerName() {
        return this.playerName;
    }
}
