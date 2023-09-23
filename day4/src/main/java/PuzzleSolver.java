import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PuzzleSolver {

    public static int task1(List<String> puzzleInput) {
        return puzzleInput.stream()
                .map(PuzzleSolver::toRoom)
                .filter(PuzzleSolver::isRealRoom)
                .mapToInt(PuzzleSolver::toSectorId)
                .sum();
    }

    private static Room toRoom(String s) {
        Pattern compile = Pattern.compile("([-a-z]*-)+(\\d+)\\[([a-z]+)\\]");
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            String room = matcher.group(1);
            String sector = matcher.group(2);
            String checksum = matcher.group(3);
            return new Room(room, Integer.parseInt(sector), checksum);
        }

        throw new RuntimeException("Unexpected: " + s);
    }

    private static int toSectorId(Room room) {
        return room.sectorId;
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

        ArrayList<Pair> pairs = new ArrayList<>();
        for (Map.Entry<Character, Integer> characterIntegerEntry : characterIntegerHashMap.entrySet()) {
            pairs.add(new Pair(characterIntegerEntry.getKey(), characterIntegerEntry.getValue()));
        }

        pairs.sort((o1, o2) -> {
            int compare = Integer.compare(o2.o, o1.o);
            if (compare == 0) {
                return Character.compare(o1.c, o2.c);
            }
            return compare;
        });

        for (int i = 0; i < s.checksum.length(); i++) {
            if (pairs.get(i).c != s.checksum.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static int task2() {
        return 12;
    }

    private static class Pair {
        char c;
        int o;

        public Pair(char c, int o) {

            this.c = c;
            this.o = o;
        }
    }

    private static class Room {
        String encryptedName;
        int sectorId;
        String checksum;

        public Room(String encryptedName, int sectorId, String checksum) {
            this.encryptedName = encryptedName;
            this.sectorId = sectorId;
            this.checksum = checksum;
        }

        public String getEncryptedName() {
            return encryptedName;
        }

        public int getSectorId() {
            return sectorId;
        }

        public String getChecksum() {
            return checksum;
        }
    }
}
