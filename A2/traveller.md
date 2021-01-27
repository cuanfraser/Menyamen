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
// Places character at a node
// PRE: that node is empty
// POST: node is not empty
// Ex: Node<T> n1 = new Stack<T>();
//     T elem = new T();
//     s.push(elem);
//     assert(elem, s.peek());
public void placeChar(T elem);
```

## Behaviour

- If a node is not empty a character can not be placed there 
- Route operation should be able to detect if no route is possible (i.e. cycles, non connected graph, characters blocking etc.)
