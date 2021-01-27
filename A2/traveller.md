# Traveller

**Goal:** Find a route for a specified character to reach a designated town without running into any other characters.  
**Language:** Java 8 (openjdk version "1.8.0_262")  
**Package Name:** Traveller  

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

## Operation

- Size
- isEmpty?
- Construct
- placeChar

```java
// Returns the amount of nodes in anetwork
// PRE: 
// POST: Integer value of the amount of nodes
// Ex: Node<N> n = new Traveller<N>();
//     N char = new Character();
//     n.placeChar(char);
//     assert(char, n.ch);
public void placeChar(T char);
```

```java
// Returns a boolean statement whether or not a node has a character or not
// PRE: a given node must be present
// POST: whether or not the node has a character on it
// Ex: Node<N> n = new Traveller<N>();
//     N char = new Character();
//     n.placeChar(char);
//     assert(char, n.ch);
public void placeChar(T char);
```

```java
// Places nodes to construct a network of edges
// PRE: nothing
// POST: nodes that will form the town
// Ex: Node<N> n = new Traveller<N>();
//     N char = new Character();
//     n.placeChar(char);
//     assert(char, n.ch);
public void Construct(List<Node>);
```

```java
// Places character at a node
// PRE: that node is empty
// POST: node is not empty 
// Ex: Node<N> n = new Traveller<N>();
//     N char = new Character();
//     n.placeChar(char);
//     assert(char, n.ch);
public void placeChar(T char);
```

## Behaviour

- If a node is not empty a character can not be placed there 
- Route operation should be able to detect if no route is possible (i.e. cycles, non connected graph, characters blocking etc.)
