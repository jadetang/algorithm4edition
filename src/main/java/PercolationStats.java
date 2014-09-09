/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class PercolationStats {
    // perform T independent computational experiments on an N-by-N grid
    private double[] result;
    private int passTimes;


    public PercolationStats(int N, int T){
        if(N<=0 || T<=0){
            throw new IllegalArgumentException("should be greater than 0。");
        }
        result = new double[T];
        passTimes = T;
        for (int i = 0; i < passTimes; i++) {
            result[i] = onePass(N);
        }
    }

    public double mean(){
        double temp = 0.0;
        for (int i = 0; i < result.length; i++) {
            temp += result[i];
        }
        return temp/passTimes;
    }

    private double onePass(int N){
        Percolation p = new Percolation(N);
        double count = 0.0;
        while(!p.percolates()){
            int x = StdRandom.uniform(1, N + 1);
            int y = StdRandom.uniform(1, N + 1);
            if (!p.isOpen(x, y)) {
                p.open(x, y);
                count++;
            }
        }
        return count * 1.00 / (N * N);
    }

    public double stddev(){
        double mean = mean();
        double temp = 0.0;
        for (int i = 0; i < result.length; i++) {
            temp += ( result[i] - mean ) * ( result[i] - mean );
        }
        return Math.sqrt( temp/(passTimes-1));
    }

    public double confidenceLo(){
        double v = mean() - (1.96 * stddev()) / Math.sqrt((double) passTimes);
        return v;
    }

    public double confidenceHi(){
        double v = mean() + (1.96 * stddev()) / Math.sqrt((double) passTimes);
        return v;
    }


    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        for (int i = 1; i< 10; i++) {
            PercolationStats p = new PercolationStats(N,T);
        }
    }
}
