package jd_crawler;

import java.util.Collection;

public class WikipediaProvider extends AbstractCrawler {

    public WikipediaProvider(){
        super(
                    // Website
              "http://en.wikipedia.org",
                    // Entrypoint
              "/wiki/List_of_television_programs_by_name",
                    // Regex for the tvshows list of the entrypoint
              "<li><i><a href=\"/wiki/([^\"]+)\"",
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
