package main.java.client;

import main.java.GameManager;
import main.java.GameSession;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;
import java.util.Scanner;

public class RunClientTTT {

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];

        //inseriamo nome da terminale:
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username: ");
        String userName = myObj.nextLine();

        try {

            Registry registry = LocateRegistry.getRegistry(host);
            var manager = (GameManager) registry.lookup("GameManager");

            var l = new RemotePlayerListenerImpl(userName);
            var lproxy = (RemotePlayerListener) UnicastRemoteObject.exportObject(l, 0);

            //scegliere se creare o joinare
            String mode;
            do {
                System.out.println("Enter c to create a new game, j to join an existing one");
                mode = myObj.nextLine();
            } while (!Objects.equals(mode, "c") && !Objects.equals(mode, "j"));

            String roomName; //nome del match che verrà creato, scelto dall'utente
            GameSession game; //creazione del match effettivo

            //se la modalità è creare:
            if (mode.equals("c")) {
                System.out.println("Choose room's name: ");
                roomName = myObj.nextLine();
                game = manager.createGame(roomName, lproxy);
                while(game == null) {
                    System.out.println("Room already exists. Choose another name: ");
                    roomName =  myObj.nextLine();
                    game = manager.createGame(roomName, lproxy);
                }
            } else { //se la modalità è joinare:
                System.out.println("Choose room's name to join: ");
                roomName = myObj.nextLine();
                game = manager.joinGame(roomName, lproxy);
                while(game == null) {
                    System.out.println("Room does not exist. Choose another name to join: ");
                    roomName =  myObj.nextLine();
                    game = manager.joinGame(roomName, lproxy);
                }
            }

            //creazione della GUI
            GameSession finalGame = game;

            SwingUtilities.invokeLater(() -> {

                ClientGUI client = null;
                try {
                    client = new ClientGUI(finalGame, finalGame.getGameName(),
                            finalGame.getPlayer1Name(), finalGame.getPlayer2Name(), userName);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                l.setGUI(client);
                client.setVisible(true);
                //simulo un ritardo di 1 secondo, poi ricevo lo stato iniziale dal server
                ClientGUI finalClient = client;


                Timer timer = new Timer(1000, e -> {

                    try {
                        finalClient.updateGameState(finalGame.getBoard(),
                                finalGame.isPlayer1Turn() ? finalGame.getPlayer1Name() : finalGame.getPlayer2Name(),
                                null);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            });


        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
