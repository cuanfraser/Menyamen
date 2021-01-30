The purpose of this module is to provide route-planning functionality for a role-playing game, 
which will be written in Python 3.8.0. We would like our town network class to be implemented 
with an array of objects of the class Town, which has the following fields: townName, a string 
representing the name of the town, townCharacters, a list of Character objects that represent 
characters in the town, and townsAdjacent, a list of the names (as strings) of towns that connect 
o this one directly. A Character class object has fields for its characterName (as a string), 
and currentTown, a string that is the name of the town the character is currently in.

The following functions are necessary to implement and have their input specified. The constructor 
function for our town network class takes a dictionary of key-value pairs:
“Towns”: an array of arrays of strings, where in each (inner) array the first string represents 
the name of the town, and the rest of the strings represent the names of towns adjacent to it. 
For each array in the outer array, a Town object will be added to the array in the town network, 
and the town constructor will pass the town’s name and adjacent towns.
“Characters”: an array of pairs of strings representing starting characters and the town they 
start in. (Reads as [“character name”, “town name”]). For each pair in the array, the place 
character function can be called to place the character in the town. Note that this must occur 
after the towns are created to minimize the chance of characters attempting to be placed in towns 
that haven’t been created yet.

The place character function will take as its input an array of strings of size two, representing 
the character name and town name. It will first iterate through our town network to ensure that 
the character name is new - otherwise it will either throw an error or move the existing character 
with that name to the town specified. If the character name is new, it will create a new Character 
object and add that character to the list of characters for the town specified. It will throw an 
error if the town name specified has not yet been created.

Lastly, there must exist a query for whether a character can reach a specified town without 
hitting any other characters. The input for this function will be the same as the place character 
function - that is, an array [“character name”, “destination name”]. We will leave the exact 
implementation of this function up to the engineers, but it must check that both the character and 
destination specified already exist within the town network, and then it can do some variation of 
search function from the town that the character is currently in to the destination node, treating 
any Town whose townCharacters list is not empty as an unreachable node, even if it is adjacent to 
a node in the path.

If you have any questions, contact our support team. Thank you!




