package main.java;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote {

    void createGame(String gameName, RemotePlayerListener myListener) throws RemoteException;

    void joinGame(String gameName, RemotePlayerListener myListener) throws RemoteException;

}

/* interfaccia che tutti i client possono cercare per dire che vogliono giocare */