package tbd.example;

import java.util.*;

/**
 * Arrange rosters according to a specified policy.
 */
public class PolicyEnforcer {
    /**
     * Policy types.
     *
     * For all policies, excluded players must not be moved from their original rosters.
     *
     * BY_NUMBER - make each roster have the same number of players, with the difference in player counts
     * being no greater than 1. For example, 7 vs 8 is BY_NUMBER, 6 vs 9 is not.
     *
     * BY_RANK - the sum of the rank values of each player in a roster should be within 90 inclusive of each other,
     * while also ensuring that the difference in players counts is no greater than 2. For example, rank sums of
     * 280 vs 370, and player counts of 7 vs. 9 would be BY_RANK, but 190 vs 850 or 4 vs 9 would not. There
     * is NO requirement to minimize the difference in rank sums, nor the difference in number of players,
     * between 2 or more otherwise correct arrangements.
     *
     * BY_GROUP - move even numbered groups to the left roster, and odd numbered groups to the right.
     * If a player's group number is updated due to the maximum group size rule, but the new group number
     * is the wrong parity (e.g., even numbered in the right roster), do NOT move the player back to its original
     * roster. Instead, increment that player's group number until the maximum group size rule is satisfied AND
     * the group number is the correct parity. The resulting number of players in each roster is not important,
     * so 4 vs 9 would be fine, as long as groups 2 and 4 are in the left roster and groups 1 and 3
     * are in the right. Changing the group numbers of excluded players is allowed, but do not move them from
     * their original rosters.
     */
    public enum Policy {
        BY_NUMBER,
        BY_RANK,
        BY_GROUP
    }

    private Policy policy;
    private Roster leftRosterOriginal;
    private Roster rightRosterOriginal;
    private Roster leftRosterFinal;
    private Roster rightRosterFinal;
    private Rules rules;

    /**
     * Left result getter.
     *
     * @return The arranged left roster.
     */
    public Roster getLeftRosterFinal() {
        return leftRosterFinal;
    }

    /**
     * Right result getter.
     *
     * @return The arranged right roster.
     */
    public Roster getRightRosterFinal() {
        return rightRosterFinal;
    }

    /**
     * Construct an enforcer for a specific policy and a specific pair of rosters.
     *
     * @param aPolicy Policy to use to arrange the rosters.
     * @param someRules Rules to be applied during this arrangement.
     * @param leftOriginal Left-hand Roster, must be non-null and greater than 0 size. Ownership of object adopted
     *                     by this class.
     * @param rightOriginal Right-hand Roster, must be non-null and greater than 0 size. Ownership of object adopted
     *                      by this class.
     * @throws IllegalArgumentException If any argument is illegal, such as a roster being empty.
     */
    public PolicyEnforcer(Policy aPolicy, Rules someRules, Roster leftOriginal, Roster rightOriginal) throws IllegalArgumentException {

        // sanity checks
        if (someRules.getMaximumGroup() < 0){
            throw new IllegalArgumentException("someRules.getMaximumGroup() is < 0");
        }
        if (leftOriginal.size() <= 0) {
            throw new IllegalArgumentException("leftOriginal.size() is not > 0");
        }
        if (rightOriginal.size() <= 0) {
            throw new IllegalArgumentException("rightOriginal.size() is not > 0");
        }
        policy = aPolicy;
        rules = someRules;
        leftRosterOriginal = leftOriginal;
        rightRosterOriginal = rightOriginal;
    }

    /**
     * Arrange rosters according to policy. After the method returns, use getLeftRosterFinal and getRightRosterFinal
     * to access the arranged rosters.
     *
     * @return Status of the arrangement
     * @see Status
     */
    public Status arrange()  {
        switch (policy) {
            case BY_GROUP:
                return arrangeByGroup();

            case BY_RANK:
                return arrangeByRank();

            case BY_NUMBER:
            default:
                return arrangeByNumber();
        }
    }

