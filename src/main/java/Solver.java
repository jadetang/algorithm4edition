import java.util.*;

/**
 * Created by Administrator on 14-9-29.
 */
public class Solver {


    private boolean completed = false;

    private List<Board> solutionMoves = new ArrayList<Board>();
    private Board initialBoard;
    private Board targetBoard;
    private boolean solvable = false;

    public Solver(Board initial) {
        this.initialBoard = initial;
        this.targetBoard = getTargetBoard(initial.dimension());
        List<Board> initialBoardSolution = findSolution(initialBoard);

        solutionMoves = initialBoardSolution;
        solvable = true;


        /*Stack<Board> twinBoardSolution = findSolution(initialBoard.twin());
        if (initialBoardSolution.size() <= twinBoardSolution.size()) {
            solvable = true;
            solutionMoves = initialBoardSolution;
        } else {
            solvable = false;
        }*/

    }

    private Board getTargetBoard(int N) {
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = i * N + j + 1;
            }
        }
        blocks[N - 1][N - 1] = 0;
        return new Board(blocks);
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return solutionMoves.size() - 1;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        /*if (!completed) {
            List<Board> initialSolutionMoves = findSolution(initialBoard);
            List<Board> twinSolutionMoves  = findSolution(initialBoard.twin());
            if (initialSolutionMoves.size() > twinSolutionMoves.size()){
                solvable = false;
                completed = true;
                return null;
            }else{
                solvable = true;
                completed = true;;
                return (Iterable<Board>) initialSolutionMoves;
            }
        }*/
        return solutionMoves;
    }

    private List<Board> findSolution(Board board) {

        List<Board> moves = new ArrayList<Board>();

        MinPQ<Board> minPQ = new MinPQ<Board>(new HammingComparator());


        if (board.equals(targetBoard)) {
            moves.add(board);
        } else {
            minPQ.insert(board);
            Iterator<Board> firstNodeNeighbors = board.neighbors().iterator();
            while (firstNodeNeighbors.hasNext()) {
                minPQ.insert(firstNodeNeighbors.next());
            }
            moves.add(board);
            while (true) {
                Board previous = minPQ.delMin();
                moves.add(previous);
                if (previous.equals(targetBoard)) {
                    break;
                } else {
                    Board now = moves.get(moves.size()-1);
                    Iterator<Board> neighbors = now.neighbors().iterator();
                    while (neighbors.hasNext()) {
                        Board oneNeighbors = neighbors.next();
                        if (!oneNeighbors.equals(previous)) {
                            minPQ.insert(oneNeighbors);
                        }
                    }
                }
            }
        }
        return moves;

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class HammingComparator implements Comparator<Board> {
        @Override
        public int compare(Board o1, Board o2) {
            Integer hanmmingO1 = o1.hamming() + Solver.this.solutionMoves.indexOf(o1);
            Integer hanmmingO2 = o2.hamming() + Solver.this.solutionMoves.indexOf(o2);
            return hanmmingO1.compareTo(hanmmingO2);
        }
    }

}
