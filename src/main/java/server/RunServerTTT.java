package main.java.server;

import main.java.GameManager;
import main.java.GameManagerImpl;
import main.java.RemotePlayerListener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RunServerTTT {

    public static void main(String[] args) {

        try {

            GameManager manager = new GameManagerImpl();

            var gameStub = (GameManager) UnicastRemoteObject.exportObject(manager, 0);

            //registro avviato su porta 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("Manager", gameStub );

            System.out.println("[Server] Server Started");

        } catch (Exception e) {
            System.err.println("exception:" + e.toString());
            e.printStackTrace();
        }
    }
}
