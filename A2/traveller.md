# Traveller

**Goal:** Find a route for a specified character to reach a designated town without running into any other characters.  
**Language:** Java 8 (openjdk version "1.8.0_262")  
**Package Name:** Traveller 

The package Traveller will be  implemented in the language Java (version 1.8). The goal of this package is to find a route for a specified character to reach a designated town without running into any other characters. Our interface will consist of the class Node which will hold the String name of the town, and a Character. The Character class will contain a String with the characters name. Therefore, each town has a node with a specified name, and each character has a specified name. We also need to specify the node or town that the specified character starts at and the node that is that character's destination. The operations that we will need will include a ‘Size’ function, an ‘isEmpty?’ function, a ‘Construct’ function, and a ‘placeChar’ function. The ‘Size’ operation returns the amount of nodes in a given network of nodes, it returns 0 if there are none. This function will help to route a path because we know a character can only travel through nodes that are empty. The ‘isEmpty?’ function returns a boolean value whether or not the node has a character or not. So given a node this function specifies if there is a character on it or not. The function ‘Construct’ takes a list of Nodes and creates a network of nodes in order to track the paths available. The ‘placeChar’ function places a character at a node only if that node is empty, once placed the node is no longer empty. Important behavior to remember is that if a node is not empty then a character can not be placed at that node. In conclusion, using these operations our interface will be able to run a route operation that will correctly detect if a route is possible or not, i.e. whether a character is blocking the path or not.  


## Data
```
  +---------------+   
  | Node          |     
  +---------------+    
  | String town   |     
  | Character ch  |--+     
  |               |  |    
  +---------------+  |   
                     v   
              +-------------+   
              | Character   |   
              +-------------+   
              | String name |   
              |             |   
              +-------------+   
```
- The class Node contains: 
    - Character (character)
    - Town Name (String)
- The destination town
- The town of origin

## Operations
- Size
- isEmpty?
- Construct
- placeChar
- Route

```java
// Returns the amount of nodes in a network
// POST: Integer value of the amount of nodes, or 0 if none
public Integer Size(N List<Node>);
```

```java
// Returns a boolean statement whether or not a node has a character or not
// PRE: a given node must be present
// POST: whether or not the node has a character on it
public boolean isEmpty(N Node);
```

```java
// Places nodes to construct a network of edges
// POST: nodes that will form the town
public void Construct(N List<Node>);
```

```java
// Places character at a node
// PRE: that node is empty
// POST: node is not empty 
public void placeChar(T char, N node);
```
```java
// Returns a boolean value whether or not a route is found for a character.
// PRE: a given character, and the destination node 
// POST: boolean value whether or not the path is found
public boolean Route(T char, N node);
```

## Behaviour

- If a node is not empty a character can not be placed there 
- Route operation should be able to detect if no route is possible (i.e. cycles, non connected graph, characters blocking etc.)
