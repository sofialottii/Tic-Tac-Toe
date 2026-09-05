package main.java.client;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Oggetto esportato via RMI al server, che lo usa per invocare passivamente aggiornamenti
 * sul client. Funge da tramite tra la GUI e il server, mandando aggiornamenti tramite
 * il thread di Swing
 */
public interface RemotePlayerListener extends Remote {

    /**
     * Quando il secondo giocatore si collega al server, il server chiama questo metodo sul primo
     * giocatore. In questo modo, il client del primo giocatore può aggiornare visivamente il nome
     * dell'avversario.
     *
     * @param player2Name Nome del secondo giocatore
     */
    void opponentJoined(String player2Name) throws RemoteException;

    /**
     * Metodo chiamato dal server ogni volta che un giocatore fa la propria mossa. Chiama questo metodo su
     * entrambi i giocatori per permettere alla loro grafica di aggiornarsi ad ogni cambio turno.
     *
     * @param board griglia formata da una lista di 9 stringhe
     * @param activePlayerName nome del giocatore di cui attualmente il turno
     */
    void onGameUpdate(String[] board, String activePlayerName) throws RemoteException;

    /**
     * Ricevuto quando il server capisce che il gioco è finito (o vittoria, o parità - griglia piena -).
     *
     * @param board griglia formata da una lista di 9 stringhe
     * @param winnerName nome del vincitore (mandata alla GUI)
     */
    void onGameOver(String[] board, String winnerName) throws RemoteException;

    String getName() throws RemoteException;

}
