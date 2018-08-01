package tbd.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
This CLI program is the driver for the Java code you will write as part of this exercise.
The Exercise() function uses classes and methods you will implement in order to arrange
rosters of players according to the specified policies and rules. The Main class and the
Exercise function are 100% implemented. You are advised to use them as is, except for the
changes required to generate results.txt, as described in the instructions. Making temporary changes
for the sake of debugging is fine, as long as the debugging code doesn't result in output
that would interfere with evaluating the correctness of your implementation.

The entire program is single-threaded, so synchronization is not important.

Rosters are arranged in pairs. The rosters in the pair are arbitrarily referred to as "left" and the "right".
The leftness or rightness is not relevant to the exercise, they could have been A and B or red and blue.

The Exercise function begins by initializing the starting roster pair. It then initializes the rules
to be used for this exercise. Finally, it arranges the rosters by each of the three policies, one
after the other, writing the results to System.out.
*/

public class Main {
    private static int LeftRankSum = 0;
    private static int RightRankSum = 0;

    public static void main(String[] args) {
        try {
            Exercise();
        } catch (RuntimeException e) {
            System.err.printf("%n%nERROR: %s%n", e);
        }
    }

    public static void Exercise() {
        System.out.println("START OF POLICY ENFORCED ARRANGEMENTS\n");
        // Initialization
        Rules myRules = new Rules(); // Don't define excluded players yet, do that after building initial rosters
        // Create initial rosters
        Roster left = new Roster();
        left.add(Player.Factory("Donna", 2, 11).name, myRules);
        left.add(Player.Factory("Alice", 3, 23).name, myRules);
        left.add(Player.Factory("Beth", 3,34).name, myRules);
        left.add(Player.Factory("Gemma", 3,45).name, myRules);

        Roster right = new Roster();
        right.add(Player.Factory("Able", 3,56).name, myRules);
        right.add(Player.Factory("Baker", 3, 67).name, myRules);
        right.add(Player.Factory("Charlie", 1,100).name, myRules);
        right.add(Player.Factory("Del", 1,100).name, myRules);
        right.add(Player.Factory("Edward", 3,78).name, myRules);
        right.add(Player.Factory("Harry", 2,89).name, myRules);
        right.add(Player.Factory("Ian", 2, 2).name, myRules);
        right.add(Player.Factory("John", 2,31).name, myRules);
        right.add(Player.Factory("Frank", 2,42).name, myRules);
        right.add(Player.Factory("Gary", 2,53).name, myRules);


        /*
        Define Rules as follows:
        + Leave maximum group size with default value
        + Exclude the the specified players from being moved
        */
        List<String> excluded = Arrays.asList("Charlie", "Del", "Donna");
        excluded.forEach((vip) -> myRules.addExcludedName(vip));

        // Write out the original rosters
        System.out.println("ORIGINAL LEFT");
        left.sortByName();
        left.counter = 1;
        left.stream()
            .forEach((name) -> {
                Player l = Player.LookupByName(name);
                System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                        left.counter++, l.name, l.group, l.rank);
        });

