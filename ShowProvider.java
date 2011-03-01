package jd_crawler;

import java.util.Collection;

public interface ShowProvider {
    
    public Collection<String> getAllShows();

    public ShowInfo getShowInfo(String name);

}
