package jd_crawler;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

public class Main {

    static class Result {
        public String showName;
        public float score;

        public Result(String showName, float score) {
            this.showName = showName;
            this.score = score;
        }


    }

    public static Result getShow(Collection<String> shows, String text) {
        String maxShow = "";
        float maxScore = 0;
        for(String s : shows) {
            float score = StringUtils.showScore(s, text);
//            if(score > 0.3) System.out.println("\t" + s + "(" + score + ")");

            if(maxScore < score) {
                maxScore = score;
                maxShow = s;
            }
            if (maxScore > 0.7) {
                break;
            }
        }
        return new Result(maxShow, maxScore);
    }

    public static void main(String[] args) throws Exception {
        ShowProvider sp = new TheTVDB_Provider();
        System.out.println("Starting crawl ...");
        Collection<String> shows = sp.getAllShows();
        System.out.println("Finished (" + shows.size() + ")");
        MRCrawler plop = new MRCrawler();
        for(List<String> links : plop.MRCrawl()) {
            Result res = getShow(shows, links.get(0));
            System.out.println(res.showName + " (" + res.score + ")");
            System.out.println("\t" + links.get(0));
        }

    }
}
