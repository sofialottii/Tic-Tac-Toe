package main.java.client;

import main.java.GameSession;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.Objects;

/**
 * Interfaccia grafica (GUI) del client. Gestisce la renderizzazione della griglia,
 * le label di stato dei giocatori
 */
public class ClientGUI extends JFrame {

    private final JLabel p1NameLabel;
    private final JLabel p1StatusLabel; //status: can be waiting or is your turn
    private final JLabel p2NameLabel;
    private final JLabel p2StatusLabel;
    private final JButton[] cells;
    private final JLabel bottomStatusLabel; //who's turn or who is the winner

    //nomi player local e altro player
    private final String localPlayerName;
    private final String remotePlayerName;

    public ClientGUI(GameSession session, String gameName, String p1Name, String p2Name, String localPlayerName)
            throws RemoteException {
        //riferimento alla partita
        this.localPlayerName = localPlayerName;
        this.remotePlayerName = Objects.equals(localPlayerName, p1Name) ? p2Name : p1Name;

        setTitle("Distributed TTT - Client di " + localPlayerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        //pannello superiore -> titolo partita
        JLabel titleLabel = new JLabel("Nome partita: " + gameName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 26));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(titleLabel, BorderLayout.NORTH);

        //pannello centrale: -> giocatori e griglia
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.fill = GridBagConstraints.VERTICAL;

        //giocatore 1 -> sinistra
        JPanel p1Panel = new JPanel(new GridLayout(3, 1));
        p1Panel.setBackground(Color.WHITE);
        JLabel p1Label = new JLabel("Player 1:");
        p1NameLabel = new JLabel(p1Name);
        p1StatusLabel = new JLabel("Attendi", SwingConstants.LEFT); // Testo default

        p1Label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        p1NameLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        p1StatusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));

        p1Label.setForeground(Color.RED);
        p1NameLabel.setForeground(Color.RED);
        p1StatusLabel.setForeground(Color.GRAY);

        p1Panel.add(p1Label);
        p1Panel.add(p1NameLabel);
        p1Panel.add(p1StatusLabel);
        gbc.gridx = 0;
        centerPanel.add(p1Panel, gbc);

        //griglia di gioco centrale
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(Color.WHITE);
        boardPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
        boardPanel.setPreferredSize(new Dimension(200, 200));

        cells = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int cellIndex = i;
            cells[i] = new JButton("");
            cells[i].setEnabled(false); //quando si aspetta il 2 gioc, non si può cliccare nelle celle
            cells[i].setFont(new Font("SansSerif", Font.PLAIN, 50));
            cells[i].setFocusPainted(false); //per rimuovere il rettangolino di focus
            cells[i].setContentAreaFilled(false); //sfondo trasparente
            cells[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            int topBorder = (i > 2) ? 2 : 0;
            int leftBorder = (i % 3 != 0) ? 2 : 0;
            cells[i].setBorder(BorderFactory.createMatteBorder(topBorder, leftBorder, 0, 0, Color.BLACK));

            //quando viene cliccata una cella:
            cells[i].addActionListener(e -> {
                //se la cella non è vuota, ignora il click
                if (!cells[cellIndex].getText().trim().isEmpty()) return;

                try {
                    session.makeMove(cellIndex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });

            boardPanel.add(cells[i]);
        }

        gbc.gridx = 1;
        centerPanel.add(boardPanel, gbc);

        //giocatore 2 -> destra
        JPanel p2Panel = new JPanel(new GridLayout(3, 1));
        p2Panel.setBackground(Color.WHITE);
        JLabel p2Label = new JLabel("Player 2:");
        p2NameLabel = new JLabel(session.getPlayer2Name());
        p2StatusLabel = new JLabel("Attendi", SwingConstants.LEFT); // Testo default

        p2Label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        p2NameLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        p2StatusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));

        p2Label.setForeground(new Color(50, 50, 200)); // Blu
        p2NameLabel.setForeground(new Color(50, 50, 200));
        p2StatusLabel.setForeground(Color.GRAY);

        p2Panel.add(p2Label);
        p2Panel.add(p2NameLabel);
        p2Panel.add(p2StatusLabel);
        gbc.gridx = 2;
        centerPanel.add(p2Panel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        //pannello inferiore -> stato della partita
        bottomStatusLabel = new JLabel("In attesa del server...", SwingConstants.CENTER);
        bottomStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 26));
        bottomStatusLabel.setForeground(Color.BLACK);
        bottomStatusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(bottomStatusLabel, BorderLayout.SOUTH);

        pack();
        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    /**
     * Aggiorna dinamicamente il nome dell'avversario nell'interfaccia grafica.
     * Usato quando il creatore della stanza è in attesa e un player 2 si connette.
     *
     * @param newName Nome del giocatore appena connesso
     */
    public void updatePlayer2Name(String newName) {
        p2NameLabel.setText(newName);

        p2NameLabel.revalidate();
        p2NameLabel.repaint();
    }

    /**
     * Metodo chiamato dal client RMI quando riceve un aggiornamento dal server.
     * Aggiorna passivamente l'interfaccia.
     *
     * @param boardArray Array di 9 stringhe ("X", "O", o "")
     * @param activePlayerName Il nome del giocatore che deve fare la mossa
     * @param winnerName Nome del vincitore, "Draw" per pareggio, o null se in corso
     */
    public void updateGameState(String[] boardArray, String activePlayerName, String winnerName) {
        //aggiornamento griglia
        for (int i = 0; i < 9; i++) {
            cells[i].setText(boardArray[i]);
            if (boardArray[i].equals("X")) {
                cells[i].setForeground(Color.BLUE);
            } else if (boardArray[i].equals("O")) {
                cells[i].setForeground(Color.RED);
            }
        }

        //aggiornamento turno / stato di fine partita
        if (winnerName != null) {
            //con la fine partita viene disabilitata la griglia)
            for (JButton cell : cells) cell.setEnabled(false);

            p1StatusLabel.setText("");
            p2StatusLabel.setText("");

            if (winnerName.equals("Draw")) {
                bottomStatusLabel.setText("Pareggio!");
                bottomStatusLabel.setForeground(Color.BLACK);
            } else {
                bottomStatusLabel.setText("Vittoria " + winnerName + "!!");
                //blu se vince P2, rosso se vince P1
                bottomStatusLabel.setForeground(winnerName.equals(p2NameLabel.getText()) ? new Color(50, 50, 200) : Color.RED);
            }
        } else {
            //controlli per far vedere graficamente chi deve giocare
            bottomStatusLabel.setText("Turno di " + (Objects.equals(activePlayerName, "") ? remotePlayerName : activePlayerName));
            bottomStatusLabel.setForeground(Color.BLACK);

            if (activePlayerName.equals(p1NameLabel.getText())) {
                p1StatusLabel.setText("È il tuo turno");
                p1StatusLabel.setForeground(Color.RED);
                p2StatusLabel.setText("Attendi");
                p2StatusLabel.setForeground(Color.GRAY);
            } else {
                p2StatusLabel.setText("È il tuo turno");
                p2StatusLabel.setForeground(new Color(50, 50, 200));
                p1StatusLabel.setText("Attendi");
                p1StatusLabel.setForeground(Color.GRAY);
            }

            boolean isMyTurn = activePlayerName.equals(this.localPlayerName);
            for (JButton cell : cells) {
                //il bottone è abilitato solo se è il mio turno e la cella è vuota
                cell.setEnabled(isMyTurn && cell.getText().isEmpty());
            }
        }
    }
}