package synsets;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class MyHypGraph extends MyGraph<Integer> implements Graph61B {
    In synsetFile;
    In hypFile;
    List<List<String>> synsets;
    //Map<Integer, List<Integer>> graph;
    public MyHypGraph(String synsetFile, String hypFile) {
        super();
        this.synsetFile = new In(synsetFile);
        this.hypFile = new In(hypFile);
        this.synsets = new ArrayList<>();
        //this.graph = new HashMap<>();
        while (this.synsetFile.hasNextLine()) {
            String nextLineS = this.synsetFile.readLine();
            String[] synEntry = nextLineS.split(",");
            List<String> words = Arrays.asList(synEntry[1].split(" "));
            synsets.add(words);
        }
        while (this.hypFile.hasNextLine()) {
            String nextLineH = this.hypFile.readLine();
            String[] hypEntry = nextLineH.split(",");
            //List<Integer> hyponyms = new ArrayList<>();
            for (int i = 1; i < hypEntry.length; i++) {
                //hyponyms.add(Integer.parseInt(hypEntry[i]));
                this.addEdge(Integer.parseInt(hypEntry[0]), Integer.parseInt(hypEntry[i]));
                //hypGraph.addEdge(Integer.parseInt(hypEntry[0]), Integer.parseInt(hypEntry[i]));
            }
//            this.addEdge(Integer.parseInt(hypEntry[0]), hyponyms);
//            if (graph.containsKey(Integer.parseInt(hypEntry[0]))) {
//                graph.get(Integer.parseInt(hypEntry[0])).addAll(hyponyms);
//            } else {
//                graph.put(Integer.parseInt(hypEntry[0]), hyponyms);
//            }
        }
    }
    public List<Integer> findSynset(String s) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < synsets.size(); i++) {
            if (synsets.get(i).contains(s)) {
                ids.add(i);
            }
        }
        return ids;
    }
//    public Set<Integer> findDescendants(int id) {
//        Set<Integer> hyps = new HashSet<>();
//        findDescendantsHelper(id, hyps);
//        hyps.add(id);
//        return hyps;
//    }
//    public void findDescendantsHelper(int id, Set<Integer> set) {
//        if (!graph.containsKey(id)) {
//            set.add(id);
//        } else {
//            for (int i : graph.get(id)) {
//                findDescendantsHelper(i, set);
//                set.add(i);
//            }
//        }
//    }
    public Set<String> convertToSynset(String s) {
        List<Integer> ids = findSynset(s);
        Set<Integer> hypsIds = new HashSet<>();
        for (int id : ids) {
            hypsIds.addAll(depthFirstTraversal(id));
        }
        Set<String> words = new TreeSet<>();
        for (int id : hypsIds) {
            words.addAll(synsets.get(id));
        }
        return words;
    }
    public Set<String> convertToSynsetList(List<String> l) {
        if (l.isEmpty()) {
            return null;
        }
        Set<String> commonHyps = convertToSynset(l.get(0));
        for (int i = 1; i < l.size(); i++) {
            Set<String> hyps = convertToSynset(l.get(i));
            commonHyps.retainAll(hyps);
        }
        return commonHyps;

    }
}
