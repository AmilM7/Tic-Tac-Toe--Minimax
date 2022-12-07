
public class Tic_Tac_Toe_Main {
    public static void main(String[] args) {
        try {
            MiniMax_Implementation t = new MiniMax_Implementation();
            t.gameStarts();
        } catch (Exception e) {
            System.out.println("Invalid input. Program finished.");
            }
        }
    }

