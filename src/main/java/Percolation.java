import java.util.*;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] opens;
    private int rowSize;
    private int TOP_VIRTUAL_INDEX;
    private int BOTTOM_VIRTUAL_INDEX;

    public Percolation(int N) {
        if ( N <= 0 ){
            throw new IllegalArgumentException();
        }
        rowSize = N;
        TOP_VIRTUAL_INDEX = rowSize*rowSize;
        BOTTOM_VIRTUAL_INDEX = rowSize*rowSize+1;
        uf = new WeightedQuickUnionUF(rowSize * rowSize+2);
        opens = new boolean[rowSize * rowSize+2];
        for (int i = 0; i < opens.length; i++) {
            opens[i] = false;
        }
        opens[TOP_VIRTUAL_INDEX] = true;
        opens[BOTTOM_VIRTUAL_INDEX] = true;
    }


    public boolean isOpen(int i, int j) {
        int index = getIndex(i, j);
        return opens[index];
    }

    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            int index = getIndex(i, j);
            opens[index] = true;
            List<Integer> adjacent = getAdjacentIndexs(i, j);
            for (Integer adjacentIndex : adjacent) {
                if (opens[adjacentIndex]) {
                    uf.union(index, adjacentIndex);
                }
            }
        }
    }

    public boolean percolates() {
        return uf.connected(TOP_VIRTUAL_INDEX,BOTTOM_VIRTUAL_INDEX);
    }





    public boolean isFull(int i, int j) {
        if(isOpen(i,j)){
            int index = getIndex(i, j);
            return uf.connected(index,TOP_VIRTUAL_INDEX);
        }
        return false;
    }

    private int getIndex(int i, int j) {

        if( i> rowSize || j > rowSize || i < 1 || j < 1){
            throw new IndexOutOfBoundsException();
        }
        int index  = (i - 1) * rowSize + (j - 1);
        return index;
    }

    private List<Integer> getAdjacentIndexs(int i, int j) {
        List<Integer> adjacentIndexs = new ArrayList<Integer>();
        int index = getIndex(i, j);
        int leftIndex = index - 1;
        int rightIndex = index + 1;
        int upIndex = index - rowSize;
        int bottomIndex = index + rowSize;

        if (index % rowSize != 0) {
            adjacentIndexs.add(leftIndex);
        }
        if ((index + 1) % rowSize != 0) {
            adjacentIndexs.add(rightIndex);
        }
        if (index >= rowSize) {
            adjacentIndexs.add(upIndex);
        }else {
            adjacentIndexs.add(TOP_VIRTUAL_INDEX);
        }
        if (index < (rowSize - 1) * rowSize) {
            adjacentIndexs.add(bottomIndex);
        }else {
            adjacentIndexs.add(BOTTOM_VIRTUAL_INDEX);
        }
        return adjacentIndexs;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        System.out.println(p.percolates());
        System.out.println(p.isFull(4, 4));

    }

}
