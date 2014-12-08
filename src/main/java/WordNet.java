import java.util.*;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class WordNet {

    private Digraph digraph;
    private Map<String, List<Integer>> synsetsMap = new HashMap<String, List<Integer>>();
    private Map<Integer, List<String>> synsetIndex = new HashMap<Integer, List<String>>();
    private int synsetCount = 0;

    public WordNet(String synsets, String hypernyms) {

        synsetsMap = init(synsets);
        digraph = new Digraph(synsetCount);
        addEdges(hypernyms);
    }

    private Iterable<Integer> getIndexs(String noun) {
        Iterable<Integer> result = synsetsMap.get(noun);
        assert result != null;
        return synsetsMap.get(noun);
    }

    private String getWordByIndex(Integer index) {

        List<String> words = synsetIndex.get(index);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word).append(" ");
        }
        return sb.toString().trim();
    }

    public Iterable<String> nouns() {
        return synsetsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetsMap.keySet().contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("not a noun");
        }
        SAP sap = new SAP(digraph);
        return sap.length(getIndexs(nounA), getIndexs(nounB));

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("not contain");
        }
        SAP sap = new SAP(digraph);
        int ancestorIndex = sap.ancestor(getIndexs(nounA), getIndexs(nounB));
        return getWordByIndex(ancestorIndex);
    }


    private void addEdges(String hypernyms) {
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] splits = line.split(",");
            Integer hyponymIndex = Integer.parseInt(splits[0]);
            for (int i = 1; i < splits.length; i++) {
                Integer hypernymIndex = Integer.parseInt(splits[i]);
                digraph.addEdge(hyponymIndex, hypernymIndex);
            }
        }
    }

    private Map<String, List<Integer>> init(String synsets) {
        Map<String, List<Integer>> resultMap = new HashMap<String, List<Integer>>();
        In in = new In(synsets);
        while (in.hasNextLine()) {
            synsetCount++;
            String line = in.readLine();
            String[] split = line.split(",");
            Integer index = Integer.parseInt(split[0]);
            String word = split[1];
            List<String> wordList = Arrays.asList(word.split(" "));
            synsetIndex.put(index, wordList);
            for (String tempWord : wordList) {
                List<Integer> indexs = resultMap.get(tempWord);
                if (indexs == null) {
                    List<Integer> tempList = new LinkedList<Integer>();
                    tempList.add(index);
                    resultMap.put(tempWord, tempList);
                } else {
                    indexs.add(index);
                }
            }
        }
        return resultMap;
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        System.out.println(wordNet.distance("1530s", "1770s"));
        System.out.println(wordNet.sap("1530s", "1770s"));
    }
}
