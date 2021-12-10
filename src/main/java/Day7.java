import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day7 {
    String answer1 = "The Answer of Day 7 part 1 is ";
    String answer2 = "The Answer of Day 7 part 2 is ";
    String inputPath = "src/main/resources/Day7Input.txt";
    private final List<String> input;

    public Day7() {
        InputReader inputReader = new InputReader();
        this.input = inputReader.readFromFile(inputPath);
    }

    public void solve() {

        System.out.println(answer1 + countLeastFuel(1));
        System.out.println(answer2 + countLeastFuel(2));
    }

    private int countLeastFuel(int part) {
        int[] positions = getPositions();
        int max = positions[0];
        int totalLeastFuel = 0;
        int move = 0;
        while (move <= max) {
            int fuel = 0;
            for (int i : positions) {
                if (move == 0 && i > max)
                    max = i;
                switch (part) {
                    case 1 -> fuel += Math.abs(i - move);
                    case 2 -> fuel += IntStream.rangeClosed(1, Math.abs(i - move)).sum();
                }
            }
            if (fuel < totalLeastFuel || move == 0)
                totalLeastFuel = fuel;
            move ++;
        }
        return totalLeastFuel;
    }

    private int[] getPositions() {
        return Arrays
                .stream(input.get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private int countFuel(int moves) {
        return IntStream.rangeClosed(1, moves).sum();
    }
}
