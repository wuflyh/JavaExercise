package tbd.example;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


/**
 * A list of player names. Player objects are looked up by name using functions in the Player class.
 *
 * The Roster class should also manage grouping and provide support for checking and changing
 * the size of groups.
 */
public class Roster {

    /**
     * Parity of a player's group number.
     */
    public enum GroupParity {
        EVEN,
        ODD
    }

    /**
     * Utility function to check the parity of a group number
     *
     * @param group Group number to check.
     * @return Parity of the group.
     */
    public static GroupParity CheckParity(int group) {
        if (group % 2 == 1) {
            return GroupParity.ODD;
        } else {
            return GroupParity.EVEN;
        }
    }

    // Fields
    private ArrayList<String> players;
    // TODO - add any additional fields you need here


    /**
     * A convenience variable to make it easier to count stuff in a Roster stream operation
     */
    public int counter;

    /**
     * Default constructor
     *
     * Create ArrayList with initial capacity of 12.
     */
    public Roster() {
        players = new ArrayList<String>(12);
        counter = 0;
        // TODO
    }

    /**
     * Sort the roster ascending (A to Z) by name, using the natural lexicographic order.
     */
    public void sortByName() {
        // TODO
    }

    /**
     * Sort the roster descending (100 to 1) by rank
     */
    public void sortByRank() {
        // TODO
    }

    /**
     * Move a player from one Roster to another. If the rules exclude the player from being moved,
     * this method does nothing and fails silently.
     *
     * If needed, the group number of the named player will be updated to conform to the specified rules,
     * with respect to the 'to' Roster, by this method.
     *
     * @param name Player name to move from this Roster.
     * @param to Roster to move the player to.
     * @param rules The rules to apply to the move.
     * @throws NoSuchElementException If no Player object can be found for a name.
     * @throws IllegalArgumentException If name is already in the 'to' roster.
     */
    public void moveTo(String name, Roster to, Rules rules) throws NoSuchElementException, IllegalArgumentException  {
        // TODO
    }

    /**
     * Add a player to this Roster, according to the specified Rules.
     * If the rules exclude the player from being moved, this method does nothing and returns false.
     *
     * If needed, the group number of the named player will be updated to conform to the specified rules by this method.
     *
     * @param name Player name to add.
     * @param rules Rules to be applied to the addition of this player.
     * @return True if this roster was changed, false otherwise.
     * @throws NoSuchElementException If no Player object can be found for a name.
     * @throws IllegalArgumentException If name is already in this roster.
     */
    public boolean add(String name, Rules rules) throws NoSuchElementException, IllegalArgumentException {
        // TODO
        return false; // Correct this line of code and remove this comment
    }

    /**
     * Add a Roster to this Roster. If the rules exclude a player from being moved, the player is not
     * added to this roster.
     *
     * The group numbers of zero or more players in the 'from' roster will be changed according to the rules.
     *
     * @param from Roster to add.
     * @param rules Rules to be applied to the addition of all the players in the 'from' roster.
     * @return True if this roster was changed, false otherwise.
     */
    public boolean addAll(Roster from, Rules rules) {
        // TODO
        return false; // Correct this line of code and remove this comment
    }

    /**
     * Remove a player from this Roster. If the rules exclude a player from being moved, the player is not
     * removed from this roster and false is returned.
     *
     * The group number of the named player will not be changed during a remove operation.
     *
     * @param name Player name to be removed from this roster
     * @param rules Rules to be applied to the removal of this player
     * @return True if this roster was changed, false otherwise
     * @throws NoSuchElementException If no Player object can be found for a name, or no such name in the roster
     */
    public boolean remove(String name, Rules rules) throws NoSuchElementException {
        // TODO
        return false; // Correct this line of code and remove this comment
    }

    /**
     * Size of the specified group within this roster. If no players are assigned the specified
     * group number, returns 0.
     *
     * @param group Group number to use.
     * @return The number of players in the group.
     * @throws IllegalArgumentException If group is less than 0.
     */
    public int groupSize(int group) throws IllegalArgumentException {
        // TODO
        return 0; // Correct this line of code and remove this comment
    }

    /**
     * Use this method to account for the rule for maximum group size in your implementation of other methods that
     * need to add or move a player to this roster.
     *
     * If needed, it updates the group number of the specified player. If the player's group in this roster is full,
     * it will try the next highest group number, and so on until a group is found that, after adding the player
     * to that group, the size of the group will conform to the maximum group size rule. If no existing higher group
     * number is found, creates a new group number that is greater than the highest numbered group in the roster.
     * If the group number needed updating, returns the updated group number, otherwise, returns the player's original
     * group number.
     *
     * @param name The name of the player to update.
     * @param rules The rules to apply.
     * @return The group number of the player.
     * @throws NoSuchElementException If no Player object can be found for a name.
     */
    public int updateGroup(String name, Rules rules) {
        // TODO
        return 0; // Correct this line of code and remove this comment
    }

    // TODO - add any additional methods you may need here

    /* ====== Thin wrapper methods ======= */

    /**
     * Return size of Roster.
     *
     * @return Number of players in the roster.
     */
    public int size() {
        return players.size();
    }

    /**
     * Returns a stream for the underlying List of Player. DO NOT MODIFY the underlying List of Player collection
     * while processing the stream elements. Modification of individual elements in the collection is okay.
     *
     * @return Stream of player names.
     */
    public Stream<String> stream() {
        return players.stream();
    }

    /**
     * Get player name at index.
     *
     * @param i Index to get player at.
     * @return Player name.
     */
    public String get(int i) {
        return players.get(i);
    }

}