    /* Utility method */
    private Pair<Roster> initBiggerSmaller() {
        /*
        Initialize final roster collections.
        Do all of your arrangement work with the final rosters.
        */
        leftRosterFinal = new Roster();
        rightRosterFinal = new Roster();

        leftRosterFinal.addAll(leftRosterOriginal, Rules.NoRules);
        rightRosterFinal.addAll(rightRosterOriginal, Rules.NoRules);

        // Guess the larger roster is the left one
        Roster bigger = leftRosterFinal;
        Roster smaller = rightRosterFinal;

        // Swap if we guessed wrong
        if (rightRosterFinal.size() > leftRosterFinal.size()) {
            bigger = rightRosterFinal;
            smaller = leftRosterFinal;
        }

        return new Pair<Roster>(bigger, smaller);
    }

    /* See Policy declaration comment for BY_NUMBER implementation requirements */
    private Status arrangeByNumber() {
        // The setup of this method is done for you ... start from TODO
        Pair<Roster> pair = initBiggerSmaller();
        Roster bigger = pair.first;
        Roster smaller = pair.second;

        // Sanity check if already arranged
        if ((bigger.size() - 1) <= smaller.size()) {
            return Status.ALREADY_ARRANGED;
        }

        // k - the number of players in bigger Roster
        // ind - the index to iterate throught the players in the bigger Roster which changes meanwhile
        int k = bigger.size();
        int ind = 0;
        while ((k > 0) && (bigger.size() -1 > smaller.size())) {
            String candidate = bigger.get(ind);
            if (bigger.remove(candidate, rules)) {
                // move one player from the bigger to the smaller Roster
                // afterwards the index will point to the next candidate
                smaller.add(candidate, rules);
            } else {
                // if this player does not qualify, inc the index
                ind += 1;
            }
            k -= 1;
        }

        if ((bigger.size() - 1) <= smaller.size()) {
            return Status.SUCCESS;
        } else {
            return Status.TOO_MANY_EXCLUSIONS;
        }
    }

    /* See Policy declaration comment for BY_RANK implementation requirements */
    private Status arrangeByRank() {
        leftRosterFinal = new Roster();
        rightRosterFinal = new Roster();

        // freeList are the free players that can be allot to either of the two Rosters
        ArrayList<String> freeList = new ArrayList<String>();
        // rankList are the free players' ranks
        ArrayList<Integer> rankList = new ArrayList<Integer>();

        // reservedLeft are those players that stick to left Roster
        ArrayList<String> reservedLeft = new ArrayList<String>();

        // reservedRight are those players that stick to right Roster
        ArrayList<String> reservedRight = new ArrayList<String>();

        //sum - sum of all player ranks
        //fsum - the sum of free player ranks
        //rsum - the sum of player in reservedLeft ranks
        int sum = 0, fsum = 0, rsum = 0;

        // calculate the metrics - sum, fsum and rsum
        for (int i = 0; i < leftRosterOriginal.size(); i++) {
            String cur = leftRosterOriginal.get(i);
            Player p = Player.LookupByName(cur);
            if (rules.isNameExcluded(cur)) {
                reservedLeft.add(cur);
                rsum += p.rank;
            } else {
                freeList.add(cur);
                rankList.add(p.rank);
                fsum += p.rank;
            }
            sum += p.rank;
        }


        for (int i = 0; i < rightRosterOriginal.size(); i++) {
            String cur = rightRosterOriginal.get(i);
            Player p = Player.LookupByName(cur);
            if (!rules.isNameExcluded(cur)) {
                freeList.add(cur);
                rankList.add(p.rank);
                fsum += p.rank;
            } else {
                reservedRight.add(cur);
            }
            sum += p.rank;
        }

        // minVal - the minimum total rank either Roster should have
        // minCnt - the minimum number of players either Roster should have
        int minVal = (sum-90+1)/2;
        int minCnt = (leftRosterOriginal.size() + rightRosterOriginal.size() - 2 + 1)/2;

        // adjusted minVal and minCnt for the left Roster
        minVal -= rsum;
        minCnt -= reservedLeft.size();


        // we split the players into two Rosters
        // temporarily into reservedLeft and reservedRight list
        // by adding players from freelist to left Roster such that
        // the rank sum of the added players in range [minVal, minVal + 90]
        // the count of the added players in range [minCnt, minCnt + 2]

        if (minVal+90 < 0 || minVal > fsum || minCnt+2 < 0 || minCnt > freeList.size()) {
            // no solution
            return Status.TOO_MANY_EXCLUSIONS;
        }

        // allot the players from the list of free players to the left Roster
        // with the rank and count requirements
        ArrayList<String> addonList = allot(freeList, rankList, minVal, minCnt);
        if (addonList == null) {
            // allot failed
            return Status.TOO_MANY_EXCLUSIONS;
        }

        // now reservedLeft and reservedRight contains the players for the two Rosters
        for (String s: freeList) {
            if (addonList.contains(s)) {
                reservedLeft.add(s);
            } else {
                reservedRight.add(s);
            }
        }

        // fill up the left and right final Rosters and return
        for (String s: reservedLeft) {
            leftRosterFinal.add(s, Rules.NoRules);
        }
        for (String s: reservedRight) {
            rightRosterFinal.add(s, Rules.NoRules);
        }
        return Status.SUCCESS;
    }

