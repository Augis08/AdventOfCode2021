import java.util.List;

public class Day2 {
    String answer1 = "The Answer of Day 2 part 1 is ";
    String answer2 = "The Answer of Day 2 part 2 is ";
    String inputPath = "src/main/resources/Day2Input.txt";
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
        int horizontalPosition = 0;
        int depth = 0;
        for (String s : input) {
            String[] command = s.split(" ");
            switch (command[0]) {
                case "forward":
                    horizontalPosition += Integer.parseInt(command[1]);
                    break;
                case "down":
                    depth += Integer.parseInt(command[1]);
                    break;
                case "up":
                    depth -= Integer.parseInt(command[1]);
                default: break;
            }
        }
        part1Answer = horizontalPosition * depth;
    }

    private void solvePart2() {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;
        for (String s : input) {
            String[] command = s.split(" ");
            switch (command[0]) {
                case "forward":
                    horizontalPosition += Integer.parseInt(command[1]);
                    depth += aim * Integer.parseInt(command[1]);
                    break;
                case "down":
                    aim += Integer.parseInt(command[1]);
                    break;
                case "up":
                    aim -= Integer.parseInt(command[1]);
                default: break;
            }
        }
        part2Answer = horizontalPosition * depth;
    }
}
