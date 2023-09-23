import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PuzzleSolverTest {

    @Test
    public void task1Test() throws Exception {
        assertEquals(2, PuzzleSolver.task1());
    }

    @Test
    public void task2Test() throws Exception {
        assertEquals(2, PuzzleSolver.task2());
    }

    private List<String> getPuzzleInput() throws Exception {
        URL resource = this.getClass().getResource("./puzzle_input.txt");
        return Files.readAllLines(Paths.get(resource.toURI()));
    }
}