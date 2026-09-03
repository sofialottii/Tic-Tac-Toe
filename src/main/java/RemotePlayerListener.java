package main.java;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePlayerListener extends Remote {

    void opponentJoined() throws RemoteException;

}

