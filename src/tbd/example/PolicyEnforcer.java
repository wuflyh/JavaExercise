package tbd.example;


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

        // TODO

        return Status.SUCCESS;
    }

    /* See Policy declaration comment for BY_RANK implementation requirements */
    private Status arrangeByRank() {
        // TODO

        return Status.SUCCESS;
    }

    /* See Policy declaration comment for BY_GROUP implementation requirements */
    private Status arrangeByGroup() {
        // TODO

        return Status.SUCCESS;
    }
}
