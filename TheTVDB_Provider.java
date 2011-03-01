package jd_crawler;

import java.util.Collection;

public class TheTVDB_Provider extends AbstractCrawler {

    public TheTVDB_Provider(){
        super(
                    // Website
              "file:///C:/Users/Mystic/Documents/thetvdb.html",
                    // Entrypoint
              "", // ?string=&searchseriesid=&tab=listseries&function=Search
                    // Regex for the tvshows list of the entrypoint
              "tab=series[^\"]+\">([^<]+)</a>",
                    // Prefix and suffix for the url gets on the tvshows
              "/w/index.php?title=",
              "&action=raw",
                    // Regex for the informations on the tvshow details (other_definition for a complex handling, null if undefined)
            /* type */ 			"\\{\\{Infobox ([^\\n]+)",
            /* name */ 			"show_name\\s+=([^\\n]+)",
            /* genres */ 		"genre\\s+=([^\\n]+)",
            /* season_number */         "num_seasons\\s+=([^\\n]+)",
            /* episodes_number */       "num_episodes\\s+=([^\\n]+)",
            /* country */ 		"country\\s+=([^\\n]+)",
            /* network */ 		"show_name\\s+=([^\\n]+)",
            /* stopped */ 		"status\\s+=([^\\n]+)",
            /* first_diffusion */ 	"first_aired\\s+=([^\\n]+)",
            /* last_diffusion */ 	"last_aired\\s+=([^\\n]+)",
            /* actors */ 		"starring\\s+=([^\\n]+)",
            /* creators */ 		"creator\\s+=([^\\n]+)",
            /* image_url */ 		"image\\s+=([^\\n]+)",
            /* running_time */ 		"runtime\\s+=([^\\n]+)",
            /* writer */ 		"show_name\\s+=([^\\n]+)",
            /* director */ 		"show_name\\s+=([^\\n]+)",
            /* synopsis */ 		"()"
        );
    }

}
