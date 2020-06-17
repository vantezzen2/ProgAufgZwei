package gui;

import schiffeversenken.Board.BoardStatus;
import schiffeversenken.Engine;
import schiffeversenken.Status;
import schiffeversenken.StatusException;
import schiffeversenken.protocol.SVSender;
import schiffeversenken.protocolBinding.StreamBindingReceiver;
import schiffeversenken.protocolBinding.StreamBindingSender;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class SVGUI {
    // Commands
    public static final String CONNECT = "connect";
    public static final String SERVER = "server";
    public static final String BOOT = "boot";
    public static final String START = "start";
    public static final String BOMBE = "bombe";
    public static final String KAPITULATION = "kapitulation";
    public static final String EXIT = "exit";
    public static final String HELP = "help";
    private final PrintStream os;
    private final BufferedReader userInput;
    private DataConnection connection;
    private SVSender sender;
    private Engine engine;
    private StreamBindingReceiver receiver;
    private GUIStatus status = GUIStatus.NICHT_VERBUNDEN;


    public SVGUI() {
        PrintStream os = System.out;

        os.println("###########################################");
        os.println("#          Schiffe versenken 1.0          #");
        os.println("#       Bennett Hollstein, s0574045       #");
        os.println("###########################################");
        os.println("Schreibe 'help' für Hilfe.");

        this.os = System.out;
        this.userInput = new BufferedReader(new InputStreamReader(System.in));

        this.run();
    }

    // Based on https://github.com/SharedKnowledge/ASAPJava/blob/master/src/net/sharksystem/cmdline/CmdLineUI.java#L195
    private void run() {
        boolean again = true;
        while (again) {
            try {
                // Gebe die aktuellen Boards aus
                if (status != GUIStatus.NICHT_VERBUNDEN) {
                    printTheBoards();
                }

                // read user input
                os.print("> ");
                String cmdLineString = userInput.readLine();

                // finish that loop if less than nothing came in
                if (cmdLineString == null) break;

                // trim whitespaces on both sides
                cmdLineString = cmdLineString.trim();

                // extract command
                int spaceIndex = cmdLineString.indexOf(' ');
                spaceIndex = spaceIndex != -1 ? spaceIndex : cmdLineString.length();

                // got command string
                String commandString = cmdLineString.substring(0, spaceIndex);

                // extract parameters string - can be empty
                String parameterString = cmdLineString.substring(spaceIndex);
                parameterString = parameterString.trim();
                String[] parameters = parameterString.split(" ");

                // start command loop
                switch (commandString) {
                    case CONNECT:
                        connect(parameters);
                        break;

                    case SERVER:
                        server(parameters);
                        break;

                    case BOOT:
                        setzteBoot(parameters);
                        break;

                    case START:
                        start();
                        break;

                    case BOMBE:
                        bombe(parameters);
                        break;

                    case KAPITULATION:
                        kapitulation();
                        break;

                    case HELP:
                        this.printUsage();
                        break;

                    case "q": // convenience
                    case EXIT:
                        again = false;
                        break; // end loop

                    default:
                        this.os.println("unknown command:" +
                                cmdLineString);
                        this.printUsage();
                        break;
                }
            } catch (IOException ex) {
                this.os.println("cannot read from input stream");
                System.exit(0);
            } catch (StatusException ex) {
                this.os.println("Statusfehler: " + ex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Require a specific number of parameters or fail otherwise
     *
     * @param num        Number of parameters
     * @param parameters Array of parameters
     * @return True if there are enough parameters
     */
    private boolean requireNumParameters(int num, String[] parameters) {
        if (parameters.length != num) {
            os.println("Dieses Kommando benötigt " + num + " Argumente (" + parameters.length + " übergeben)");
            os.println("Schreibe 'help', um mehr über die Nutzung der Kommandos zu erfahren");
            return false;
        }
        return true;
    }

    // Based on https://github.com/SharedKnowledge/ASAPJava/blob/master/src/net/sharksystem/cmdline/CmdLineUI.java#L60
    private void printUsage() {
        StringBuilder b = new StringBuilder();

        b.append("\n");
        b.append("Verfügbare Kommandos:");
        b.append("\n");
        if (status == GUIStatus.NICHT_VERBUNDEN) {
            b.append(CONNECT);
            b.append(" [IP] [Port]\n\tVerbinde mit externem Server");
            b.append("\n");
            b.append(SERVER);
            b.append(" [Port]\n\tErzeuge einen Server");
            b.append("\n");
        }
        if (status == GUIStatus.SETZE_BOOTE) {
            b.append(BOOT);
            b.append(" [Start x] [Start y] [Länge] [h (horizontal) oder v (vertikal)]\n\tSetze ein Boot");
            b.append("\n");
            b.append(START);
            b.append("\n\tBestätige Schiffe und starte das Spiel");
            b.append("\n");
        }
        if (status == GUIStatus.IM_SPIEL) {
            b.append(BOMBE);
            b.append(" [x] [y]\n\tWerfe eine Bombe");
            b.append("\n");
            b.append(KAPITULATION);
            b.append("\n\tKapituliere");
            b.append("\n");
        }
        b.append(EXIT);
        b.append("\n\tExit");

        this.os.println(b.toString());
    }

    // ---- Helfer ----
    private void printTheBoards() {
        os.println(engine.getOtherBoard());
        os.println("--------------------");
        os.println(engine.getOwnBoard());
        os.println("Legende: Wasser: ░, Schiff: █, Verfehlt: -, Versenkt: *, Getroffen: ■");
    }

    // ---- Verbindungsaufbau ----
    private void connect(String[] parameters) throws IOException {
        if (!requireNumParameters(2, parameters)) return;
        if (status != GUIStatus.NICHT_VERBUNDEN) {
            os.println("Statusfehler: Bereits mit Partner verbunden");
            return;
        }

        // Connect to Server
        String host = parameters[0];
        int port = Integer.parseInt(parameters[1]);

        os.println("Verbindung zu " + host + ":" + port + " wird hergestellt...");

        try {
            connection = new DataConnector(host, port);
        } catch (IOException e) {
            os.println("Fehler: Kann nicht mit Server verbinden");
            return;
        }

        startTheEngine();
    }

    private void server(String[] parameters) throws IOException {
        if (!requireNumParameters(1, parameters)) return;
        if (status != GUIStatus.NICHT_VERBUNDEN) {
            os.println("Statusfehler: Bereits mit Partner verbunden");
            return;
        }

        // Set up Server
        int port = Integer.parseInt(parameters[0]);

        os.println("Warte auf Verbindung auf Port " + port + "...");
        try {
            connection = new DataConnector(port);
        } catch (IOException e) {
            os.println("Fehler: Kann Server nicht erstellen");
            return;
        }

        startTheEngine();
    }

    private void startTheEngine() throws IOException {
        sender = new StreamBindingSender(connection.getDataOutputStream());
        engine = new Engine(sender);
        receiver = new StreamBindingReceiver(
                connection.getDataInputStream(),
                engine
        );
        receiver.start();

        status = GUIStatus.SETZE_BOOTE;

        os.println("Verbunden. Bitte setze deine Boote");
    }

    // ---- Boote setzen ----
    private void setzteBoot(String[] parameters) {
        if (!requireNumParameters(4, parameters)) return;
        if (status != GUIStatus.SETZE_BOOTE) {
            os.println("Statusfehler: Es können zur Zeit keine Boote gesetzt werden");
            return;
        }

        // TODO: Check if boat is allowed
        // TODO: Check if boat goes over edge

        int x = Integer.parseInt(parameters[0]);
        int y = Integer.parseInt(parameters[1]);
        int length = Integer.parseInt(parameters[2]);
        boolean vertikal = !parameters[3].trim().equals("v");

        // Setzte Boot
        for (int i = 0; i < length; i++) {
            engine.getOwnBoard().set(x, y, BoardStatus.SCHIFF);

            if (vertikal) {
                y++;
            } else {
                x++;
            }
        }
    }

    // ---- Spielablauf ----
    private void start() throws IOException, StatusException, InterruptedException {
        if (status != GUIStatus.SETZE_BOOTE) {
            os.println("Statusfehler: Das Spiel ist schon gestartet oder noch nicht verbunden");
            return;
        }

        status = GUIStatus.IM_SPIEL;
        engine.doDice();

        os.println("Warte auf Spielbeginn...");
        while (engine.getStatus() == Status.WURFEL_GESENDET) {
            Thread.sleep(1000);
        }

        if (engine.isActive()) {
            os.println("Du bist am Spielzug");
        } else {
            warteAufGegner();
        }
    }

    private void warteAufGegner() throws InterruptedException {
        os.println("Warte auf Spielzug des Gegners...");
        while (!engine.isActive() && engine.getStatus() != Status.BEENDEN) {
            Thread.sleep(1000);
        }

        if (engine.getStatus() == Status.BEENDEN) {
            status = GUIStatus.ENDE;
        }
    }

    private void bombe(String[] parameters) throws IOException, StatusException, InterruptedException {
        if (!requireNumParameters(2, parameters)) return;
        if (status != GUIStatus.IM_SPIEL) {
            os.println("Statusfehler: Du kannst zur Zeit keine Bomben werfen");
            return;
        }

        int x = Integer.parseInt(parameters[0]);
        int y = Integer.parseInt(parameters[1]);

        engine.bombe_werfen(x, y);

        if (engine.getStatus() == Status.BEENDEN) {
            status = GUIStatus.ENDE;
        } else {
            warteAufGegner();
        }
    }

    private void kapitulation() throws StatusException, IOException {
        if (status != GUIStatus.IM_SPIEL) {
            os.println("Statusfehler: Du kannst zur Zeit nicht kapitulieren");
            return;
        }

        System.out.println("Kapituliere");

        engine.sende_kapitulation();
        status = GUIStatus.ENDE;
    }
}
