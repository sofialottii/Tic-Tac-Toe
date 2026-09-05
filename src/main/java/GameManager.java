package main.java;

import main.java.client.RemotePlayerListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Implementazione lato server del gestore delle partite. Mantiene la memoria delle
 * sessioni di gioco che cercano ancora un player 2.
 * I client comunicano con questo oggetto remoto per creare o unirsi alle stanze.
 * Tutti i metodi sono sincronizzati per garantire la thread-safety.
 */
public interface GameManager extends Remote {

    /**
     * Crea un nuova stanza di gioco. L'accesso è sincronizzato per prevenire la creazione
     * concorrente di stanze con lo stesso nome.
     *
     * @param gameName Il nome della stanza da creare
     * @param user1 Il riferimento (listener) del creatore della partita
     * @return lo stub RIM della sessione di gioco appena creata (o null se il nome è già usato)
     */
    GameSession createGame(String gameName, RemotePlayerListener user1) throws RemoteException;

    /**
     * Permette a un secondo giocatore di unirsi a una stanza già creata. L'accesso è sincronizzato
     * per evitare accessi concorrenti alla stessa stanza.
     *
     * @param gameName Il nome della stanza a cui unirsi
     * @param user2 Il riferimento (listener) del giocatore che si sta unendo
     * @return lo stub RIM della sessione di gioco appena creata (o null se il nome è già usato)
     */
    GameSession joinGame(String gameName, RemotePlayerListener user2) throws RemoteException;

}