        System.out.println("ORIGINAL RIGHT");
        right.sortByName();
        right.counter = 1;
        right.stream()
             .forEach((name) -> {
                Player r = Player.LookupByName(name);
                System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                        right.counter++, r.name, r.group, r.rank);
        });

        // Write out the rules
        System.out.println("RULES");
        System.out.printf("Maximum group size: %d%n", myRules.getMaximumGroup());
        System.out.print("Excluded: ");
        System.out.println(excluded.stream().collect(Collectors.joining(", ")));

        System.out.println("-----");

        /* Apply BY_NUMBER policy first */

        // Make copies of the starting rosters, since PolicyEnforcer adopts ownership of the rosters
        Roster left1 = new Roster();
        Roster right1 = new Roster();
        left1.addAll(left, Rules.NoRules);
        right1.addAll(right, Rules.NoRules);
        PolicyEnforcer byNumberEnforcer = new PolicyEnforcer(PolicyEnforcer.Policy.BY_NUMBER, myRules, left1, right1);

        // Arrange by number
        System.out.printf("%nBY_NUMBER%n");
        Status status = byNumberEnforcer.arrange();
        System.out.println("Status: " + status);

        System.out.println("-----");

        // Write output
        final Roster leftByNumber = byNumberEnforcer.getLeftRosterFinal();
        final Roster rightByNumber = byNumberEnforcer.getRightRosterFinal();

        System.out.println("LEFT");
        leftByNumber.sortByName();
        leftByNumber.counter = 1;
        leftByNumber.stream()
                    .forEach((name) -> {
                        Player l = Player.LookupByName(name);
                        System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                                leftByNumber.counter++, l.name, l.group, l.rank);
                    });

        System.out.println("RIGHT");
        rightByNumber.sortByName();
        rightByNumber.counter = 1;
        rightByNumber.stream()
                     .forEach((name) -> {
                        Player r = Player.LookupByName(name);
                        System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                                rightByNumber.counter++, r.name, r.group, r.rank);
                     });

        /* Apply BY_RANK policy next */

        // Make copies of the starting rosters, since PolicyEnforcer adopts ownership of the rosters
        Roster left2 = new Roster();
        Roster right2 = new Roster();
        left2.addAll(left, Rules.NoRules);
        right2.addAll(right, Rules.NoRules);
        PolicyEnforcer byRankEnforcer = new PolicyEnforcer(PolicyEnforcer.Policy.BY_RANK, myRules, left2, right2);

        // Arrange by rank
        System.out.printf("%nBY_RANK%n");
        status = byRankEnforcer.arrange();
        System.out.println("Status: " + status);

        System.out.println("-----");

        // Write output
        final Roster leftByRank = byRankEnforcer.getLeftRosterFinal();
        final Roster rightByRank = byRankEnforcer.getRightRosterFinal();

        System.out.println("LEFT");
        leftByRank.sortByName();
        leftByRank.counter = 1;
        leftByRank.stream()
                  .forEach((name) -> {
                        Player l = Player.LookupByName(name);
                        System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                                leftByRank.counter++, l.name, l.group, l.rank);
                        LeftRankSum += l.rank;
                  });

        System.out.println("RIGHT");
        rightByRank.sortByName();
        rightByRank.counter = 1;
        rightByRank.stream()
                   .forEach((name) -> {
                        Player r = Player.LookupByName(name);
                        System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                                rightByRank.counter++, r.name, r.group, r.rank);
                        RightRankSum += r.rank;
                   });

        // Final rank totals
        System.out.printf("Rank sum totals: [%d/%d]%n", LeftRankSum, RightRankSum);

        /* Apply BY_GROUP policy last */

        // Make copies of the starting rosters, since PolicyEnforcer adopts ownership of the rosters
        Roster left3 = new Roster();
        Roster right3 = new Roster();
        left3.addAll(left, Rules.NoRules);
        right3.addAll(right, Rules.NoRules);
        PolicyEnforcer byGroupEnforcer = new PolicyEnforcer(PolicyEnforcer.Policy.BY_GROUP, myRules, left3, right3);

        // Arrange by group
        System.out.printf("%nBY_GROUP%n");
        status = byGroupEnforcer.arrange();
        System.out.println("Status: " + status);

        System.out.println("-----");

        // Write output
        final Roster leftByGroup = byGroupEnforcer.getLeftRosterFinal();
        final Roster rightByGroup = byGroupEnforcer.getRightRosterFinal();

        System.out.println("LEFT");
        leftByGroup.sortByName();
        leftByGroup.counter = 1;
        leftByGroup.stream()
                   .forEach((name) -> {
                        Player l = Player.LookupByName(name);
                        System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                                leftByGroup.counter++, l.name, l.group, l.rank);
                   });

        System.out.println("RIGHT");
        rightByGroup.sortByName();
        rightByGroup.counter = 1;
        rightByGroup.stream()
                    .forEach((name) -> {
                        Player r = Player.LookupByName(name);
                        System.out.printf("%d. Name: %s, Group: %d, Rank: %d%n",
                                rightByGroup.counter++, r.name, r.group, r.rank);
                    });
    }
}
