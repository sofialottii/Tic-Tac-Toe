package main.java;

import main.java.client.RemotePlayerListener;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class GameManagerImpl implements GameManager{

    private final Map<String, GameSessionImpl> pendingGames; //game creati da gioc1 che attendono un gioc2

    public GameManagerImpl(){
        pendingGames = new HashMap<>();
    }

    @Override
    public boolean createGame(String gameName, RemotePlayerListener user1) throws RemoteException {

        if (pendingGames.containsKey(gameName)){
            System.err.println("Nome partita già esistente");
            return false;
        } else {

            GameSessionImpl game = new GameSessionImpl(gameName, user1);

            pendingGames.put(gameName, game);
            System.out.println("[Server] Created game: " + gameName);
            return true;
        }

    }

    @Override
    public boolean joinGame(String gameName, RemotePlayerListener user2) throws RemoteException {

        if (!pendingGames.containsKey(gameName)) {
            System.err.println("Partita non trovata o già iniziata");
            return false;
        } else {
            GameSessionImpl game = pendingGames.get(gameName);

            game.addSecondPlayer(user2);

            pendingGames.remove(gameName); //quando la partita è al completo viene rimossa dalla mappa
            return true;
        }
    }
}
