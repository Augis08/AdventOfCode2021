import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {
    String answer1 = "The Answer of Day 5 part 1 is ";
    String answer2 = "The Answer of Day 5 part 2 is ";
    String inputPath = "src/main/resources/Day5Input.txt";
    private int part1Answer = 0;
    private int part2Answer = 0;
    private final List<String> input;
    int[][] area;
    List<int[]> lines = new ArrayList<>();

    public Day5() {
        InputReader inputReader = new InputReader();
        this.input = inputReader.readFromFile(inputPath);
    }

    public void solve() {
        getLinesAndArea();
        solvePart1();
        solvePart2();
        System.out.println(answer1 + part1Answer);
        System.out.println(answer2 + part2Answer);
    }

    private void solvePart1() {
        for (int[] line : lines) {
            drawHorizontalAndVerticalLines(line);
        }
        part1Answer = countOverlapPoints(area);
    }

    private void solvePart2() {
        for (int[] line : lines)
            drawDiagonalLine(line);
        part2Answer = countOverlapPoints(area);
    }

    private void drawHorizontalAndVerticalLines(int[] line) {
        if (isHorizontal(line) || isVertical(line)) {
            int lineLength = line[0] + line[1] - line[2] - line[3];
            int sign = lineLength / Math.abs(lineLength);
            lineLength = Math.abs(lineLength);
            while (lineLength >= 0) {
                if (isHorizontal(line)) {
                    area[line[3]][line[2] + lineLength * sign] += 1;
                } else
                    area[line[3] + lineLength * sign][line[2]] += 1;
                lineLength -= 1;
            }
        }
    }

    private void drawDiagonalLine(int[] line) {
        if (!isHorizontal(line) && !isVertical(line)) {
            int lineLength = Math.abs(line[0] - line[2]);
            int signV = (line[0] - line[2]) / lineLength;
            int signH = (line[1] - line[3]) / lineLength;
            while (lineLength >= 0) {
                area[line[3] + lineLength * signH][line[2] + lineLength * signV] += 1;
                lineLength -= 1;
            }
        }
    }

    private int countOverlapPoints(int[][] area) {
        return Arrays
                .stream(area)
                .mapToInt(row -> (int) Arrays.stream(row).filter(col -> col > 1).count())
                .sum();
    }

    private boolean isVertical(int[] line) {
        return line[0] == line[2];
    }

    private boolean isHorizontal(int[] line) {
        return line[1] == line[3];
    }

    private void getLinesAndArea() {
        int areaSize = 0;
        for (String s : input) {
            int[] line = new int[4];
            s = s.replace(" -> ", ",");
            String[] coordinates = s.split(",");
            for (int i = 0; i < line.length; i++) {
                line[i] = Integer.parseInt(coordinates[i]);
                if (line[i] > areaSize)
                    areaSize = line[i];
            }
            lines.add(line);
        }
        area = new int[areaSize + 1][areaSize + 1];
    }
}
