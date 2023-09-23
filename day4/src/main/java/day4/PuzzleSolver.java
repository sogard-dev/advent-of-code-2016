package day4;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PuzzleSolver {

    public static int task1(List<String> puzzleInput) {
        return puzzleInput.stream()
                .map(PuzzleSolver::parse)
                .filter(PuzzleSolver::isRealRoom)
                .mapToInt(r -> r.sectorId)
                .sum();
    }

    public static int task2(List<String> puzzleInput) {
        return puzzleInput.stream()
                .map(PuzzleSolver::parse)
                .filter(PuzzleSolver::isRealRoom)
                .filter(room -> {
                    String realName = decryptName(room);
                    return realName.startsWith("northpole object storage ");
                })
                .mapToInt(r -> r.sectorId)
                .sum();
    }

    private static Room parse(String s) {
        Pattern compile = Pattern.compile("([-a-z]*-)+(\\d+)\\[([a-z]+)]");
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            String room = matcher.group(1);
            String sector = matcher.group(2);
            String checksum = matcher.group(3);
            return new Room(room, Integer.parseInt(sector), checksum);
        }

        throw new RuntimeException("Unexpected: " + s);
    }

    private static boolean isRealRoom(Room s) {
        TreeMap<Character, Integer> characterIntegerHashMap = new TreeMap<>();
        for (char c : s.encryptedName.toCharArray()) {
            if (c == '-') {
                continue;
            }
            characterIntegerHashMap.putIfAbsent(c, 0);
            characterIntegerHashMap.computeIfPresent(c, (k, v) -> v + 1);
        }

        List<Pair> pairs = characterIntegerHashMap.entrySet().stream()
                .map(e -> new Pair(e.getKey(), e.getValue()))
                .sorted((o1, o2) -> {
                    int compare = Integer.compare(o2.o, o1.o);
                    if (compare == 0) {
                        return Character.compare(o1.c, o2.c);
                    }
                    return compare;
                })
                .collect(Collectors.toList());

        for (int i = 0; i < s.checksum.length(); i++) {
            if (pairs.get(i).c != s.checksum.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static String decryptName(Room r) {
        int start = 'a';
        int gap = 'z' - 'a' + 1;
        int shift = r.sectorId % gap;

        return r.encryptedName.chars()
                .map(i -> {
                    if (i == '-') {
                        return ' ';
                    }
                    return ((i - start + shift) % gap) + start;
                })
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    private static class Pair {
        char c;
        int o;

        public Pair(char c, int o) {
            this.c = c;
            this.o = o;
        }
    }

    static class Room {
        String encryptedName;
        int sectorId;
        String checksum;

        public Room(String encryptedName, int sectorId, String checksum) {
            this.encryptedName = encryptedName;
            this.sectorId = sectorId;
            this.checksum = checksum;
        }
    }
}
