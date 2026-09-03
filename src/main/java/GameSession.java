package main.java;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSession extends Remote {

    //il client dovrà chiamare questo metodo sul server per fare la sua mossa
    void makeMove(int row, int col) throws RemoteException;

}
