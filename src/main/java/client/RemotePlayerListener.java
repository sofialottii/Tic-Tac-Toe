package main.java.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePlayerListener extends Remote {

    void opponentJoined() throws RemoteException;

    void onGameUpdate(String[] board, boolean isYourTurn) throws RemoteException;

    String onGameOver() throws RemoteException;

    String getName() throws RemoteException;

}

/*
NOTE: funzioni che il client riceve

opponentJoined: tipo quando il primo giocatore crea la stanza, sarà in attesa di un avversario. Quando
il secondo giocatore si collega al server, il server chiamerà questo metodo sul primo giocatore. Il
client del primo lo riceve così si può aggiornare

onGameUpdate: ogni volta che un giocatore fa la sua mossa, il server aggiorna la board e poi chiama il metodo
(solo sull'altro giocatore? credo? o su entrambi? se è solo sull'altro giocatore si può togliere la bool
isYourTurn, altrimenti va tenuta. Da capire bene). Quando gioc1 fa mossa, comunica al server che fa la mossa,
il server fa i calcoli e cambia la board: entrambi devono vedere la modifica

onGameOver: viene ricevuto quando il server capisce che il gioco è finito (o è stato fatto tris, oppure la
griglia è piena). Dovrà returnare il winner, per questo è uno String

 */
