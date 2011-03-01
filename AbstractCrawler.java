package jd_crawler;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

public abstract class AbstractCrawler implements ShowProvider {

    public String website;
    public String entrypoint;
    public String regex_TVShows;
    public String TVShow_pref;
    public String TVShow_suf;
    public TreeMap<String, Pattern> RegexMap = new TreeMap<String, Pattern>();
    public TreeMap<String, ShowInfo> infos;

    public AbstractCrawler() {
    }

    public AbstractCrawler(String website, String entrypoint, String regex_TVShows, String TVShow_pref, String TVShow_suf,
            String type, String name, String genres, String season_number, String episodes_number, String country,
            String channel, String stopped, String first_diffusion, String last_diffusion, String actors,
            String creators, String image_url, String running_time,
            String writters, String directors, String synopsis) {
        this.website = website;
        this.entrypoint = website + entrypoint;
        this.regex_TVShows = regex_TVShows;
        this.TVShow_pref = TVShow_pref;
        this.TVShow_suf = TVShow_suf;

        RegexMap.put("type", Pattern.compile(type));
        RegexMap.put("name", Pattern.compile(name));
        RegexMap.put("genres", Pattern.compile(genres));
        RegexMap.put("season_number", Pattern.compile(season_number));
        RegexMap.put("episodes_number", Pattern.compile(episodes_number));
        RegexMap.put("country", Pattern.compile(country));
        RegexMap.put("channel", Pattern.compile(channel));
        RegexMap.put("stopped", Pattern.compile(stopped));
        RegexMap.put("first_diffusion", Pattern.compile(first_diffusion));
        RegexMap.put("last_diffusion", Pattern.compile(last_diffusion));
        RegexMap.put("actors", Pattern.compile(actors));
        RegexMap.put("creators", Pattern.compile(creators));
        RegexMap.put("image_url", Pattern.compile(image_url));
        RegexMap.put("running_time", Pattern.compile(running_time));
        RegexMap.put("writters", Pattern.compile(writters));
        RegexMap.put("directors", Pattern.compile(directors));
        RegexMap.put("synopsis", Pattern.compile(synopsis));

    }

    public Collection<String> getAllShows() {
        try {
            if (infos == null) {
                Crawl();
            }
            return infos.keySet();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ShowInfo getShowInfo(String name) {
        return infos.get(name);
    }



    public void Crawl() throws Exception {

        infos = new TreeMap<String, ShowInfo>();
        // We get the mainPage and parse it
        String mainPageContent = getWebPage(entrypoint);
        List<String> TVShows = Matches(mainPageContent, regex_TVShows);
        TVShows = applyFilter(TVShows, "action=edit");

        int k = 0;

        // We look at every tv show page
        for (String partial_url : TVShows) {
//            if(k > 10) break;
            String complete_url = website + TVShow_pref + partial_url + TVShow_suf;
            String TVShow_Page = "";//getWebPage(complete_url);
            TVShow_Handling(TVShow_Page, partial_url);
            k++;
        }

    }

    private void TVShow_Handling(String Page, String PageName) {
        if (Page.contains("{{disambig}}")) {
            return;
        }
        ShowInfo info = new ShowInfo();
        String name = PageName.replace("_", " ").replaceAll("\\(.*\\)", "");
        /*for (String Column : RegexMap.navigableKeySet()) {
            String value = simpleMatch(Page, RegexMap.get(Column));
            //System.out.println(Column + " -> " + value );
            if (Column.equals("name")) {
                name = value;
            }
            //TODO remplir info avec Column
        }*/
        if(name != null) infos.put(name, info);
        //System.out.println("#####################################");
    }

    private List<String> applyFilter(List<String> list, String regex_filter) {
        List<String> newList = new ArrayList<String>();

        Pattern p = Pattern.compile(regex_filter);
        for (String s : list) {
            if (!p.matcher(s).find()) {
                newList.add(s);
            }
        }

        return newList;
    }

    private String simpleMatch(String sToMatch, Pattern regex) {
        Matcher m = regex.matcher(sToMatch);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
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
