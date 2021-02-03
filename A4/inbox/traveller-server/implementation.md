# Alterations to the Provided Traveller-Server Implementation

    The provided implementation was quite thorough and explained in detail
the necessary requirements for the module. However, there was not an incomplete
definition of the data representation of the town network through the Construct
method. Without providing a representation of the connectivity of the Nodes
provided through the Construct method, there is no way to check the validity
of the provided simple graph, nor is there a way to calculate the safe passage
of a requested Character and their route. To remedy this issue, we have
proposed the following alterations to the implementation to make it more 
explicit and whole.

1. The Road Class
2. The Construct Method

## The Road Class
```
+------------+
| Road       |
+------------+
| Node to    |--+
| Node from  |--+
|            |  |
+------------+  |
                V
            +---------------+
            | Node          |
            +---------------+
            | String town   |
            | Character ch  |--+
            |               |  |
            +---------------+  |
                               V
                        +-------------+
                        | Character   |
                        +-------------+
                        | String name |
                        |             |
                        +-------------+
```
The Road class is a separate class constructed from two Nodes. These Roads represent undirected edges connecting the various Nodes (towns) within the town network.

This class comes with simple methods for construction of an instance of the Road class.

## The Construct Method
The provided implementation of Traveller server was as follows:
```java
// Places nodes to construct a network of edges
// Post: nodes that will form the town
public void Construct(N List<Node>);
```

We found this description to be lacking in explicitness as it mentions that edges are constructed from the List of Nodes provided to the Construct method, yet there is no detailed data representation that the List of Nodes should be formatted to in order to infer edges. Because of
this open interpretation, it appeared that edges were neglected, and thus
we chose to modify the statement slightly to take in both a List of Nodes to represent the towns of the network as well as a List of Roads detailing
the connections of the various Nodes. While we believe the same functionality could be achieved by providing a carefully formatted List of Roads, the added clarity of providing both a List of Nodes and a List of Roads makes the method easier to understand from a high level, improving overall readability both in code and in implementation documentation. The altered method that we implemented is as follows:
```java
// Places nodes and roads connecting various nodes within a simple graph
// to create the town network
// Pre: A valid List of Nodes and List of Roads has been received/constructed
// Post: The Traveller module has committed the List of Nodes and Roads to memory
public void Construct(N List<Node>, N List<Road>);
```

## Testing

Additionally, there were a few assumptions that we had to make in order to implement the specification. For this specification, we assume that characters and nodes will be created first. This list of roads is then used to construct the town network. Finally, characters will be placed into their respective nodes. An example of this can be found in the **TestTravellerServer.java** file.
    