    /**
     * Allot the players from the free player list to the left Roster
     * @param  freeList - the list of players to be alloted
     * @param  rankList - the ranks for the free players
     * @param minVal - the minimum value of rank the alloted players should sum up to.
     * @param minCnt - the minimum number of players that should be alloted.
     * @return list of players for the left Roster.
     */
    private ArrayList<String> allot(ArrayList<String> freeList, ArrayList<Integer> rankList, int minVal, int minCnt) {
        ArrayList<String> res = new ArrayList<String>();
        HashMap<Integer, ArrayList<ArrayList<String>>> hm = new HashMap<Integer, ArrayList<ArrayList<String>>>();
        // initialize
        ArrayList<String> empty = new ArrayList<String>();
        ArrayList<ArrayList<String>> emptyList = new ArrayList<ArrayList<String>>();
        emptyList.add(empty);
        // set emptyList only for sum 0
        hm.put(0, emptyList);

        // dynamic programming

        for (int i = 0; i < freeList.size(); i++) {
            int curRank = rankList.get(i);
            for (int k=minVal+90; k>0; k--) {
                ArrayList<ArrayList<String>> newVal = new ArrayList<ArrayList<String>>();
                if (hm.containsKey(k)) {
                    newVal.addAll(hm.get(k));
                }
                int prev = k - curRank;
                if (hm.containsKey(prev)) {
                    ArrayList<ArrayList<String>> prevList = hm.get(prev);
                    for (ArrayList<String> x:prevList) {
                        ArrayList<String> newItem = new ArrayList<String>();
                        newItem.addAll(x);
                        newItem.add(freeList.get(i));
                        newVal.add(newItem);
                    }
                }

                if (newVal.size() > 0) {
                    hm.put(k, newVal);
                }
            }
        }

        // we select a qualified list to return
        for (int k = minVal; k<=minVal+90; k++) {
            if (hm.containsKey(k)) {
                ArrayList<ArrayList<String>> ans = hm.get(k);
                for (ArrayList<String> x: ans) {
                    if (x.size() >= minCnt && x.size() <= minCnt+2) {
                        // x could be empty at extream case
                        return x;
                    }
                }
            }
        }

        return null;
    }

    private void processOriginalRoster(Roster r) {
        Roster home;
        for (int i = 0; i < r.size(); i++) {
            String cur = r.get(i);
            Player p = Player.LookupByName(cur);
            Roster.GroupParity par = Roster.CheckParity(p.group);
            if (par == Roster.GroupParity.EVEN) {
                home = leftRosterFinal;
            } else {
                home = rightRosterFinal;
            }
            home.addWithParity(cur, rules, par);
        }
    }
    /* See Policy declaration comment for BY_GROUP implementation requirements */
    private Status arrangeByGroup() {

        leftRosterFinal = new Roster();
        rightRosterFinal = new Roster();
        processOriginalRoster(leftRosterOriginal);
        processOriginalRoster(rightRosterOriginal);
        return Status.SUCCESS;
    }
}
