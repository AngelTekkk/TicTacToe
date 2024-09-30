public class BotHardModv1 {
    /*
    // Diese Methode simuliert den Zug des "HardBot" auf Basis einer etwas "intelligenteren" Strategie.
    // Sie versucht, den besten Zug für den Bot zu finden, indem sie bestimmte Spielfelder bevorzugt.
    public static int[] botHardModPlayers(char[][] board, int moveCounter, String[] playersNames) {
        char botSymbol;
        char playerSymbol;
        int playerIndex;

        if (moveCounter % 2 == 0) {
            botSymbol = 'X';
            playerSymbol = 'O';
            playerIndex = 1;

        } else {
            botSymbol = 'O';
            playerSymbol = 'X';
            playerIndex = 0;
        }
        // 1. Проверка на выигрышный ход для бота
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = botSymbol;
                    if (winOrDrawLogic(board, playersNames).equals("Bot")) {
                        boardOutput(board);
                        return new int[] {i, j};
                    }
                    board[i][j] = '-'; // отмена хода
                }
            }
        }

        // 2. Блокировка выигрыша игрока
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = playerSymbol;
                    if (winOrDrawLogic(board, playersNames).equals(playersNames[playerIndex])) {
                        board[i][j] = '-'; // отмена хода
                        return new int[] {i, j};
                    }
                    board[i][j] = '-';
                }
            }
        }

        // 3. Занятие центральной клетки
        if (board[1][1] == '-') {
            return new int[] {1, 1};
        }

        // 4. Занятие угловых клеток
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] corner : corners) {
            if (board[corner[0]][corner[1]] == '-') {
                return corner;
            }
        }

        // 5. Занятие любых оставшихся клеток
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return new int[] {i, j};
                }
            }
        }

        System.out.println("BOT FAILED!");
        return new int[] {-1, -1}; // если нет доступных клеток
    }

     */
}
