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
    public synchronized GameSession createGame(String gameName, RemotePlayerListener user1) throws RemoteException {

        if (pendingGames.containsKey(gameName)){
            System.err.println("Game name already existing");
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
    public synchronized GameSession joinGame(String gameName, RemotePlayerListener user2) throws RemoteException {

        if (!pendingGames.containsKey(gameName)) {
            System.err.println("Game not found or already started");
            return null;
        } else {
            GameSession gameStub = pendingGames.get(gameName);

            gameStub.addSecondPlayer(user2);

            pendingGames.remove(gameName); //quando la partita è al completo viene rimossa dalla mappa
            return gameStub;
        }
    }
}
