package main.java;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameManagerImpl implements GameManager{

    private List<GameSessionImpl> gameInAttesa;

    public GameManagerImpl(){
        gameInAttesa = new ArrayList<>();
    }

    @Override
    public void createGame(String gameName, RemotePlayerListener myListener) throws RemoteException {

        /* controllare se esiste già una partita con il nome, quindi bisogna creare una lista di game già esistenti */

        GameSession game = new GameSessionImpl(gameName);

    }

    @Override
    public void joinGame(String gameName, RemotePlayerListener myListener) throws RemoteException {

    }
}
