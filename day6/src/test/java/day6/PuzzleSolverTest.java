package day6;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PuzzleSolverTest {

    @Test
    public void task1Test() throws Exception {
        assertEquals("easter", PuzzleSolver.task1(getPuzzleTestInput()));
        assertEquals("xhnqpqql", PuzzleSolver.task1(getPuzzleInput()));
    }

    @Test
    public void task2Test() throws Exception {
        assertEquals("advent", PuzzleSolver.task2(getPuzzleTestInput()));
        assertEquals("brhailro", PuzzleSolver.task2(getPuzzleInput()));
    }

    private List<String> getPuzzleInput() throws Exception {
        URL resource = this.getClass().getResource("/puzzle_input.txt");
        return Files.readAllLines(Paths.get(resource.toURI()));
    }

    private List<String> getPuzzleTestInput() throws Exception {
        URL resource = this.getClass().getResource("/puzzle_test_input.txt");
        return Files.readAllLines(Paths.get(resource.toURI()));
    }
}