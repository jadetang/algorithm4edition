import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class Board {
    private int[][] blocks;

    private Board targetBoard;

    private Board previousBoard;

    private int N;


    private Board(int N) {
        this.N = N;
        blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = i * N + j + 1;
            }
        }
        blocks[N - 1][N - 1] = 0;
    }


    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.N = blocks.length;
        targetBoard = new Board(blocks.length);
    }

    public Board twin() {
        int[][] copyArray = copyTwoDimArray(this.blocks);
        for (int i = 0; i < copyArray.length; i++) {
            int[] row = copyArray[i];
            outer:
            for (int j = 0; j < row.length - 1; j++) {
                if (row[j] != 0 && row[j + 1] != 0) {
                    exec(row, j, j + 1);
                    break outer;
                }
            }
        }
        return new Board(copyArray);
    }

    private void exceInTwoDimArray(int[][] array, int ai, int aj, int bi, int bj) {
        int temp = array[ai][aj];
        array[ai][aj] = array[bi][bj];
        array[bi][bj] = temp;
    }

    private int[][] copyTwoDimArray(int[][] originArray) {
        int[][] copyArray = new int[originArray.length][];
        for (int i = 0; i < originArray.length; i++) {
            copyArray[i] = Arrays.copyOf(originArray[i], originArray.length);
        }
        return copyArray;
    }

    private void exec(int[] row, int i, int j) {
        int temp = row[i];
        row[i] = row[j];
        row[j] = temp;
    }


    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0) {
                    if (blocks[i][j] != targetBoard.blocks[i][j]) {
                        hamming++;
                    }
                }
            }

        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0) {
                    manhattan += countManhattan(blocks, i, j);
                }
            }
        }
        return manhattan;
    }

    private int countManhattan(int[][] blocks, int i, int j) {
        int blockNumber = blocks[i][j];
        int length = blocks.length;
        if (blockNumber == i * length + j + 1) {
            return 0;
        } else {
            int y = (blockNumber - 1) / length;
            int x = (blockNumber - 1) % length;
            return Math.abs(y - i) + Math.abs(x - j);
        }
    }

    public boolean isGoal() {
        return this.equals(targetBoard);
    }


    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y instanceof Board == false) return false;
        Board that = (Board) y;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public Iterable<Board> neighbors() {
        List<Board> result = new LinkedList<Board>();
        int zeroI = 0;
        int zeroJ = 0;
        outer:
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break outer;
                }
            }
        }
        for (int i = -1; i < 2; i += 2) {
            if (notOutArray(zeroI + i) ) {
                int[][] copy = copyTwoDimArray(this.blocks);
                exceInTwoDimArray(copy, zeroI, zeroJ, zeroI + i, zeroJ);
                Board b = new Board(copy);
                if (this.previousBoard != null) {
                    if (!this.previousBoard.equals(b)) {
                        result.add(b);
                    }
                }else{
                    result.add(b);
                }
            }
        }
        for (int i = -1; i < 2; i += 2) {
            if (notOutArray(zeroJ + i)) {
                int[][] copy = copyTwoDimArray(this.blocks);
                exceInTwoDimArray(copy, zeroI, zeroJ, zeroI, zeroJ + i);
                Board b = new Board(copy);
                if (this.previousBoard != null) {
                    if (!this.previousBoard.equals(b)  ) {
                        result.add(b);
                    }
                }else{
                    result.add(b);
                }
            }
        }
        return result;
    }

    private boolean notOutArray(int i) {
        return i < this.blocks.length && i >= 0;
    }


}
