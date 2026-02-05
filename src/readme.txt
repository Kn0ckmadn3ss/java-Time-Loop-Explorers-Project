Project Summary
This project is a text-based adventure game where a group of explorers try to survive in a mysterious world by collecting gems, earning treasure, and encountering random events. Each class has a specific responsibility, and generic structures make it easy to implement.

CLASS DESCRIPTIONS
1- Action
Purpose: Represents each action that occurs during the game (event outcome, gem usage, treasure acquisition, etc.).
description: Textual description of the event.
Usage: Generated within the GameEngine as a result of an event or player actions and recorded by the ActionLog/GameState.

2- Event
Purpose: Represents random events that occur in the game.
Attributes:
Good events: Gain health or give treasure (example: "Energy Drink", "Health Kit").
Bad events: Lose health or destroy items (example: "Poison Box", "Predator Attack").

3 - Explorer
Purpose: Represents the explorer character in the game.
Properties: name, hp, treasure: The character's basic properties.
"stones: A GenericLinkedList<T> that holds the stones the explorer carries."
Methods: takeDamage(), heal(), addTreasure(), addStone(), useStone().
Description: The Explorer class is the primary entity in the game world. Throughout the game, the Event class uses its methods to change the character's state.

4 - GameEngine
Purpose: The game's main control center.
Role:
Manages the player's turn.
Influences events.
Saves and updates the game state (GameState).
Controls the game's win/lose conditions.
Classes Used: Explorer, Event, GameState, GenericQueue

IMPLEMENTATION:
The player enters the next room.
The GameEngine selects a random event.
The event affects the Explorer.
The action is recorded, and the GameState is updated.
The turn moves to the next Explorer.

5- GameState
Purpose: Stores the current state of the game.
Content:
HP and treasure information for all Explorers,
Current room and tour information,
Action history.
Usage: Used to return to the past with time stones (e.g., Paradox Stone) or with flashback features.

6- GenericCircularLinkedList<T>
Purpose: Allows players to play sequentially in an infinite loop.
Usage: "Used to manage the 'Reverse Tour Order' event that occurs in rooms."
Properties: add(T data), getNext()…

7- GenericLinkedList<T>
Purpose: A general-purpose one-way linked list structure.
Usage: Other structures are built upon this class.

8- GenericLinkedListStack<T>
Purpose: Implements stack (LIFO) logic in GenericLinkedList.
Usage: Manages the explorer's bag of stones.
Methods: push(T data), pop(), peek(), isEmpty()

9- GenericQueue<T>
Purpose: Implements FIFO (First In, First Out) logic.
Usage: Used to keep track of the order of solving the "Echo Stone" puzzle (1, 2, 3...). As explorers use stones, they are added to this queue, and the order is checked.

10- Node<T>
Purpose: The basic node structure of generic linked data structures.
Properties:  data: Data held in the node.  next: Reference to the next node.
Usage: All generic lists, stacks, and queue structures are constructed using this class.

11- PlayGame (Main)
Purpose: Game starter.
Task: Creates the GameEngine object. ---- Starts the game loop.
Methods:
main(String[] args)
startGame() — Starts the main loop of the game.

GAME FLOW
Explorers are created and queued (GenericQueue or GenericCircularLinkedList).
The GameEngine starts the game.
Each turn, an explorer encounters a new Event.
The Action is recorded as a result of the event, and the GameState is updated.
The loop continues as long as the explorer remains alive.
The game ends if all explorers die or the target treasure is reached.
