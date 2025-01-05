package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;
import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

public class NGramMap {
    In wordFile;
    In countsFile;
    //List<List<String>> listOfListsWords = new ArrayList<>(); //[0] word, [1] year, [2] number, [3] distinct sources
    HashMap<String, TimeSeries> wordMap;
    TimeSeries countsTS = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordFile = new In(wordsFilename);
        countsFile = new In(countsFilename);
        wordMap = new HashMap<>();
        TimeSeries ts = new TimeSeries();
        while (wordFile.hasNextLine()) {
            String nextLineW = wordFile.readLine();
            String[] wordEntry = nextLineW.split("\t");
            //List<String> wordEntries = new ArrayList<>(Arrays.asList(wordEntry));
            if (wordMap.containsKey(wordEntry[0])) {
                wordMap.get(wordEntry[0]).put(Integer.parseInt(wordEntry[1]), Double.parseDouble(wordEntry[2]));
            } else {
                wordMap.put(wordEntry[0], new TimeSeries());
                wordMap.get(wordEntry[0]).put(Integer.parseInt(wordEntry[1]), Double.parseDouble(wordEntry[2]));
            }
        }
        while (countsFile.hasNextLine()) {
            String nextLineC = countsFile.readLine();
            String[] countsEntry = nextLineC.split(",");
            if (!countsEntry[0].isEmpty()) {
                countsTS.put(Integer.parseInt(countsEntry[0]), Double.parseDouble(countsEntry[1]));
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordMap.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordMap.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countsTS, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = new TimeSeries(wordMap.get(word), startYear, endYear);
        return ts.dividedBy(countsTS);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = new TimeSeries(wordMap.get(word), MIN_YEAR, MAX_YEAR);
        return ts.dividedBy(countsTS);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String s : words) {
            ts = ts.plus(weightHistory(s));
        }
        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for (String s : words) {
            ts = ts.plus(weightHistory(s));
        }
        return ts;
    }
}
