package tbd.example;

/**
 * Status codes returned by PolicyEnforcer methods.
 *
 * Even though the target output for this exercise (out.txt) only shows SUCCESS cases,
 * your code is still required to test for and report the appropriate status code for the specified conditions.
 *
 * @see Rules
 * @see PolicyEnforcer
 */
public enum Status {

    /**
     * Successful enforcement of policy.
     */
    SUCCESS,

    /**
     * Already arranged, nothing changed.
     */
    ALREADY_ARRANGED,

    /**
     * Failed due to too many exclusions, as defined by Rules.
     */
    TOO_MANY_EXCLUSIONS,

    /**
     * Ranks are too lopsided to meet goal of no more than 90 apart. For example, if the left roster
     * has five players of rank 1, and right roster has one player of rank 100, there is no arrangement
     * of 3 vs 3 or 4 vs 2 players whose rank sums that are no more than 90 apart.
     */
    RANKS_TOO_LOPSIDED

}
