import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PuzzleSolverTest {

    @Test
    public void task1Test() throws Exception {
        assertEquals(1514, PuzzleSolver.task1(Arrays.asList(
                "aaaaa-bbb-z-y-x-123[abxyz]",
                "a-b-c-d-e-f-g-h-987[abcde]",
                "not-a-real-room-404[oarel]",
                "totally-real-room-200[decoy]"
        )));
        assertEquals(409147, PuzzleSolver.task1(getPuzzleInput()));
    }

    @Test
    public void task2Test() throws Exception {
        assertEquals("very encrypted name", PuzzleSolver.decryptName(new PuzzleSolver.Room("qzmt-zixmtkozy-ivhz", 343, "")));
        assertEquals(991, PuzzleSolver.task2(getPuzzleInput()));
    }

    private List<String> getPuzzleInput() throws Exception {
        URL resource = this.getClass().getResource("./puzzle_input.txt");
        return Files.readAllLines(Paths.get(resource.toURI()));
    }
}