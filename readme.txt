notes - 

1. Please refer to source code for the implementatoin details.

2. arrangeByNumber and arrangeByGroup returns the same results as out.txt, but not arrangeByRank. As I implemented 
the arrangeByRank using dyanamic programming which could find all the possible arranges, from which I chooose the first one 
that has the allowed minimum rank sum. 

3. the code template presents ways of decouple the interface and implementations, yet the requirements are not very clear and I can only contemplate through the out.txt and source code. The code might work for the current test settings, but could have problems in other senarios. e.g. when one player should be assigned to two Rosters. 

4. the meta data of players are maintained in the Player factory. so running arrange operations on one Roster that changes the rank and group could disrupt the enforced rule on another Roster.

Thanks!


