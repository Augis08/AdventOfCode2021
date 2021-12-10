import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {
    String answer1 = "The Answer of Day 6 part 1 is ";
    String answer2 = "The Answer of Day 6 part 2 is ";
    String inputPath = "src/main/resources/Day6Input.txt";
    private final List<String> input;
    List<Integer> fishList;

    public Day6() {
        InputReader inputReader = new InputReader();
        this.input = inputReader.readFromFile(inputPath);
    }

    public void solve() {
        getFishList();
        System.out.println(answer1 + countAllFish(80));
        System.out.println(answer2 + countAllFish(256));
    }

    private void getFishList() {
        fishList = Arrays
                .stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private long countAllFish(int day) {
        int[] timersCounts = {1, 2, 3, 4, 5};
        Arrays.stream(timersCounts)
                .forEach(timer -> timersCounts[timer - 1] = Collections.frequency(fishList, timer));
        return IntStream
                .range(0, timersCounts.length)
                .mapToLong(i -> countFishForTimer(i + 1, day, 1) * timersCounts[i])
                .sum();
    }

    private long countFishForTimer(int timer, int day, long countFish) {
        if (day <= timer)
            return countFish;
        day -= timer;
        int createdFish = 1 + day / 7;
        if (day % 7 == 0)
            createdFish -= 1;
        countFish += createdFish;
        timer = 9;
        for (int i = 0; i < createdFish; i++) {
            countFish += countFishForTimer(timer, day, 0);
            day -= 7;
        }
        return countFish;
    }
}
