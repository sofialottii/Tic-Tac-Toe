package main.java;

import main.java.client.RemotePlayerListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class GameManagerImpl implements GameManager{

    private final Map<String, GameSession> pendingGames; //game creati da gioc1 che attendono un gioc2

    public GameManagerImpl(){
        pendingGames = new HashMap<>();
    }

    @Override
    public GameSession createGame(String gameName, RemotePlayerListener user1) throws RemoteException {

        if (pendingGames.containsKey(gameName)){
            System.err.println("Nome partita già esistente");
            return null;
        } else {

            GameSessionImpl game = new GameSessionImpl(gameName, user1);
            var gameStub = (GameSession) UnicastRemoteObject.exportObject(game, 0);

            pendingGames.put(gameName, gameStub);
            System.out.println("[Server] Created game: " + gameName);
            return gameStub;
        }

    }

    @Override
    public GameSession joinGame(String gameName, RemotePlayerListener user2) throws RemoteException {

        if (!pendingGames.containsKey(gameName)) {
            System.err.println("Partita non trovata o già iniziata");
            return null;
        } else {
            GameSession gameStub = pendingGames.get(gameName);

            gameStub.addSecondPlayer(user2);

            pendingGames.remove(gameName); //quando la partita è al completo viene rimossa dalla mappa
            return gameStub;
        }
    }
}
