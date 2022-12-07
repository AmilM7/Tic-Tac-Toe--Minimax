import java.util.Scanner;

public class MiniMax_Implementation {
    private final int size = 3;
    private final Scanner scan = new Scanner(System.in);
    private final Value[][] board = new Value[size][size];

    private enum Value {
        X("X"), O("O"), EMPTY(" ");
        private final String symbol;

        Value(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public MiniMax_Implementation() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = Value.EMPTY;
            }
        }
    }

    public void gameStarts() {
        String program = "O"; // Human always starts first
        String opponent = "X";
        int[] input;

        System.out.println();
        System.out.println("Game starts! Good luck!");
        System.out.println("Input should be in this format ~ x,y.   X for row and Y for column");
        System.out.println("Minimum: 1, Maximum: 3");
        System.out.println(this);

        while (true) {
            System.out.println("Your move: ");
            input = getInput();
            move(input, opponent);
            System.out.println(this);


            if (finishTest(Value.X) == 1) {
                System.out.println("Draw! You're as smart as minimax algorithm!");
                break;
            } else if (finishTest(Value.X) == 2) {
                System.out.println("Congratulations, you won!");   //Impossible
                break;
            }

            System.out.println("Computer move: ");

            input = programMove();
            move(input, program);
            System.out.println(this);

            if (finishTest(Value.O) == 2) {
                System.out.println("Program won. I am sorry.");
                break;
            }
        }
    }

    private int finishTest(Value symbol) {   // finishTest is function which will return 0 if there is neither draw and win, 1 for draw and 2 for win
        int count = 0;

        for (int i = 0; i < size; i++) {
            for (int t = 0; t < size; t++) {
                if (board[i][t] != Value.EMPTY) {  //testing for draw
                    count++;
                }
            }
        }
        if (count == 9) {
            return 1;
        }


        count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == symbol) {
                    count++;
                }
            }
            if (count == size) {
                return 2;
            } else {
                count = 0;
            }
        }

        count = 0;
        for (int t = 0; t < size; t++) {
            for (int i = 0; i < size; i++) {
                if (board[i][t] == symbol) {
                    count++;
                }
            }
            if (count == size) {
                return 2;
            } else {
                count = 0;
            }
        }

        count = 0;
        for (int i = 0; i < size; i++) {
            if (board[i][i] == symbol) {
                count++;
            }
        }

        if (count == size) {
            return 2;
        } else {
            count = 0;
        }

        for (int i = 0; i < size; i++) {
            if (board[i][size - i - 1] == symbol) {
                count++;
            }
        }
        if (count == size) {
            return 2;
        }

        return 0;
    }


    private void move(int[] coordinate, String playerType) {
        Value currentCell = board[coordinate[0]][coordinate[1]];
        if (currentCell == Value.EMPTY) {
            if (playerType == "X") {
                board[coordinate[0]][coordinate[1]] = Value.X;
            } else {
                board[coordinate[0]][coordinate[1]] = Value.O;
            }
        } else {
            System.out.println("Invalid move.");
            coordinate = getInput();
            move(coordinate, playerType);
        }
    }

    private int heuristicFunction() {
        if (finishTest(Value.X) == 2)
            return -1;
        else if (finishTest(Value.O) == 2)
            return 1;
        else
            return 0;
    }


    private int minimax(int depth, boolean isMax) {
        int score = heuristicFunction();

        if (score == 1 || score == -1) {
            return score;
        }
        if (finishTest(Value.X) == 1) {
            return 0;
        }

        if (isMax) {
            int bestScore = -100000000;
            for (int i = 0; i < size; i++) {
                if (board[i][0] == Value.EMPTY) {
                    board[i][0] = Value.O;
                    bestScore = Math.max(bestScore, minimax(depth + 1, !isMax));
                    board[i][0] = Value.EMPTY;
                }
                if (board[i][1] == Value.EMPTY) {
                    board[i][1] = Value.O;
                    bestScore = Math.max(bestScore, minimax(depth + 1, !isMax));
                    board[i][1] = Value.EMPTY;
                }
                if (board[i][2] == Value.EMPTY) {
                    board[i][2] = Value.O;
                    bestScore = Math.max(bestScore, minimax(depth + 1, !isMax));
                    board[i][2] = Value.EMPTY;
                }
            }

            return bestScore;
        } else {
            int bestScore = 1000000000;

            for (int i = 0; i < size; i++) {
                if (board[i][0] == Value.EMPTY) {
                    board[i][0] = Value.X;
                    bestScore = Math.min(bestScore, minimax(depth + 1, !isMax));
                    board[i][0] = Value.EMPTY;
                }
                if (board[i][1] == Value.EMPTY) {
                    board[i][1] = Value.X;
                    bestScore = Math.min(bestScore, minimax(depth + 1, !isMax));
                    board[i][1] = Value.EMPTY;
                }
                if (board[i][2] == Value.EMPTY) {
                    board[i][2] = Value.X;
                    bestScore = Math.min(bestScore, minimax(depth + 1, !isMax));
                    board[i][2] = Value.EMPTY;
                }
            }

            return bestScore;
        }
    }

    private int[] programMove() {
        int highestScore = -10000000;
        int[] bestInput = new int[2];

        for (int i = 0; i < size; i++) {
            for (int t = 0; t < size; t++) {
                if (board[i][t] == Value.EMPTY) {
                    board[i][t] = Value.O;

                    int finalScore = minimax(0, false);
                    board[i][t] = Value.EMPTY;

                    if (highestScore < finalScore) {
                        bestInput[0] = i;
                        bestInput[1] = t;
                        highestScore = finalScore;
                    }
                }
            }
        }

        return bestInput;
    }

    private boolean validInput(int[] input) {
        return input[0] < size && input[1] < size && input[0] >= 0 && input[1] >= 0;
    }

    private int[] getInput() {
        String[] input = scan.nextLine().split(",");
        int x = Integer.parseInt(input[0]);
        int y = Integer.parseInt(input[1]);
        x--;
        y--;

        if (!validInput(new int[]{x, y})) {
            System.out.println("Invalid move. Please, try again: ");
            input = scan.nextLine().split(",");
            x = Integer.parseInt(input[0]);
            y = Integer.parseInt(input[1]);
        }

        return new int[]{x, y};
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();

        for (int i = 0; i < size; i++) {
            display.append("— — — — — — —\n");
            for (int t = 0; t < size; t++) {
                display.append("| ").append(board[i][t].toString()).append(" ");
                if (t == size - 1) {
                    display.append("| ");
                }
            }
            display.append("\n");
            if (i == size - 1) {
                display.append("— — — — — — —\n");
            }
        }
        return display.toString();
    }
}
