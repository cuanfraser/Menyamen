# Traveller-Server Integration Report 

The other team implemented each of our specified operations and classes within the Interface Traveller. 
They did a thorough and successful job, however they did modify our design a bit. 
While implementing our specification the other team came to the realization that 
we had neglected to properly detail the edges of each Node, or the Roads that create the town network.
By deviating a bit and filling in the blanks the other team created another class called Road which holds an undirected edge 
formed by two verified towns, one 'From' and the other 'To'.


## Modification: Road Class
```
+------------+
| Road       |
+------------+
| Node to    |  
| Node from  |  
|            |  
+------------+ 
```

We noticed that we could also have added these to and from nodes to each Node itself. Also the other team integrated these
Roads into our Construct method by updating the design recipe as: 

```java
// Places nodes and roads connecting various nodes within a simple graph
// to create the town network
// Pre: A valid List of Nodes and List of Roads has been received/constructed
// Post: The Traveller module has committed the List of Nodes and Roads to memory
public void Construct(N List<Node>, N List<Road>);
```
They first verify that each node and list of roads received is valid then saves the list of nodes and roads to memory. This is 
where we would have to adapt functions in our client to deal with roads. This would be a possible integration with minimal effort since
the idea of roads is similar to our notion of edges. Finally, in thinking about the improvements we could have made in our 
specification the biggest issue we had was not specifying the complete construct method and the edges within our network. 
