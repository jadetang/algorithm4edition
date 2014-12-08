/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int outcast = Integer.MIN_VALUE;
        String outCastString = null;
        for (int i = 0; i < nouns.length; i++) {
            int tempOutCast = 0;
            for (int j = 0; j < nouns.length; j++) {
                int tempDistance = wordNet.distance(nouns[i], nouns[j]);
                /*if( tempDistance != -1 ){
                    tempOutCast += tempDistance;
                }*/

                tempOutCast += tempDistance;
            }
            if (tempOutCast > outcast) {
                outcast = tempOutCast;
                outCastString = nouns[i];
            }
        }
        assert outCastString != null;
        return outCastString;
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = "wordnet/" + args[i];
        }
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
