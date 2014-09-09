package week1;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @version 1.0
 * @author tangsicheng
 * @since 1.0
 */
public class UF_quickFindImpl implements UF {

    private int[] id;
    private int count;

    public UF_quickFindImpl(int n){
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i]=i;
        }
    }

    @Override
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);
        if( pid != qid ){
            for (int i = 0; i < id.length; i++) {
                if( id[i] == pid ){
                    id[i] = qid;
                }
            }
        }
        count--;
    }

    @Override
    public int find(int p) {
        while( id[p] != p){
            p = id[p];
        }
        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
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
        UF_quickFindImpl uf = new UF_quickFindImpl(10);
        uf.union(2,8);
        uf.union(5,2);
        uf.union(9,8);
        uf.union(0,2);
        uf.union(6,2);
        uf.union(1,5);
        System.out.println( uf);
    }

}

