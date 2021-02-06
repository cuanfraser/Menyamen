The specification traveller.md we received was specified for Python 3.8.0 however we
decided to implement it in Java out of comfort. In implementing the specification in Java 
we found it very straight forward and were able to implement the server as specified. We 
created the classes; Town, Character, and TownNetwork. In TownNetwork rather than having 
dictionaries with the town name, and towns adjacent we created a Map to hold the key town name 
and the value of adjacent towns. We also used a Map for the town and corresponding character key and values.
For the function for placing Characters we had to make sure that the town existed as well as the
character which was not specified in the given specification. We then created nested for loops to relocate a character 
that was already on a town or else we created a new character to place. Furthermore, The specification left the query
design and implementation entirely up to us. Therefore, we utilized helper functions to check a town was on the network, it was empty,
and had adjacent neighbors. This allowed us to create a recursive function that found if a path was possible for a given character and destination town. 