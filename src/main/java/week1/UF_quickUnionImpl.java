package week1;


import org.apache.commons.lang3.ArrayUtils;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class UF_quickUnionImpl implements UF {

    private int[] id;
    private int count;
    private int[] size;


    public UF_quickUnionImpl(int n) {
        count = n;
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);
        if( pid == qid) {
            return;
        }
        if(size[pid] >= size[qid]){
            id[qid] = pid;
            size[pid] += size[qid];
        }else{
            id[pid] = qid;
            size[qid] += size[pid];
        }
        count--;
    }

    @Override
    public int find(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public String toString(){
        return ArrayUtils.toString(id);
    }

    public static void main(String[] args) {
        UF_quickUnionImpl uf = new UF_quickUnionImpl(10);
        uf.union(6,7);
        uf.union(4,7);
        uf.union(3,0);
        uf.union(8,9);
        uf.union(4,5);
        uf.union(0,9);
        uf.union(2,4);
        uf.union(0,5);
        uf.union(8,1);
        System.out.println( uf);
    }
}
