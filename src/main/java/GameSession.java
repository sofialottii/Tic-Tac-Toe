package main.java;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSession extends Remote {

    //il client dovrà chiamare questo metodo sul server per fare la sua mossa
    void makeMove(int pos) throws RemoteException;

}

/* la griglia non è vista come una row;col, ma è una posizione che va da 0 a 8*/

//bisogna passare il parametro di riferimento del client
