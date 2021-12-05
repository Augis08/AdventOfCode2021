import java.util.ArrayList;
import java.util.List;

public class Day3 {
    String answer1 = "The Answer of Day 3 part 1 is ";
    String answer2 = "The Answer of Day 3 part 2 is ";
    String inputPath = "src/main/resources/Day3Input.txt";
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
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();
        int[] countZero = new int[input.get(0).length()];
        int[] countOne = new int[input.get(0).length()];

        for (String s : input)
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) == '0')
                    countZero[i] += 1;
                else
                    countOne[i] += 1;

        for (int i = 0; i < countZero.length; i++)
            if (countZero[i] > countOne[i]) {
                gammaRate.append(0);
                epsilonRate.append(1);
            } else {
                gammaRate.append(1);
                epsilonRate.append(0);
            }

        part1Answer = Integer.parseInt(gammaRate.toString(), 2)
                * Integer.parseInt(epsilonRate.toString(), 2);
    }

    private void solvePart2() {
        int bitNo = 0;
        List<String> oxygenGeneratorRating = new ArrayList<>(input);
        List<String> co2ScrubberRating = new ArrayList<>(input);

        while (oxygenGeneratorRating.size() > 1 || co2ScrubberRating.size() > 1) {
            boolean sortingCondition;
            int count1 = countNumbersWithSomeBit(bitNo, '1', oxygenGeneratorRating);
            sortingCondition = count1 < oxygenGeneratorRating.size() - count1;
            getRelevantNumbers(bitNo, count1, sortingCondition, oxygenGeneratorRating);

            int count0 = countNumbersWithSomeBit(bitNo, '0', co2ScrubberRating);
            sortingCondition = count0 <= co2ScrubberRating.size() - count0;
            getRelevantNumbers(bitNo, count0, sortingCondition, co2ScrubberRating);
            bitNo += 1;
        }
        part2Answer = Integer.parseInt(oxygenGeneratorRating.get(0), 2)
                * Integer.parseInt(co2ScrubberRating.get(0), 2);
    }

    private int countNumbersWithSomeBit(int bitNo, char bit, List<String> numbers) {
        int count = 0;
        for (String s : numbers)
            if (s.charAt(bitNo) == bit)
                count += 1;
        return count;
    }

    private void getRelevantNumbers(int bitNo, int count, boolean sortingCondition, List<String> sortedInput) {
        if (sortedInput.size() == 1)
            return;
        List<String> listToSort = new ArrayList<>(sortedInput);
        if (sortingCondition) {
            for (String s : listToSort)
                if (s.charAt(bitNo) == '1')
                    sortedInput.remove(s);
        } else
            for (String s : listToSort)
                if (s.charAt(bitNo) == '0')
                    sortedInput.remove(s);
    }
}
