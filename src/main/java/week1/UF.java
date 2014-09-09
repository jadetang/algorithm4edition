package week1;

/**
 * @version 1.0
 * @author: tangsicheng
 * @date: 14-9-9
 * @since 1.0
 */
public interface UF {
    void union(int p, int q);
    int find(int p);
    boolean connected(int p, int q);
    int count();
}
