/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TrieSET trieSET = new TrieSET();
    public BoggleSolver(String[] dictionary){
        for (int i = 0; i <dictionary.length; i++) {
            trieSET.add(dictionary[i]);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        return null;
    }


    private char getChar(BoggleBoard board, int index){
        assert board.cols() == board.rows();
        int row = index / board.cols();
        int col = index % board.cols();
        return board.getLetter(row,col);
    }


    private Iterable<Integer>getAdjacentChars(BoggleBoard boggleBoard,int index,boolean[] marked){
        Bag<Integer> result = new Bag<Integer>();
        int rowSize = boggleBoard.cols();

        int leftIndex = index - 1;
        if (index % rowSize != 0 && !marked[leftIndex]) {
            result.add(leftIndex);
        }

        int rightIndex = index + 1;
        if ((index + 1) % rowSize != 0 && !marked[rightIndex]) {
            result.add(rightIndex);
        }

        int upIndex = index - rowSize;
        if (index >= rowSize && !marked[upIndex]) {
            result.add(upIndex);
        }

        int bottomIndex = index + rowSize;
        if (index < (rowSize - 1) * rowSize && !marked[bottomIndex]) {
            result.add(bottomIndex);
        }



        return result;
    }












    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        return 1;
    }


    public static void main(String[] args) {
        BoggleSolver solver = new BoggleSolver(new String[]{"x"});
        BoggleBoard boggleBoard = new BoggleBoard();

        System.out.println(boggleBoard.getLetter(1,3));
        char x = solver.getChar(boggleBoard,7);
        System.out.println(x);
    }

}
