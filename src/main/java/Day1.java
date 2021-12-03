import java.util.List;
import java.util.stream.IntStream;

public class Day1 {

    String answer1 = "The Answer of Day 1 part 1 is ";
    String answer2 = "The Answer of Day 1 part 2 is ";
    String inputPath = "src/main/resources/Day1Input.txt";
    private int part1Answer = 0;
    private int part2Answer = 0;
    private List<String> input;
    InputReader inputReader = new InputReader();

    public void solve() {
        input = inputReader.readFromFile(inputPath);
        solvePart1();
        solvePart2();
        System.out.println(answer1 + part1Answer);
        System.out.println(answer2 + part2Answer);
    }

    private void solvePart1() {
        IntStream.range(1, input.size())
                .filter(i -> Integer.parseInt(input.get(i)) > Integer.parseInt(input.get(i - 1)))
                .forEach(i -> part1Answer += 1);
    }

    private void solvePart2() {
        IntStream.range(3, input.size())
                .filter(i -> (Integer.parseInt(input.get(i - 2))
                        + Integer.parseInt(input.get(i - 1))
                        + Integer.parseInt(input.get(i)))
                        > (Integer.parseInt(input.get(i - 3))
                        + Integer.parseInt(input.get(i - 2))
                        + Integer.parseInt(input.get(i - 1))))
                .forEach(i -> part2Answer += 1);
    }
}
