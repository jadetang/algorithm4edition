import java.util.*;

/**
 * Created by Administrator on 14-9-29.
 */
public class Solver {


    private volatile boolean completed = false;

    private List<Board> solutionMoves = new ArrayList<Board>();
    private List<Board> twinSolutionMoves = new ArrayList<Board>();
    private Board initialBoard;
    private boolean solvable = false;

    private boolean initialSolution = false;

    private boolean twinSolution = false;

    public Solver(Board initial) {
        this.initialBoard = initial;

        final Board twin = initial.twin();

        completed = false;

        //    findSolution(solutionMoves, initialBoard, "initial");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                findSolution(solutionMoves, initialBoard, "initial");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                findSolution(twinSolutionMoves, twin, "twin");
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (initialSolution) {
            solvable = true;
        }
        if (twinSolution) {
            solvable = false;
        }
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
        return solutionMoves;
    }

    private void findSolution(List<Board> moves, Board board, String whichBoard) {
        MinPQ<Board> minPQ = new MinPQ<Board>(new ManhattanComparator());
       /* if (whichBoard.equals("initial")) {
            minPQ = new MinPQ<Board>(new HammingComparator());
        } else {
            minPQ = new MinPQ<Board>(new TwinHammingComparator());
        }*/
        if (board.isGoal()) {
            moves.add(board);
            if (whichBoard.equals("initial")) {
                this.initialSolution = true;
            } else {
                this.twinSolution = true;
            }
            completed = true;
        } else {
            Iterator<Board> firstNodeNeighbors = board.neighbors().iterator();
            while (firstNodeNeighbors.hasNext()) {
                minPQ.insert(firstNodeNeighbors.next());
            }
            moves.add(board);
            while (!completed) {
                Board now = minPQ.delMin();

                int priority = now.manhattan();
                System.out.println("insert a board, priority " + priority + "; " + now);
                moves.add(now);
                if (now.isGoal()) {
                    if (whichBoard.equals("initial")) {
                        this.initialSolution = true;
                    } else {
                        this.twinSolution = true;
                    }
                    completed = true;
                } else {
                    Board previous = moves.get(moves.size() - 1);
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
            int move = Solver.this.solutionMoves.size() - 1;
            Integer hanmmingO1 = o1.hamming() + move;
            Integer hanmmingO2 = o2.hamming() + move;
            return hanmmingO1.compareTo(hanmmingO2);
        }
    }

    private class TwinHammingComparator implements Comparator<Board> {
        @Override
        public int compare(Board o1, Board o2) {
            int move = Solver.this.twinSolutionMoves.size() - 1;
            Integer hanmmingO1 = o1.hamming() + move;
            Integer hanmmingO2 = o2.hamming() + move;
            return hanmmingO1.compareTo(hanmmingO2);
        }
    }

    private class ManhattanComparator implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            Integer manhattan1 = o1.manhattan();
            Integer manhattan2 = o2.manhattan();
            return manhattan1.compareTo(manhattan2);
        }
    }

}
