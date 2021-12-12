import java.util.*;

public class Day8 {
    String answer1 = "The Answer of Day 8 part 1 is ";
    String answer2 = "The Answer of Day 8 part 2 is ";
    String inputPath = "src/main/resources/Day8Input.txt";
    private int part1Answer = 0;
    private int part2Answer = 0;
    private final List<String> input;

    public Day8() {
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
        for (String s : input) {
            s = s.split(" \\| ")[1];
            String[] digits = s.split(" ");
            for (String digit : digits)
                if (digit.length() == 7 || digit.length() >= 2 && digit.length() <= 4)
                    part1Answer += 1;
        }
    }

    private void solvePart2() {
        for (String s : input) {
            Map<Integer, String> patterns = new HashMap<>();
            getPatterns(s, patterns);
            StringBuilder number = new StringBuilder();
            for(String segments: s.split(" \\| ")[1].split(" "))
                patterns.keySet().forEach(num -> {
                    if (patterns.get(num).equals(getSortedString(segments))) number.append(num);
                });
            part2Answer += Integer.parseInt(number.toString());
        }
    }

    private void getPatterns(String segments, Map<Integer, String> patterns) {
        String[] digits = segments.split(" \\| ")[0].split(" ");
        List<String> digits5 = new ArrayList<>();
        List<String> digits6 = new ArrayList<>();
        while (patterns.size() < 10)
            for (String num : digits) {
                if (patterns.size() + digits5.size() + digits6.size() < 10)
                    switch (num.length()) {
                        case 2 -> patterns.put(1, getSortedString(num));
                        case 3 -> patterns.put(7, getSortedString(num));
                        case 4 -> patterns.put(4, getSortedString(num));
                        case 5 -> digits5.add(getSortedString(num));
                        case 6 -> digits6.add(getSortedString(num));
                        case 7 -> patterns.put(8, getSortedString(num));
                    }
                else if (num.length() == 5)
                    getNumberWith5Segments(patterns, digits5);
                else if (num.length() == 6)
                    getNumbersWith6Digits(patterns, digits6);
            }
    }

    private boolean contains(String digit, String number) {
        for (String ch : number.split(""))
            if (!digit.contains(ch))
                return false;
        return true;
    }

    private void getNumbersWith6Digits(Map<Integer, String> patterns, List<String> digits6) {
        for (String digit6 : digits6) {
            if (!contains(digit6, patterns.get(1)))
                patterns.put(6, digit6);
            else if (contains(digit6, patterns.get(4)))
                patterns.put(9, digit6);
            else
                patterns.put(0, digit6);
        }
    }

    private void getNumberWith5Segments(Map<Integer, String> patterns, List<String> digits5) {
        for (String digit5 : digits5) {
            String fourMinusOne = patterns.get(4).replace(String.valueOf(patterns.get(1).charAt(0)), "");
            fourMinusOne = fourMinusOne.replace(String.valueOf(patterns.get(1).charAt(1)), "");
            if (contains(digit5, patterns.get(1)))
                patterns.put(3, digit5);
            else if (contains(digit5, fourMinusOne))
                patterns.put(5, digit5);
            else patterns.put(2, digit5);
        }
    }

    private String getSortedString(String d) {
        char[] array = d.toCharArray();
        Arrays.sort(array);
        d = new String(array);
        return d;
    }
}
