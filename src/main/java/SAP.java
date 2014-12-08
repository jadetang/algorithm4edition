import java.util.Iterator;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class SAP {
    // constructor takes a digraph (not necessarily a DAG)
    private Digraph digraph;



    public SAP(Digraph G) {
        digraph  = new Digraph(G.V());
        for (int i = 0; i < G.V(); i++) {
            Iterator<Integer> it = G.adj(i).iterator();
            while(it.hasNext()){
                digraph.addEdge(i,it.next());
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfsa = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfab = new BreadthFirstDirectedPaths(digraph,w);
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if(bfsa.hasPathTo(i)&&bfab.hasPathTo(i)){
                distance = Math.min(distance,bfsa.distTo(i)+bfab.distTo(i));
            }
        }
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        return distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfsa = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfab = new BreadthFirstDirectedPaths(digraph,w);
        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if(bfsa.hasPathTo(i)&&bfab.hasPathTo(i)){
               // distance = Math.min(distance,bfsa.distTo(i)+bfab.distTo(i));
                int tempDistance = bfsa.distTo(i)+bfab.distTo(i);
                if(tempDistance<distance){
                    distance = tempDistance;
                    ancestor = i;
                }
            }
        }
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){


        Iterator<Integer> itv = v.iterator();
        int distance = Integer.MAX_VALUE;
        while(itv.hasNext()){
            int tempV = itv.next();
            Iterator<Integer> itw = w.iterator();
            while(itw.hasNext()){
                int tempW = itw.next();
                int tempLength = length(tempV,tempW);
                distance = Math.min(distance,tempLength);
            }
        }
        return distance;
    }

     public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        Iterator<Integer> itv = v.iterator();
        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        while(itv.hasNext()){
            Iterator<Integer> itw = w.iterator();
            int tempV = itv.next();
            while(itw.hasNext()){
                int tempW = itw.next();
                int tempLength = length(tempV,tempW);
                if(tempLength<distance){
                    ancestor = ancestor(tempV,tempW);
                }

            }
        }
        return ancestor;
    }



    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    // public int length(Iterable<Integer> v, Iterable<Integer> w)

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    //public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("wordnet/digraph1.txt");
     //   In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
