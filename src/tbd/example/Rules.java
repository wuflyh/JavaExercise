package tbd.example;

import java.util.HashSet;

/**
 * Rules that influence policy enforcement.
 *
 * The PolicyEnforcer and Roster classes will call methods on this class to accomplish the following:
 *
 * + Determine the maximum size of a group. Default value is 5.
 *
 * + Determine if named player is excluded from moving. Default is no players are excluded from moving. May
 * result in an arrange() failing with Status.Code.TOO_MANY_EXCLUSIONS.
 *
 * @see PolicyEnforcer
 * @see Roster
 */
public class Rules {
    private int maximumGroup;
    private HashSet<String> excludedNames;

    /**
     * Utility Rules object with no constraints, useful for Roster methods that require a rules object
     * when the constraints are not useful.
     */
    static public Rules NoRules;

    static {
        NoRules = new Rules();
        NoRules.setMaximumGroup(Integer.MAX_VALUE);
    }

    public Rules() {
        maximumGroup = 5;
        excludedNames = new HashSet<String>();
    }

    /** */
    public int getMaximumGroup() {
        return maximumGroup;
    }

    /** */
    public void setMaximumGroup(int maxGroup) {
        maximumGroup = maxGroup;
    }

    /**
     * Add a name to the exclusion rule.
     *
     * @param name Name to be excluded.
     */
    public void addExcludedName(String name) {
        excludedNames.add(name);
    }

    /**
     * Check if the specified name is excluded by this rule set.
     *
     * @param name Name to check.
     * @return True if the name is excluded, otherwise false.
     */
    public boolean isNameExcluded(String name) {
        return excludedNames.contains(name);
    }
}
