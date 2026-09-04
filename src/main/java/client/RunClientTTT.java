package main.java.client;

import main.java.GameManager;

import javax.swing.*;
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
            var manager = (GameManager) registry.lookup("ManagerObj");

            var l = new RemotePlayerListenerImpl(userName);
            var lproxy = (RemotePlayerListener) UnicastRemoteObject.exportObject(l, 0);

            //scegliere se creare o joinare
            String mode;
            do {
                System.out.println("Enter c to create a new game, j to join an existing one");
                mode = myObj.nextLine();
            } while (!Objects.equals(mode, "c") && !Objects.equals(mode, "j"));

            String roomName;

            //se la modalità è creare:
            if (mode.equals("c")) {
                System.out.println("Choose room's name: ");
                roomName = myObj.nextLine();
                while(!manager.createGame(roomName, lproxy)) {
                    System.out.println("Room already exists. Choose another name: ");
                    roomName =  myObj.nextLine();
                };
            } else { //se la modalità è joinare:
                System.out.println("Choose room's name to join: ");
                roomName = myObj.nextLine();
                while(!manager.joinGame(roomName, lproxy)) {
                    System.out.println("Room does not exist. Choose another name to join: ");
                    roomName =  myObj.nextLine();
                };
            }

            //creazione GUI
            String finalRoomName = roomName;
            SwingUtilities.invokeLater(() -> {
                // Immaginiamo di essere il client di "Marco"
                ClientGUI client = new ClientGUI(finalRoomName, "Luca", "Marco", "Marco");
                client.setVisible(true);

                // Simulo un ritardo di 1 secondo, poi ricevo lo stato iniziale dal server
                Timer timer = new Timer(1000, e -> {
                    String[] fakeBoard = {"", "", "", "", "", "", "", "", "X"};
                    // Passo "Marco" come giocatore attivo, quindi i bottoni si abiliteranno
                    client.updateGameState(fakeBoard, "Marco", null);
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
