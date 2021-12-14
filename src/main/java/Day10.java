import java.util.*;

public class Day10 {

    String answer1 = "The Answer of Day 10 part 1 is ";
    String answer2 = "The Answer of Day 10 part 2 is ";
    String inputPath = "src/main/resources/Day10Input.txt";
    private int part1Answer = 0;
    private long part2Answer = 0;
    private final List<String> input;
    private List<String> incompleteLines;

    public Day10() {
        InputReader inputReader = new InputReader();
        this.input = inputReader.readFromFile(inputPath);
    }

    public void solve() {
        solvePart1();
        solvePart2();
        System.out.println(answer1 + part1Answer);
        System.out.println(answer2 + part2Answer);
    }

    private void solvePart1() {
        Map<Character, Integer> illegalCharacters = new HashMap<>();
        incompleteLines = new ArrayList<>(input);
        for (String s : input) {
            char[] chars = s.toCharArray();
            List<Character> openBrackets = new ArrayList<>();
            for (char ch : chars)
                if (isOpenBracket(ch))
                    openBrackets.add(ch);
                else if (isValidCloseBracket(ch, openBrackets.get(openBrackets.size() - 1)))
                    openBrackets.remove(openBrackets.size() - 1);
                else {
                    illegalCharacters.merge(ch, 1, Integer::sum);
                    incompleteLines.remove(s);
                    break;
                }
        }
        part1Answer = countSyntaxErrorScore(illegalCharacters);
    }

    private void solvePart2() {
        List<List<Character>> incompleteOpenBrackets = new ArrayList<>();
        for (String s : incompleteLines) {
            char[] chars = s.toCharArray();
            List<Character> openBrackets = new ArrayList<>();
            for (char ch : chars)
                if (isOpenBracket(ch))
                    openBrackets.add(ch);
                else
                    openBrackets.remove(openBrackets.size() - 1);
            Collections.reverse(openBrackets);
            incompleteOpenBrackets.add(openBrackets);
        }
        part2Answer = getMiddleScore(incompleteOpenBrackets);
    }

    private int countSyntaxErrorScore(Map<Character, Integer> illegalCharacters) {
        int score = 0;
        for (char ch : illegalCharacters.keySet())
            switch (ch) {
                case ')' -> score += (illegalCharacters.get(ch) * 3);
                case ']' -> score += (illegalCharacters.get(ch) * 57);
                case '}' -> score += (illegalCharacters.get(ch) * 1197);
                case '>' -> score += (illegalCharacters.get(ch) * 25137);
            }
        return score;
    }

    private boolean isValidCloseBracket(char close, char open) {
        boolean isValid = false;
        switch (close) {
            case ')' -> isValid = open == '(';
            case ']' -> isValid = open == '[';
            case '}' -> isValid = open == '{';
            case '>' -> isValid = open == '<';
        }
        return isValid;
    }

    private boolean isOpenBracket(char character) {
        return character == '{' || character == '(' || character == '[' || character == '<';
    }

    private long getMiddleScore(List<List<Character>> incompleteOpenBrackets) {
        List<Long> scores = new ArrayList<>();
        for (List<Character> chars : incompleteOpenBrackets) {
            long score = 0;
            for (char ch : chars) {
                switch (ch) {
                    case '(' -> score = score * 5 + 1;
                    case '[' -> score = score * 5 + 2;
                    case '{' -> score = score * 5 + 3;
                    case '<' -> score = score * 5 + 4;
                }
            }
            scores.add(score);
        }
        Collections.sort(scores);
        if (scores.size() % 2 != 0)
            return scores.get(scores.size() / 2);
        return (scores.get(scores.size() / 2 - 1) + scores.get(scores.size() / 2)) / 2;
    }
}
