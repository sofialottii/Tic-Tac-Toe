package main.java;

import main.java.client.RemotePlayerListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote {

    GameSession createGame(String gameName, RemotePlayerListener myListener) throws RemoteException;

    GameSession joinGame(String gameName, RemotePlayerListener myListener) throws RemoteException;

}

/* interfaccia che tutti i client possono cercare per dire che vogliono giocare */