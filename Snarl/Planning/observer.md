```java

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Specifies The interface for the Observer which allows a 
 * game to be viewed in progress as a local API (connects to * the game via Network)
 */
public interface Observer {

    private final List<Observer> observers = new ArrayList<>();

   /**
   * Void function that updates the game state taking an input string event
   *
   * @param event the state change that occurs which tells the observer to update 
   * the game render
   */
   void update(String event);

   /**
   * Void function that moves a player and updates the game state using  
   * input from the user given as a string event
   *
   * @param event the state change that occurs which tells * * the observer to    
   * update the game render
   * @throws IllegalArgumentException if the move update is invalide
   */
   void updateMove(String event);

   /**
   * Void function that updates the state of the key in the game render using the 
   * user input
   *
   * @param event the state change that occurs to the key which tells the 
   * observer to update the game render
   */
   void updateKey(String event);

   /**
   * Void function that updates the state of the exit and renders the game 
   * accordingly
   *
   * @param event the state change that occurs to the exit which tells the observer * to update the game render (move on to the next level)
   */
   void updateExit(String event);

   /**
   * Void function that updates the state of the game if a player is expelled 
   *
   * @param event the state change that occurs to expel a player
   */
   void updateExpel(String event);

   /**
   * Void function that starts the game with a given level inputed by the user
   *
   * @param event the level to be rendered
   */
   void renderGame(String event);

   /**
   * When a string is supplied from System.in, this method is called to notify
   * all observers of the event's occurence, when the update methods are invoked
   *
   * @param player Player whose name is going to validated 
   */
   void notifyObservers(String event){
        observers.forEach(observer -> observer.update(event));
    }

   /**
   * Void function that adds an Observer
   *
   * @param observer Observer to add
   */
   void addObserver(Observer observer) {
        observers.add(observer);
    }

   /**
   * Void function that takes in input and notifies the observers of events
   */
   void scanSystemIn();

}
```
