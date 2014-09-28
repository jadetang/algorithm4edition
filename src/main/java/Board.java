/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class Board {
    private int[][] blocks;

    private Board targetBoard;

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


}
