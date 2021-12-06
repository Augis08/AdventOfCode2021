import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    String answer1 = "The Answer of Day 4 part 1 is ";
    String answer2 = "The Answer of Day 4 part 2 is ";
    String inputPath = "src/main/resources/Day4Input.txt";
    private int part1Answer = 0;
    private int part2Answer = 0;
    private List<String> input;
    InputReader inputReader = new InputReader();

    public void solve() {
        input = inputReader.readFromFile(inputPath);
        List<int[][]> boards = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        getNumbersAndBoards(numbers, boards);
        solvePart1(numbers, boards);
        solvePart2(numbers, boards);
        System.out.println(answer1 + part1Answer);
        System.out.println(answer2 + part2Answer);
    }

    private void getNumbersAndBoards(List<Integer> numbers, List<int[][]> boards) {
        int[][] board = new int[5][5];
        boolean isNumbers = true;
        int row = 0;
        for (String s : input) {
            if (isNumbers) {
                if (s.equals("")) {
                    isNumbers = false;
                    continue;
                }
                Arrays.stream(s.split(",")).map(Integer::parseInt).forEach(numbers::add);
                continue;
            }
            if (!s.equals("")) {
                String[] boardRow = s.split(" ");
                int column = 0;
                for (String c : boardRow)
                    if (!c.equals("")) {
                        board[row][column] = Integer.parseInt(c);
                        column += 1;
                    }
                row += 1;
            }
            if (row == 5) {
                row = 0;
                boards.add(board);
                board = new int[5][5];
            }
        }
    }

    private void solvePart1(List<Integer> numbers, List<int[][]> boards) {
        int lastCalledNumber = 0;
        int boardNumber = 0;
        List<boolean[][]> markedNumbers = new ArrayList<>();
        for (int i = 0; i < boards.size(); i++)
            markedNumbers.add(new boolean[5][5]);
        for (int number : numbers)
            for (int i = 0; i < boards.size(); i++)
                for (int row = 0; row < 5; row++)
                    for (int column = 0; column < 5; column++)
                        if (number == boards.get(i)[row][column]) {
                            markedNumbers.get(i)[row][column] = true;
                            if (isMarkedLine(markedNumbers.get(i), row, column)) {
                                lastCalledNumber = number;
                                boardNumber = i;
                                part1Answer = countScore(lastCalledNumber,
                                        boards.get(boardNumber),
                                        markedNumbers.get(boardNumber));
                                return;
                            }
                        }
    }

    private int countScore(int lastCalledNumber, int[][] board, boolean[][] markedNumbers) {
        int count = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (!markedNumbers[i][j])
                    count += board[i][j];
        return count * lastCalledNumber;
    }

    private boolean isMarkedLine(boolean[][] markedNumbers, int row, int column) {
        int markedRow = 0;
        int markedColumn = 0;
        for (int i = 0; i < 5; i++) {
            if (markedNumbers[row][i])
                markedRow += 1;
            if (markedNumbers[i][column])
                markedColumn += 1;
        }
        return markedRow == 5 || markedColumn == 5;
    }

    private void solvePart2(List<Integer> numbers, List<int[][]> boards) {
        int lastCalledNumber = 0;
        int boardNumber = 0;
        boolean boardWon = false;
        List<boolean[][]> markedNumbers = new ArrayList<>();
        for (int i = 0; i < boards.size(); i++)
            markedNumbers.add(new boolean[5][5]);
        for(int number: numbers)
            for (int j = boards.size() - 1; j >= 0; j--)
                for (int row = 0; row < 5; row++) {
                    if (boardWon) {
                        boardWon = false;
                        break;
                    }
                    for (int column = 0; column < 5; column++)
                        if (number == boards.get(j)[row][column]) {
                            markedNumbers.get(j)[row][column] = true;
                            if (isMarkedLine(markedNumbers.get(j), row, column)) {
                                lastCalledNumber = number;
                                boardNumber = j;
                                part2Answer = countScore(lastCalledNumber,
                                        boards.get(boardNumber),
                                        markedNumbers.get(boardNumber));
                                boards.remove(j);
                                markedNumbers.remove(j);
                                boardWon = true;
                                break;
                            }
                        }
                }
    }
}
