package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import synsets.Graph61B;
import synsets.MyAncGraph;
import synsets.MyHypGraph;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    String synsetFile;
    String hyponymFile;
    NGramMap map;
    Graph61B graph;
    MyAncGraph ancGraph;
    MyHypGraph hypGraph;

    public HyponymsHandler(String s, String h, NGramMap map) {
        this.map = map;
        this.synsetFile = s;
        this.hyponymFile = h;
        ancGraph = new MyAncGraph(s, h);
        hypGraph = new MyHypGraph(s, h);
    }

    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        NgordnetQueryType type = q.ngordnetQueryType();
        if (type == NgordnetQueryType.HYPONYMS) {
            graph = hypGraph;
        } else {
            graph = ancGraph;
        }
        Set<String> synsets = graph.convertToSynsetList(words);
        if (k == 0) {
            return synsets.toString();
        }
        Map<String, Double> totalCounts = new HashMap<>();
        TimeSeries ts;
        for (String word : synsets) {
            double counts = 0;
            ts = map.countHistory(word, startYear, endYear);
            for (Double d : ts.data()) {
                counts += d;
            }
            totalCounts.put(word, counts);
        }
        if (totalCounts.size() <= k) {
            Set<String> result = new TreeSet<>();
            for (String s : totalCounts.keySet()) {
                if (totalCounts.get(s) != 0) {
                    result.add(s);
                }
            }
            return result.toString();
        } else {
            List<Double> ordered = new ArrayList<>(totalCounts.values());
            Collections.sort(ordered);
            Collections.reverse(ordered);
            Set<String> result = new TreeSet<>();
            Iterator<Double> iter = ordered.iterator();
            for (int i = 0; i < k; i++) {
                Double value = iter.next();
                for (String s : totalCounts.keySet()) {
                    if (totalCounts.get(s).equals(value) && value != 0) {
                        result.add(s);
                    }
                }
            }
            return result.toString();
        }
    }
}

