import java.util.Random;


public class TestFlight {

    public static void main(String[] args) {
        String[] playersNames = new String[2];

        for (int i = 0; i < 10; i++) {
            botPlayer();

        }
    }

    public static void botPlayer() {
        Random random = new Random();
        int row = random.nextInt(3);
        int column = random.nextInt(3);
        System.out.println(ConsoleColors.RED + row + " " + column + ConsoleColors.RESET);
    }
}
