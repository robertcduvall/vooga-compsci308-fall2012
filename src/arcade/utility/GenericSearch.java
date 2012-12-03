package arcade.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Provides a wide variety of generic search algorithms
 * 
 * @author Patrick Royal
 * 
 */
public final class GenericSearch {

    /**
     * 
     */
    private GenericSearch() {
    }
    /**
     * Given a map associating objects (keys) with objects representing their
     * characteristics and a list of characteristics, return all objects that
     * match the given characteristics. Exact matches only.
     * 
     * @param map set of items and their associated characteristics
     * @param tags characteristics to match
     */
    public static List<Object> search (Map<Object, List<Object>> map,
            List<Object> tags) {
        List<Object> l = new ArrayList<Object>();
        for (Object o : map.keySet()) {
            boolean includeMe = true;
            for (int j = 0; j < tags.size(); j++) {
                if (!(tags.get(j) == null) &&
                        !tags.get(j).equals(map.get(o).get(j))) {
                    includeMe = false;
                }
            }
            if (includeMe) {
                l.add(o);
            }
        }
        return l;
    }

    /**
     * This handles a special case of search, where there is just one
     * characteristic to be matched.
     * 
     * @param map set of items and their associated characteristic
     * @param tag characteristic to match
     */
    public static List<Object> search (Map<Object, Object> map, Object tag) {
        List<Object> l = new ArrayList<Object>();
        for (Object o : map.keySet()) {
            if (map.get(o).equals(tag)) {
                l.add(o);
            }
        }
        return l;
    }

    /**
     * This handles searching with integers. In this case, the user can search
     * for objects associated with values greater than or less than a given
     * value.
     * 
     * @param map set of items and their associated value
     * @param tag value to be greater than or less than
     * @param b false indicates less than or equal, true indicates greater than
     *        or equal
     */
    public static List<Object> searchByNumber (Map<Object, Integer> map,
            Integer tag, boolean b) {
        List<Object> l = new ArrayList<Object>();
        for (Object o : map.keySet()) {
            if (!b) {
                if (map.get(o) <= tag) {
                    l.add(o);
                }
            }
            else {
                if (map.get(o) >= tag) {
                    l.add(o);
                }
            }
        }
        return l;
    }

    /**
     * This search function is used when the goal is to find the best matches to
     * a search query among a set of objects and their characteristics, not
     * necessarily the best match. It will return the item that matches the most
     * characteristics. If there is a tie, all of the best matches will be
     * returned.
     * 
     * @param map set of items and their associated characteristics
     * @param tags characteristics to match
     */
    public static List<Object> bestMatch (Map<Object, List<Object>> map,
            List<Object> tags) {
        List<Object> l = new ArrayList<Object>();
        Map<Object, Integer> scores = new HashMap<Object, Integer>();
        List<Object> keys = new ArrayList<Object>();
        for (Object o : map.keySet()) {
            keys.add(o);
        }
        for (Object o : keys) {
            int myScore = 0;
            for (int j = 0; j < tags.size(); j++) {
                if ((tags.get(j) == null) ||
                        tags.get(j).equals(map.get(o).get(j))) {
                    myScore++;
                }
            }
            scores.put(o, myScore);
        }
        for (Object o : keys) {
            if (l.size() == 0) {
                l.add(o);
            }
            else {
                if (scores.get(o) > scores.get(l.get(0))) {
                    l.clear();
                    l.add(o);
                }
                else if (scores.get(o) == scores.get(l.get(0))) {
                    l.add(o);
                }
            }
        }
        return l;
    }
}
