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
    private int opendBlocks;

    public Percolation(int N) {
        rowSize = N;
        uf = new WeightedQuickUnionUF(rowSize * rowSize);
        opens = new boolean[rowSize * rowSize];
        for (int i = 0; i < opens.length; i++) {
            opens[i] = false;
        }
    }


    public boolean isOpen(int i, int j) {
        int index = getIndex(i, j);
        return opens[index];
    }

    public void open(int i, int j) {
        if (isOpen(i, j)) {
            return;
        } else {
            int index = getIndex(i, j);
            opens[index] = true;
            List<Integer> adjacent = getAdjacentIndexs(i, j);
            for (Integer adjacentIndex : adjacent) {
                if (opens[adjacentIndex]) {
                    uf.union(index, adjacentIndex);
                }
            }
            opendBlocks ++;
        }
    }

    public boolean percolates() {
        Set<Integer> upIdSet = getPercolateIdSet();
        return (!upIdSet.isEmpty()) && (upIdSet.size() == 1);
    }

    public boolean isFull(int i, int j) {
        int index = getIndex(i, j);
        int id = uf.find(index);
        if (percolates()) {
            Set<Integer> percolateHashSet = getPercolateIdSet();
            return percolateHashSet.contains(id);
        }
        return false;
    }

    private Set<Integer> getPercolateIdSet() {
        Set<Integer> upIdSet = new HashSet<>();
        Set<Integer> bottomIdSet = new HashSet<>();
        for (int i = 0; i < rowSize; i++) {
            upIdSet.add(uf.find(i));
        }
        for (int i = (rowSize - 1) * rowSize; i < rowSize * rowSize; i++) {
            bottomIdSet.add(uf.find(i));
        }
        upIdSet.retainAll(bottomIdSet);
        return upIdSet;
    }


    private int getIndex(int i, int j) {
        return (i - 1) * rowSize + (j - 1);
    }

    private List<Integer> getAdjacentIndexs(int i, int j) {
        List<Integer> adjacentIndexs = new ArrayList<>();
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
        }
        if (index < (rowSize - 1) * rowSize) {
            adjacentIndexs.add(bottomIndex);
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
        p.open(2, 1);
        System.out.println(p.percolates());
        System.out.println(p.isFull(2, 1));
    }

    public int getOpendBlocks() {
        return opendBlocks;
    }
}
