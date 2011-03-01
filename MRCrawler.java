package jd_crawler;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

public class MRCrawler {

    public List<List<String>> MRCrawl() throws Exception{
        List<List<String>> res = new ArrayList<List<String>>();
        String currentPage, currentURL;
        List<String> nextPageMatching = new ArrayList<String>();
        nextPageMatching.add("http://megarelease.net/category/television/");
        int k=0;
        do{
            currentURL = nextPageMatching.get(0);
            currentPage = getWebPage(currentURL);
            //List<String> TVShows = Matches(currentPage, "\"([^\"]+)\" class=\"more-link");
            List<String> TVShows = Matches(currentPage, "posttitle\"><a href=\"[^\"]+\">([^<]+)</a>");
            for(String s : TVShows){
                List<String> tmp = new ArrayList<String>();
                tmp.add(s);
                res.add(tmp);
            }
            /*for(String tvshow : TVShows){
                List<String> tmp = new ArrayList<String>();
                String tmpPage = getWebPage(tvshow);
                List<String> tmp2 = Matches(tmpPage, "<title>([^&]+)&");
                if(tmp2.isEmpty()) continue;
                tmp.add(tmp2.get(0));
                Set<String> tmpSet = new TreeSet<String>();
                tmpSet.addAll( Matches(tmpPage, "(http://www.megaupload.com/\\?d=[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9])") );
                if( !tmpSet.isEmpty() ){
                    tmp.addAll( tmpSet );
                    res.add( tmp );
                }
            }*/
            k++;
            if( k>10 ) break;
        } while( !(nextPageMatching = Matches(currentPage, "\"([^\"]+)\" >&laquo; Older Entries</a>")).isEmpty() );
        return res;
    }


    private List<String> Matches(String sToMatch, String regex) {
        List<String> sList = new ArrayList<String>();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sToMatch);
        while (m.find()) {
            sList.add(m.group(1));
        }

        return sList;
    }

    private String getWebPage(String url) throws Exception {
        BufferedReader BR = new BufferedReader(
                new InputStreamReader(
                new URL(url).openStream()));

        String line;
        StringBuilder page = new StringBuilder();
        while ((line = BR.readLine()) != null) {
            page.append(line).append("\n");
        }

        return page.toString();
    }
}
