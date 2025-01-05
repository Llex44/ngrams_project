package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import synsets.MyHypGraph;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        return new HyponymsHandler(synsetFile, hyponymFile, new NGramMap(wordFile, countFile));
    }
}
