package main.java;

import main.java.client.RemotePlayerListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Implementazione lato server della singola partita. Gestisce stato scacchiera,
 * controllo turni, logica di vittoria/pareggio e manda messaggi ai client ad ogni
 * cambiamento di stato
 */
public interface GameSession extends Remote {

    /**
     * Registra il secondo giocatore nella partita avviando il game. Notifica i client
     *
     * @param user2 Riferimento del secondo giocatore
     */
    void addSecondPlayer(RemotePlayerListener user2) throws RemoteException;

    /**
     * Elabora la mossa di un giocatore su una cella. Controlla se la partita è finita, gestisce
     * logica di vittoria/pareggio e notifica i risultati o il passaggio del turno ai client
     *
     * @param pos Indice da 0 a 9 della cella su cui si vuole fare la mossa
     */
    void makeMove(int pos) throws RemoteException;

    String getGameName() throws RemoteException;

    String getPlayer1Name() throws RemoteException;

    String getPlayer2Name() throws RemoteException;

}
