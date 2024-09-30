import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random RANDOM = new Random(); // Objekt zur Generierung von Zufallszahlen.
    private static final int WIN_SUM_X = 264; // Summe der ASCII-Codes für das Zeichen 'X', verwendet zur Bestimmung eines Sieges.
    private static final int WIN_SUM_O = 237; // Summe der ASCII-Codes für das Zeichen 'O', verwendet zur Bestimmung eines Sieges.

    public static void main(String[] args) {
        int ifPlayAgain = 2; // Variable, um zu bestimmen, ob eine neue Runde gestartet werden soll.
        String[] playersNames = new String[2]; // Array zur Speicherung der Namen der beiden Spieler.
        int[] finalScore = {0, 0}; // Array zur Speicherung des Punktestands der Spieler.
        char[][] board = new char[3][3]; // Das Spielfeld, eine 3x3-Matrix.

        // Hauptspiels-Schleife.
        mainLoop: while (true) {
            int moveCounter = 0; // Zähler für die Spielzüge.
            String winner;

            // Initialisierung des Spielfeldes (alle Zellen werden mit '-' gefüllt).
            for (char[] chars : board) {
                Arrays.fill(chars, '-');
            }

            // Abfrage der Spielernamen und/oder Neustart des Spiels.
            playerNameInput(playersNames, finalScore, ifPlayAgain);
            boardOutput(board);

            while(true) {
                int[] userInput = userInput(board, playersNames, moveCounter); // Eingabe des Zuges durch den Spieler.
                moveCounter++; // Inkrement des Zugzählers.

                moveRecord(board, userInput, moveCounter); // Aufzeichnung des Zuges auf dem Spielfeld.
                boardOutput(board); // Ausgabe des aktualisierten Spielfeldes.
                winner = winOrDrawLogic(board, playersNames); // Überprüfung auf Sieg oder Unentschieden.

                if ((winner.equals(" "))) { // Wenn es einen Gewinner gibt oder ein Unentschieden vorliegt.
                    continue;
                }

                winnerMessage(winner, moveCounter); // Ausgabe der Gewinnermeldung.
                finalScore(playersNames, winner, finalScore); // Aktualisierung des Punktestands.
                ifPlayAgain = playAgain(); // Abfrage, ob das Spiel erneut gespielt werden soll.
                if (ifPlayAgain == 1 || ifPlayAgain == 2) {
                    continue mainLoop; // Neustart des Spiels.
                } else if (ifPlayAgain == 3) {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    break mainLoop; // Beenden des Spiels.
                }
            }
        }

    }

    public static boolean playWithBot() {
        Scanner scan = new Scanner(System.in);
        int withBot;

        // Abfrage, ob der Spieler gegen einen anderen Spieler oder gegen einen Bot spielen möchte.
        System.out.println("Wollen Sie zusammen oder mit dem Bot spielen?");
        System.out.print("Wenn zusammen, geben Sie 1 ein, wenn mit Bot - 2: ");
        while (true) {
            try {
                withBot = scan.nextInt();
                if (withBot >= 1 && withBot <= 2) {
                    System.out.println();
                    break;
                } else {
                    System.out.print("Bitte geben Sie die Zahl " + ConsoleColors.RED_BOLD_BRIGHT + "NUR" + ConsoleColors.RESET + " von 1 bis 2 ein: ");
                }
            } catch (Exception e) {
                System.out.println("Etwas geht nicht!");
                scan.next(); // Rücksetzen des Scanners nach einem Fehler.
            }
        }

        return withBot == 2; // Rückgabe true, wenn mit Bot gespielt wird, sonst false.
    }

    private static boolean easyOrHard() {
        Scanner scan = new Scanner(System.in);
        int easyOrHard;

        // Abfrage des Schwierigkeitsgrades, wenn gegen den Bot gespielt wird.
        System.out.print("Wählen Sie den Schwierigkeitsgrad: leicht - 1 oder schwer - 2: ");
        while (true) {
            try {
                easyOrHard = scan.nextInt();
                if (easyOrHard >= 1 && easyOrHard <= 2) {
                    System.out.println();
                    break;
                } else {
                    System.out.print("Bitte geben Sie die Zahl " + ConsoleColors.RED_BOLD_BRIGHT + "NUR" + ConsoleColors.RESET + " von 1 bis 2 ein: ");
                }
            } catch (Exception e) {
                System.out.println("Etwas geht nicht!");
                scan.next();
            }
        }

        return easyOrHard == 1; // Rückgabe true, wenn der Schwierigkeitsgrad leicht ist, sonst false.
    }

    public static void playerNameInput(String[] playersNames, int[] finalScore, int ifPlayAgain) {
        Scanner scan = new Scanner(System.in);
        boolean playWithBot;

        if (ifPlayAgain == 2) { // Wenn das Spiel zum ersten Mal oder mit neuen Spielern gestartet wird.
            playWithBot = playWithBot(); // Überprüfung, ob der Spieler gegen einen Bot spielen möchte.
            Arrays.fill(finalScore, 0); // Zurücksetzen des Punktestands.

            if (playWithBot) { // Logik, wenn gegen einen Bot gespielt wird.
                int isBotFirst = RANDOM.nextInt(2); // Zufällig bestimmen, ob der Bot beginnt.
                playersNames[isBotFirst] = easyOrHard() ? "EasyBot" : "HardBot"; // Abfrage des Schwierigkeitsgrades für den Bot. Zuordnung des Bots.

                if (isBotFirst == 0) {
                    try {
                        System.out.print("Bitte, Spieler " + (isBotFirst + 2) + ", schreibe deinen Namen: ");
                        playersNames[isBotFirst + 1] = scan.nextLine();
                        if (playersNames[isBotFirst + 1].equals("EasyBot") || playersNames[isBotFirst + 1].equals("HardBot")) {
                            System.out.print("Die Namen 'EasyBot' und 'HardBot' sind schon reserviert: ");
                            playersNames[isBotFirst + 1] = scan.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.print("Etwas geht nicht!");
                        scan.next();
                    }
                } else {
                    try {
                        System.out.print("Bitte, Spieler " + (isBotFirst) + ", schreibe deinen Namen: ");
                        playersNames[isBotFirst - 1] = scan.nextLine();
                        if (playersNames[isBotFirst - 1].equals("Bot")) {
                            System.out.print("Die Name 'Bot' ist schon reserviert: ");
                            playersNames[isBotFirst - 1] = scan.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.print("Etwas geht nicht!");
                        scan.next();
                    }
                }
            } else { // Wenn zwei menschliche Spieler spielen.
                for (int i = 0; i < 2; i++) {
                    try {
                        System.out.print("Bitte, Spieler " + (i + 1) + ", schreibe deinen Namen: ");
                        playersNames[i] = scan.nextLine();
                        if (playersNames[i].equals("Bot")) {
                            System.out.print("Die Name 'Bot' ist schon reserviert: ");
                            playersNames[i] = scan.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.print("Etwas geht nicht!");
                        scan.next();
                    }
                }
            }

        } else if (ifPlayAgain == 1) { // Wenn das Spiel erneut mit den gleichen Spielern gestartet wird.
            reorderNamesAndScore(playersNames, finalScore); // Neuanordnung der Spielernamen und der Punktestände.
        }

        System.out.println("\n" + playersNames[0] + " wird 'X' spielen");
        System.out.println(playersNames[1] + " wird 'O' spielen\n");
    }

    // Diese Methode gibt das Spielfeld in der Konsole aus.
    public static void boardOutput(char[][] board) {
        System.out.println(ConsoleColors.BLUE_BOLD + "\t1   2   3" + ConsoleColors.RESET);
        for (int i = 0; i < board.length; i++) {
            System.out.print(ConsoleColors.YELLOW_BOLD + (i + 1) + ConsoleColors.RESET + "\t");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'X') {
                    System.out.print(ConsoleColors.GREEN_BOLD + board[i][j] + ConsoleColors.RESET);
                } else if (board[i][j] == 'O') {
                    System.out.print(ConsoleColors.RED_BOLD + board[i][j] + ConsoleColors.RESET);
                } else {
                    System.out.print(board[i][j]);
                }
                if (j < board[i].length - 1) {
                    System.out.print(ConsoleColors.WHITE_BOLD + " | " + ConsoleColors.RESET);
                }
            }
            if (i < board.length - 1) {
                System.out.println(ConsoleColors.WHITE_BOLD + "\n\t--+---+--" + ConsoleColors.RESET);
            }
        }
        System.out.println("\n\n\n");
    }

    // Diese Methode nimmt die Eingabe des Spielers entgegen und gibt die Koordinaten des Zuges zurück.
    public static int[] userInput(char[][] board, String[] playersNames, int moveCounter) {
        Scanner scan = new Scanner(System.in);
        int[] fieldInput = new int[2];
        String[] prompts = {"Reihennummer", "Spaltennummer"}; String player = playersNames[moveCounter % 2];

        // Überprüfen, ob der aktuelle Zug von einem Bot oder einem menschlichen Spieler gemacht wird.
        if (player.equals("EasyBot")) { // Wenn der aktuelle Bot "EasyBot" ist, wird der Zug zufällig gewählt
            fieldInput = botPlayer(board);
            System.out.println("Der EasyBot hat das Feld " + ConsoleColors.YELLOW_BOLD + (fieldInput[0] + 1) + " " + ConsoleColors.BLUE_BOLD + (fieldInput[1] + 1) + ConsoleColors.RESET + " ausgewählt.\n");
            return  fieldInput;
        } else if (player.equals("HardBot")) { // "HardBot" versucht, klügere Entscheidungen zu treffen, basierend auf dem aktuellen Zustand des Spielfelds.
            fieldInput = botHardModPlayer(board, moveCounter);
            System.out.println("Der HardBot hat das Feld " + ConsoleColors.YELLOW_BOLD + (fieldInput[0] + 1) + " " + ConsoleColors.BLUE_BOLD + (fieldInput[1] + 1) + ConsoleColors.RESET + " ausgewählt.\n");
            return fieldInput;
        } else { // Wenn ein menschlicher Spieler am Zug ist, wird dessen Eingabe abgefragt.
            for (int i = 0; i < 2; i++) {
                System.out.print(player + ", bitte geben Sie die " + prompts[i] + " von 1 bis 3 ein: ");
                while (true) { // Eine Schleife zur Validierung der Eingaben, bis eine gültige Position gewählt wird.
                    try {
                        fieldInput[i] = scan.nextInt() - 1;
                        if (fieldInput[i] >= 0 && fieldInput[i] <= 2) break;
                        System.out.print(player + ", bitte geben Sie die " + prompts[i] + ConsoleColors.RED_BOLD_BRIGHT + " NUR" + ConsoleColors.RESET + " von 1 bis 3 ein: ");
                    } catch (Exception e) {
                        System.out.print(player + ", bitte geben Sie die " + prompts[i] + ConsoleColors.RED_BOLD_BRIGHT + " NUR" + ConsoleColors.RESET + " von 1 bis 3 ein: ");
                        scan.next(); // Rücksetzen des Scanners, falls eine ungültige Eingabe gemacht wurde.
                    }
                }
            }
        }

        if (!isFieldFree(board, fieldInput)) {  // Überprüfen, ob die eingegebene Position innerhalb des Spielfelds liegt und ob sie leer ist.
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Entschuldigung, aber dieses Feld: " + ConsoleColors.YELLOW_BOLD + (fieldInput[0] + 1) + " " + ConsoleColors.BLUE_BOLD + (fieldInput[1] + 1) + ConsoleColors.RED_BOLD_BRIGHT + " ist bereits besetzt :(" +  ConsoleColors.RESET );
            System.out.println("Bitte wählen Sie ein anderes Feld.");
            System.out.println();

            return userInput(board, playersNames, moveCounter);
        }
        System.out.println();

        return fieldInput; // Rückgabe der gültigen Koordinaten.
    }

    // Diese Methode überprüft, ob die eingegebene Position leer ist.
    public static boolean isFieldFree(char[][] board, int[] userInput) {
        return board[(userInput[0])][(userInput[1])] == '-';
    }

    public static boolean isBoardFull(char[][] board) {
        // Überprüfung, ob das Spielfeld vollständig gefüllt ist (Unentschieden).
        for (char[] rows : board) {
            for (char field : rows) {
                if (field == '-') {
                    return true;
                }
            }
        }
        return false;
    }

    // Diese Methode zeichnet den Zug eines Spielers auf dem Spielfeld auf.
    public static void moveRecord(char[][] board, int[] userInput, int moveCounter) {
        boolean xOrO = moveCounter % 2 == 1;
        if (xOrO) {
            board[userInput[0]][userInput[1]] = 'X'; // 'X' wird für den ersten Spieler gesetzt.
        } else {
            board[userInput[0]][userInput[1]] = 'O'; // 'O' wird für den zweiten Spieler gesetzt.
        }
    }

    // Diese Methode überprüft, ob ein Spieler gewonnen hat oder das Spiel unentschieden ist.
    public static String winOrDrawLogic(char[][] board, String[] playersNames ) {
        int sumDiagonal1 = 0;
        int sumDiagonal2 = 0;

        // Schleife durch alle Zellen des Spielfelds, um die Summen für Reihen, Spalten und Diagonalen zu berechnen.
        for (int i = 0; i < board.length; i++) {
            int sumRow = 0;
            int sumColumn = 0;

            for (int j = 0; j < board[i].length; j++) {
                sumRow += board[i][j]; // Summe der Reihe i.
                sumColumn += board[j][i]; // Summe der Spalte j.

                // Überprüfung, ob die Summen einem Sieg für 'X' oder 'O' entsprechen.
                if (sumRow == Main.WIN_SUM_X || sumColumn == Main.WIN_SUM_X) return playersNames[0]; // Spieler 'X' hat gewonnen.
                if (sumRow == Main.WIN_SUM_O || sumColumn == Main.WIN_SUM_O) return playersNames[1]; // Spieler 'O' hat gewonnen.
            }

            sumDiagonal1 += board[i][i]; // Summe der Hauptdiagonale.
            sumDiagonal2 += board[i][board.length - i - 1]; // Summe der Nebendiagonale.
        }

        // Überprüfung der Diagonalen.
        if (sumDiagonal1 == Main.WIN_SUM_X || sumDiagonal2 == Main.WIN_SUM_X) return playersNames[0]; // Spieler 'X' hat gewonnen.
        if (sumDiagonal1 == Main.WIN_SUM_O || sumDiagonal2 == Main.WIN_SUM_O) return playersNames[1]; // Spieler 'O' hat gewonnen.

        // Überprüfung, ob das Spielfeld vollständig gefüllt ist (Unentschieden). Wenn es noch leere Zellen gibt, ist es kein Unentschieden.
        // Rückgabe des Ergebnisses (Gewinner oder Unentschieden). Kein Ergebnis, das Spiel geht weiter.
        return isBoardFull(board) ? " " : "Unentschieden";
    }

    // Diese Methode fragt den Spieler, ob er noch eine Runde spielen möchte.
    public static int playAgain() {
        Scanner scan = new Scanner(System.in);
        int playAgain;

        // Abfrage, ob das Spiel fortgesetzt oder beendet werden soll.
        System.out.println("Wollen Sie noch einmal spielen?");
        System.out.print("Wenn ja, geben Sie 1 ein, wenn andere Spieler - 2, wenn nein - 3: ");
        while (true) {
            try {
                playAgain = scan.nextInt();
                if (playAgain >= 1 && playAgain <= 3) {
                    System.out.println();
                    break; // Gültige Eingabe, die Schleife wird beendet.
                } else {
                    System.out.print("Bitte geben Sie die Zahl " + ConsoleColors.RED_BOLD_BRIGHT + "NUR" + ConsoleColors.RESET + " von 1 bis 3 ein: ");
                }
            } catch (Exception e) {
                System.out.println("Etwas geht nicht!");
                scan.next(); // Rücksetzen des Scanners nach ungültiger Eingabe.
            }
        }

        return playAgain; // Rückgabe der Eingabe des Spielers.
    }

    // Diese Methode sortiert die Spielernamen und den Punktestand nach Punkten absteigend.
    // Spieler mit den meisten Punkten werden zuerst angezeigt.
    public static void reorderNamesAndScore(String[] playersNames, int[] finalScore) {
        // Temporäre Variablen zum Sortieren von Namen und Punkten.
        String tmp;
        int tempScore;

        // Einfache Sortierlogik
        for (int i = 0; i < playersNames.length / 2; i++) {
            tmp = playersNames[i];
            playersNames[i] = playersNames[playersNames.length - i - 1];
            playersNames[playersNames.length - i - 1] = tmp;
        }
        for (int i = 0; i < finalScore.length / 2; i++) {
            tempScore = finalScore[i];
            finalScore[i] = finalScore[finalScore.length - i - 1];
            finalScore[finalScore.length - i - 1] = tempScore;
        }
    }

    // Diese Methode zeigt eine Nachricht an, wenn das Spiel gewonnen wurde oder unentschieden endet.
    public static void winnerMessage(String winner, int moveCounter) {
        if (winner.equals("Unentschieden")) {
            System.out.println(ConsoleColors.GREEN_BOLD + "Diesmal steht es unentschieden :)" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN_BOLD + "Und der Gewinner ist " + winner + "!" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + winner + " hat es im " + moveCounter + " Zug getan. Herzlichen Glückwunsch!\n" + ConsoleColors.RESET);
        }
    }

    // Diese Methode aktualisiert den Punktestand der Spieler nach einem Spiel.
    public static void finalScore(String[] playersNames, String winner, int[] finalScore) {
        System.out.print("Endstand: ");
        for (int i = 0; i < playersNames.length; i++) {
            if (playersNames[i].equals(winner)) { // Punkt für Spieler [i].
                finalScore[i] += 1;
            }
            System.out.print(playersNames[i] + " - " + finalScore[i] + "\t");
        }
        System.out.println("\n");
    }

    // Diese Methode simuliert den Zug des "easyBot" auf Basis der zuverlässigen Strategie.
    public static int[] botPlayer(char[][] board) {
        int[] botInput = {RANDOM.nextInt(3), RANDOM.nextInt(3)};
        return isFieldFree(board, botInput) ? botInput : botPlayer(board);
    }

    // Diese Methode simuliert den Zug des "HardBot" auf Basis einer etwas "intelligenteren" Strategie.
    // Sie versucht, den besten Zug für den Bot zu finden, indem sie bestimmte Spielfelder bevorzugt.
    public static int[] botHardModPlayer(char[][] board, int moveCounter) {
        char botSymbol; char playerSymbol;

        if (moveCounter % 2 == 0) {
            botSymbol = 'X'; playerSymbol = 'O';

        } else {
            botSymbol = 'O'; playerSymbol = 'X';
        }

        // 1. Versuche zu gewinnen
        int[] move = tryToWinOrBlock(board, botSymbol);
        if (move != null) {
            return move; // Rückgabe des besten Zugs für die KI (O)
        }

        // 2. Blockiere den Gegner
        move = tryToWinOrBlock(board, playerSymbol);
        if (move != null) {
            return move; // Rückgabe des Blockierungszugs
        }

        // 3. Bevorzuge das mittlere Feld, falls es frei ist
        if (board[1][1] == ' ') {
            return new int[]{1, 1}; // Rückgabe: Bot wählt das mittlere Feld
        }

        // 4. Zufälliger Zug, falls keine der obigen Bedingungen zutrifft
        return botPlayer(board); // Verwendet einfachen Bot für zufällige Züge
    }

    // Methode, um zu prüfen, ob der Bot gewinnen oder den Gegner blockieren kann
    public static int[] tryToWinOrBlock(char[][] board, char currentSymbol) {
        // Prüfe Reihen, Spalten und Diagonalen
        for (int i = 0; i < 3; i++) {
            // Prüfe Reihen
            int[] move = checkTwoInLine(board[i][0], board[i][1], board[i][2], i, 0, i, 1, i, 2, currentSymbol);
            if (move != null) return move;

            // Prüfe Spalten
            move = checkTwoInLine(board[0][i], board[1][i], board[2][i], 0, i, 1, i, 2, i, currentSymbol);
            if (move != null) return move;
        }

        // Prüfe Diagonalen
        int[] move = checkTwoInLine(board[0][0], board[1][1], board[2][2], 0, 0, 1, 1, 2, 2, currentSymbol);
        if (move != null) return move;
        move = checkTwoInLine(board[0][2], board[1][1], board[2][0], 0, 2, 1, 1, 2, 0, currentSymbol);

        return move;// Kein Zug möglich
    }

    // Hilfsmethode, um zu prüfen, ob zwei Felder mit dem gleichen Symbol belegt sind und das dritte frei ist
    public static int[] checkTwoInLine(char a, char b, char c, int row1, int col1, int row2, int col2, int row3, int col3, char currentSymbol) {
        if (a == currentSymbol && b == currentSymbol && c == '-') {
            return new int[]{row3, col3}; // Rückgabe der Position des freien Felds
        }
        if (a == currentSymbol && c == currentSymbol && b == '-') {
            return new int[]{row2, col2}; // Rückgabe der Position des freien Felds
        }
        if (b == currentSymbol && c == currentSymbol && a == '-') {
            return new int[]{row1, col1}; // Rückgabe der Position des freien Felds
        }
        return null; // Kein Zug möglich
    }
}


