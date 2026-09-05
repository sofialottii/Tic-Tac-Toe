package main.java.server;

import main.java.GameManager;
import main.java.GameManagerImpl;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RunServerTTT {
    public static void main(String[] args) {
        try {
            // Se passi l'IP da terminale usa quello, altrimenti lo rileva in automatico
            String hostIp = (args.length > 0) ? args[0] : InetAddress.getLocalHost().getHostAddress();

            System.setProperty("java.rmi.server.hostname", hostIp);
            System.out.println("[Server] RMI Hostname configured on: " + hostIp);

            GameManager manager = new GameManagerImpl();
            var gameStub = (GameManager) UnicastRemoteObject.exportObject(manager, 0);

            //try creato in modo che se la porta è già occupata, recupera il registro esistente
            Registry registry;
            try {
                //prova a creare un nuovo registro RMI
                registry = LocateRegistry.createRegistry(1099);
            } catch (java.rmi.server.ExportException e) {
                registry = LocateRegistry.getRegistry(1099);
            }

            registry.rebind("GameManager", gameStub );
            System.out.println("[Server] Server Started and on listening");

        } catch (Exception e) {
            System.err.println("exception:" + e);
            e.printStackTrace();
        }
    }
}