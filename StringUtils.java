package jd_crawler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class StringUtils {

    public static float showScore(String showName, String text) {
        showName = normalize(showName).trim() + " ";
        showName = showName.replace(" ", "    ");
        if (showName.equals(" ") )
            return 0f;
        List<String> parsed = split(text);
        float maxScore = 0f;
        int k = 0;
        float l = showName.length();
        while(!parsed.isEmpty()) {
            String s = merge(parsed);
            s = s.replace(" ", "    ");
            int[] pref = prefixDistance(showName, s);
            float d = pref[0];
            d = (1+d*d*d)/(float)Math.sqrt(l) + (float)k/2;
            maxScore = Math.max(maxScore, 1f/(1+d));
            if(maxScore > 0.7) break;
            parsed.remove(0);
            k++;
        }
        return maxScore;
    }

    public static List<String> split(String s) {
        String[] ss = s.replaceAll("[^a-zA-Z0-9']", " ").toLowerCase().split(" ");
        List<String> res = new LinkedList<String>();
        for(String block : ss) {
            res.add(block);
        }
        return res;
    }

    public static String merge(Collection<String> c) {
       StringBuilder b = new StringBuilder();
       for(String s : c) b.append(s).append(" ");
       return b.toString();
    }

    public static String normalize(String s) {
        return s.replaceAll("[^a-zA-Z0-9']", " ").replaceAll(" +", " ").toLowerCase();
    }

    public static int[] prefixDistance(String s, String t) {

        int n = s.length(); // length of s
        int m = t.length(); // length of t


        int p[] = new int[n + 1]; //'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }
        int prefMin = Integer.MAX_VALUE;
        int prefJ = 0;
        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            if(d[n] < prefMin) {
                prefMin = d[n];
                prefJ = j;
            }
            prefMin = Math.min(d[n],prefMin);
            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return new int[] { prefMin, prefJ };
    }
}
