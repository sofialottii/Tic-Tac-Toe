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

            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("Manager", gameStub );

            System.out.println("[Server] Server Started");

        } catch (Exception e) {
            System.err.println("exception:" + e.toString());
            e.printStackTrace();
        }
    }
}
