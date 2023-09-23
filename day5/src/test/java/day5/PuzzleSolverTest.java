package day5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PuzzleSolverTest {

    @Test
    public void task1Test() throws Exception {
        byte[] test = PuzzleSolver.getDigest("abc", 3231929);
        assertEquals(0, test[0]);
        assertEquals(0, test[1]);

        assertEquals("18f47a30", PuzzleSolver.task1("abc"));
        assertEquals("f97c354d", PuzzleSolver.task1("reyedfim"));
    }

    @Test
    public void task2Test() throws Exception {
        assertEquals("05ace8e3", PuzzleSolver.task2("abc"));
        assertEquals("863dde27", PuzzleSolver.task2("reyedfim"));
    }
}