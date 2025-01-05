package synsets;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class MyAncGraph extends MyGraph<Integer> implements Graph61B {
    In synsetFile;
    In hypFile;
    List<List<String>> synsets;
    //Map<Integer, List<Integer>> ancestralGraph;
    public MyAncGraph(String synsetFile, String hypFile) {
        super();
        this.synsetFile = new In(synsetFile);
        this.hypFile = new In(hypFile);
        this.synsets = new ArrayList<>();
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
                //hypGraph.addEdge(Integer.parseInt(hypEntry[0]), Integer.parseInt(hypEntry[i]));
                this.addEdge(Integer.parseInt(hypEntry[i]), Integer.parseInt(hypEntry[0]));
            }
//            for (int i : hyponyms) {
//                if (ancestralGraph.containsKey(i)) {
//                    ancestralGraph.get(i).add(Integer.parseInt(hypEntry[0]));
//                } else {
//                    List<Integer> descendant = new ArrayList<>();
//                    descendant.add(Integer.parseInt(hypEntry[0]));
//                    ancestralGraph.put(i,descendant);
//                }
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
//    public Set<Integer> findParents(int id) {
//        Set<Integer> ancs = new HashSet<>();
//        findParentsHelper(id, ancs);
//        ancs.add(id);
//        return ancs;
//    }
//    public void findParentsHelper(int id, Set<Integer> set) {
//        if (!ancestralGraph.containsKey(id)) {
//            set.add(id);
//        } else {
//            for (int i : ancestralGraph.get(id)) {
//                findParentsHelper(i, set);
//                set.add(i);
//            }
//        }
//    }
    public Set<String> convertToSynset(String s) {
        List<Integer> ids = findSynset(s);
        Set<Integer> ancsIds = new HashSet<>();
        for (int id : ids) {
            ancsIds.addAll(depthFirstTraversal(id));
        }
        Set<String> words = new TreeSet<>();
        for (int id : ancsIds) {
            words.addAll(synsets.get(id));
        }
        return words;
    }
    public Set<String> convertToSynsetList(List<String> l) {
        if (l.isEmpty()) {
            return null;
        }
        Set<String> commonAncs = convertToSynset(l.get(0));
        for (int i = 1; i < l.size(); i++) {
            Set<String> hyps = convertToSynset(l.get(i));
            commonAncs.retainAll(hyps);
        }
        return commonAncs;
    }
}

