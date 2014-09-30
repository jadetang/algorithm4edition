import org.junit.Before;
import org.junit.Test;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class SolverTest {

    private int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int[][] anotherBlocks = new int[][]{{8,0,3},{4,1,2},{7,6,5}};
    private Board alreadySolved;
    private Board notSolveBoard;

    private Board feasibleBoard;
    private int[][] feasibleBlocks = new int[][]{{0,1,3},{4,2,5},{7,8,6}};


    @Before
    public void setUp() throws Exception {
        alreadySolved = new Board(blocks);
        notSolveBoard = new Board(anotherBlocks);
        feasibleBoard = new Board(feasibleBlocks);
    }


    @Test
    public void testAlreadSolve() throws Exception{
        Solver solver = new Solver(alreadySolved);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    @Test
    public void testFeasibleSolution() throws Exception{
        Solver solver = new Solver(feasibleBoard);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}
