package main.java.server;

import main.java.GameManager;
import main.java.GameManagerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RunServerTTT {
    public static void main(String[] args) {

        try {
            GameManager manager = new GameManagerImpl();

            var gameStub = (GameManager) UnicastRemoteObject.exportObject(manager, 0);

            //try creato in modo che se la porta è già occupata, recupera il registro esistente
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (java.rmi.server.ExportException e) {
                registry = LocateRegistry.getRegistry(1099);
            }


            registry.rebind("GameManager", gameStub );

            System.out.println("[Server] Server Started");

        } catch (Exception e) {
            System.err.println("exception:" + e.toString());
            e.printStackTrace();
        }
    }
}
