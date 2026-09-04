package main.java;

import java.rmi.RemoteException;

public class GameSessionImpl implements GameSession{

    private String gameName;

    public GameSessionImpl(String gameName) throws RemoteException {
        this.gameName = gameName;
    }

    @Override
    public void makeMove(int pos) throws RemoteException {

    }

    public String getGameName() {
        return this.gameName;
    }
}
