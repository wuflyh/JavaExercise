package tbd.example;

import java.util.HashMap;

/**
 * Player object. Holds the name, rank and group number of a player. Also manages the global
 * pool of all players, regardless of which roster they may be in.
 */
public class Player {
    // Fields
    /** */
    public final String name;
    /**
     * Rank of player, from low of 1 to high of 100
     */
    public final int rank;
    /**
     * Group id number, 0 or greater
     */
    public final int group;

    // Statics
    private static HashMap<String, Player> Pool;


    static {
        Pool = new HashMap<String, Player>();
    }

    /**
     * Full constructor
     */
    protected Player(String aName, int aRank, int aGroup) {
        name = aName;
        rank = aRank;
        group = aGroup;
    }

    /**
     * Use this factory to create a Player object.
     *
     * @param name The player's name, MUST be unique across all Rosters unless Nobody.
     * @param group Must be greater than or equal to 0
     * @param rank From 1 to 100 inclusive, with 100 being highest/best
     * @throws IllegalArgumentException If name, rank or group are illegal values.
     */
    public static Player Factory(String name, int group, int rank) throws IllegalArgumentException {
        // sanity checks
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        if (Pool.containsKey(name)) {
            throw new IllegalArgumentException("Non-unique name");
        }
        if (rank < 1 || rank > 100) {
            throw new IllegalArgumentException("Bad rank");
        }
        if (group < 0) {
            throw new IllegalArgumentException("Bad group");
        }

        // Manufacture a player
        Player p = new Player(name, rank, group);
        Pool.put(name, p);
        return p;
    }

    /**
     * Lookup the player object by name from the player pool.
     *
     * @param name Name of the player to look up.
     * @return Player object.
     */
    public static Player LookupByName(String name) {
        return Pool.get(name);
    }

    /**
     * Replace the Player object with the specified name.
     *
     * @param name The name of the player whose object should be replaced.
     * @param rank The rank of the replacement player.
     * @param group The group number of the replacement player.
     */
    public static void Replace(String name, int rank, int group) {
        // First we have to remove the player from the Pool
        Pool.remove(name);
        // Then we factory a replacement player, which automatically adds it to the pool
        Factory(name, group, rank);
    }


}
