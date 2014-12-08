import java.util.*;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TrieSET trieSET = new TrieSET();

    private int score[];


    public BoggleSolver(String[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            trieSET.add(dictionary[i]);
        }
        initScore();
    }

    private void initScore() {
        score = new int[17];
        for (int i = 0; i <= 16; i++) {
            if (i >= 0 && i <= 2) {
                score[i] = 0;
                continue;
            }
            if (i >= 3 && i <= 4) {
                score[i] = 1;
                continue;
            }
            if (i == 5) {
                score[i] = 2;
                continue;
            }
            if (i == 6) {
                score[i] = 3;
                continue;
            }
            if (i == 7) {
                score[i] = 5;
                continue;
            }
            score[i] = 11;
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < board.cols() * board.cols(); i++) {
            boolean[] marked = new boolean[board.cols() * board.cols()];
            dfs(i, "", board, result, marked);
        }
        return result;
    }

    private void dfs(int start, String previous, BoggleBoard board, Set<String> wordSet, boolean[] marked) {
        marked[start] = true;
        String now = previous + getChar(board, start);
        if (now.length() < 3) {
            for (Integer index : adjacent(board, start, marked)) {
                boolean[] copy = Arrays.copyOf(marked, marked.length);
                dfs(index, now, board, wordSet, copy);
            }
        } else {
            if (!trieSET.keysWithPrefix(now).iterator().hasNext()) {
                return;
            } else {
                wordSet.add(now);
                for (Integer index : adjacent(board, start, marked)) {
                    boolean[] copy = Arrays.copyOf(marked, marked.length);
                    dfs(index, now, board, wordSet, copy);
                }
            }
        }
    }


    private String getChar(BoggleBoard board, int index) {
        assert board.cols() == board.rows();
        int row = index / board.cols();
        int col = index % board.cols();
        char result = board.getLetter(row, col);
        if (result == 'Q') return "QU";
        return String.valueOf(result);
    }


    private List<Integer> adjacent(BoggleBoard boggleBoard, int index, boolean[] marked) {
        List<Integer> result = new LinkedList<>();
        int rowSize = boggleBoard.cols();


        int leftUp = index - rowSize - 1;
        if (!isLeft(index, rowSize) && !isTop(index, rowSize) && !marked[leftUp]) {
            result.add(leftUp);
        }

        int upIndex = index - rowSize;
        if (!isTop(index, rowSize) && !marked[upIndex]) {
            result.add(upIndex);
        }


        int rightUp = index - rowSize + 1;
        if (!isTop(index, rowSize) && !isRight(index, rowSize) && !marked[rightUp]) {
            result.add(rightUp);
        }

        int leftIndex = index - 1;
        if (!isLeft(index, rowSize) && !marked[leftIndex]) {
            result.add(leftIndex);
        }

        int rightIndex = index + 1;
        if (!isRight(index, rowSize) && !marked[rightIndex]) {
            result.add(rightIndex);
        }


        int leftBottom = index + rowSize - 1;
        if (!isButtom(index, rowSize) && !isLeft(index, rowSize) && !marked[leftBottom]) {
            result.add(leftBottom);
        }

        int bottomIndex = index + rowSize;
        if (!isButtom(index, rowSize) && !marked[bottomIndex]) {
            result.add(bottomIndex);
        }


        int rightBottom = index + rowSize + 1;
        if (!isRight(index, rowSize) && !isButtom(index, rowSize) && !marked[rightBottom]) {
            result.add(rightBottom);
        }

        return result;
    }


    private boolean isTop(int index, int rowSize) {
        return index < rowSize;
    }

    private boolean isButtom(int index, int rowSize) {
        return index >= (rowSize - 1) * rowSize;
    }

    private boolean isLeft(int index, int rowSize) {
        return index % rowSize == 0;
    }

    private boolean isRight(int index, int rowSize) {
        return (index + 1) % rowSize == 0;
    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (trieSET.contains(word)) {
            return score[word.length()];
        }
        return 0;
    }


    public static void main(String[] args) {
        // testadjacent();

        String fileName = "dictionary-algs4.txt";
        fileName = "boggle/" + fileName;
        String boardName = "board-q.txt";
        boardName = "boggle/" + boardName;
        In in = new In(fileName);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(boardName);
        int score = 0;
        List<String> reuslt = new LinkedList<>();
        for (String word : solver.getAllValidWords(board)) {
            reuslt.add(word);

        }
        Collections.sort(reuslt);
        for (String word : reuslt) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

    }

    private static void testadjacent() {
        BoggleSolver solver = new BoggleSolver(new String[]{});
        BoggleBoard board = new BoggleBoard();
        boolean[] marked = new boolean[board.cols() * board.cols()];
        marked[5] = true;
        for (int i = 0; i < board.cols() * board.cols(); i++) {
            List<Integer> result;
            result = solver.adjacent(board, i, marked);
            Collections.sort(result);
            System.out.println("index " + i);
            System.out.println(result);
            System.out.println("---------");
        }
    }


}
