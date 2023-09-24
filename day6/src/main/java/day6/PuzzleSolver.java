package day6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PuzzleSolver {

    public static String task1(List<String> input) {
        return decrypt(input, true);
    }

    public static String task2(List<String> input) {
        return decrypt(input, false);
    }

    private static String decrypt(List<String> input, boolean biggest) {
        int len = input.get(0).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            Map<Character, Integer> occ = new HashMap<>();
            for (String s : input) {
                char c = s.charAt(i);
                occ.putIfAbsent(c, 0);
                occ.computeIfPresent(c, (k, v) -> v + 1);
            }

            int asInt = getOccurence(occ, biggest);
            Character key = occ.entrySet().stream().filter(k -> k.getValue() == asInt).findFirst().get().getKey();
            sb.append(key);
        }

        return sb.toString();
    }

    private static int getOccurence(Map<Character, Integer> occ, boolean biggest) {
        IntStream intStream = occ.values().stream().mapToInt(e -> e);

        if (biggest) {
            return intStream.max().getAsInt();
        } else {
            return intStream.min().getAsInt();
        }
    }
}
