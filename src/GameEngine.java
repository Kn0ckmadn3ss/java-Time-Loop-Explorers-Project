package thetimeloopexplorers1;

import java.util.Scanner;


public class GameEngine {

    private GenericCircularLinkedList<Explorer> turnOrder;
    private Explorer currentExplorer;
    private int currentRoom;
    private int expectedStone;
    private boolean gameLost;
    private boolean gameWon;

    private GenericQueue<Integer> puzzleLog;

    private GenericLinkedList<GenericLinkedList<Action>> timelineHistory;
    private GenericLinkedList<Action> currentTimeline;
    private Scanner scanner;
    private GenericLinkedList<Explorer> allExplorers;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.gameLost = false;
        this.gameWon = false;
    }

    public void start() {
        setupGame();

        while (!gameLost && !gameWon) {
            checkGameStatus();
            if (gameLost || gameWon) {
                break;
            }

            currentExplorer = turnOrder.getNext();

            if (!currentExplorer.isAlive()) {
                System.out.println(currentExplorer.getName() + " dead, pass to next explorer...");
                continue;
            }

            displayTurnInfo();
            takePlayerTurn();
        }

        if (gameWon) {
            System.out.println("=====================================================");
            System.out.println("Congratulations! You used the Echo Stones in the correct order!");
            System.out.println("1 -> 2 -> 3... You are free of the time loop!");
            System.out.println("=====================================================");
        }
        if (gameLost) {
            System.out.println("\n***************************************************");
            System.out.println("ALL EXPLORERS ARE DEAD! YOU'VE LOST THE GAME.");
            System.out.println("!!!GAME OVER!!!");
            System.out.println("***************************************************");
        }
    }

    private void setupGame() {
        System.out.println("--- The Time Loop Explorers ---");
        System.out.print("Enter your number of explorers. (Number of players) (3-5): ");
        int n = Integer.parseInt(scanner.nextLine());
        if (n < 3) {
            n = 3;
        }
        if (n > 5) {
            n = 5;
        }

        turnOrder = new GenericCircularLinkedList<>();
        allExplorers = new GenericLinkedList<>();
        puzzleLog = new GenericQueue<>();
        timelineHistory = new GenericLinkedList<>();
        currentTimeline = new GenericLinkedList<>();
        timelineHistory.insertLast(currentTimeline);

        for (int i = 1; i <= n; i++) {
            Explorer e = new Explorer("Explorer " + i);
            turnOrder.add(e);
            allExplorers.insertLast(e);
        }

        currentRoom = 1;
        expectedStone = 1;
        System.out.println(n + " explorer enters ancient ruin!!!");
    }

    private void displayTurnInfo() {
        System.out.println("=============================================");
        System.out.println("Room: " + currentRoom + " || Next Explorer: " + currentExplorer.getName());
        System.out.println(currentExplorer.toString());
        currentExplorer.printInventory();
    }

    private void takePlayerTurn() {
        System.out.println("What do you want to do?");
        System.out.println("1: Move (Pass to next Room)");
        System.out.println("2: Use Stone");

        String choice = scanner.nextLine();

        GameState preActionState = new GameState(currentRoom, expectedStone, turnOrder, puzzleLog);
        Action action = null;

        if (choice.equals("1")) {
            currentRoom++;
            System.out.println(currentExplorer.getName() + " moved to the " + currentRoom + ". room.");

            action = new Action("Explorer " + " moved to room " + currentRoom, currentExplorer, preActionState);

            Event event = Event.generateRandomEvent(currentRoom);
            applyEvent(event);

        } else if (choice.equals("2")) {
            System.out.print("Select the type of stone you will use: ");
            String stoneName = scanner.nextLine();

            if (!currentExplorer.hasStone(stoneName)) {
                System.out.println("You don't have this stone! Next Explorer.");
                action = new Action("Explorer " + " want to use " + stoneName + " [failed request!]", currentExplorer, preActionState);
            } else {
                currentExplorer.useStone(stoneName);

                action = new Action(" Used" + stoneName, currentExplorer, preActionState);

                if (stoneName.equals("ParadoxStone")) {
                    handleParadoxStone(preActionState);
                    return;

                } else if (stoneName.startsWith("EchoStone-")) {
                    try {

                        int index = stoneName.indexOf('-');
                        String numberString = stoneName.substring(index + 1);
                        int stoneNum = Integer.parseInt(numberString);

                        System.out.println(currentExplorer.getName() + " used " + stoneName);
                        checkPuzzle(stoneNum);

                    } catch (Exception e) {
                        System.out.println("INVALID STONE TYPE!!!.");
                    }
                }
            }
        } else {
            System.out.println("You made an invalid selection");
            action = new Action("Invalid action", currentExplorer, preActionState);
        }

        if (action != null) {
            currentTimeline.insertLast(action);
        }
    }

    private void applyEvent(Event event) {
        System.out.println("Event: " + event.getDescription());

        if (event.getAffectedStone() != null) {
            currentExplorer.addStone(event.getAffectedStone());
        }

        int hpChange = event.getHpChange();
        if (hpChange > 0) {
            currentExplorer.heal(hpChange);
        } else if (hpChange < 0) {

            currentExplorer.takeDamage(hpChange * -1);
        }

        int treasureChange = event.getTreasureChange();
        if (treasureChange > 0) {
            currentExplorer.addTreasure(treasureChange);
        }

        if (event.isReverseEvent()) {
            turnOrder.reverse();
        }
    }

    private void checkPuzzle(int stoneNumber) {
        if (stoneNumber == expectedStone) {
            System.out.println("The stone adapted to the spell! (" + stoneNumber + ")");
            puzzleLog.enqueue(stoneNumber);
            expectedStone++;

            if (expectedStone > 3) {
                gameWon = true;
            }
        } else {
            System.out.println("WRONG STONE! The spell was resisted and reset!");
            puzzleLog.clear();
            expectedStone = 1;
        }
    }

    private void checkGameStatus() {
        int deadExplorers = 0;
        Node<Explorer> current = allExplorers.head;
        while (current != null) {
            if (!current.data.isAlive()) {
                deadExplorers++;
            }
            current = current.next;
        }

        if (deadExplorers == allExplorers.size()) {
            gameLost = true;
        }
    }

    private void handleParadoxStone(GameState previousActionState) {
        System.out.println("/=/=/=/=/=/=/=/=/ PARADOX STONE ACTIVE /=/=/=/=/=/=/=/=/");
        System.out.println("1: View Timelines");
        System.out.println("2: Create a New Path");
        System.out.println("Cancel");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("/=/=/=/=/=/=/=/=/ ALL TİMELİNES /=/=/=/=/=/=/=/=/");
            Node<GenericLinkedList<Action>> timelineNode = timelineHistory.head;
            int timelineIndex = 0;
            while (timelineNode != null) {
                System.out.println("[ Time Line " + timelineIndex + " ]" + (timelineNode.data == currentTimeline ? " (Active)" : " (Branch)"));
                timelineNode.data.displayList(false);

                timelineNode = timelineNode.next;
                timelineIndex++;
            }

        } else if (choice.equals("2")) {
            System.out.println("/=/=/=/=/=/=/=/=/ Current TimeLine's Actions /=/=/=/=/=/=/=/=/");
            System.out.println("Which action would you like to return to? (Enter sequence number): ");

            currentTimeline.displayList(true);

            System.out.print("Selection [NUMBER!!!]:  ");
            int actionIndex = Integer.parseInt(scanner.nextLine());

            if (actionIndex < 0 || actionIndex >= currentTimeline.size()) {
                System.out.println("Invalid action number.");
                return;
            }

            System.out.println("Let time flow backwards!!!");

            Action targetAction = currentTimeline.get(actionIndex);
            GameState restoredState = targetAction.getPreActionState();

            GenericLinkedList<Action> newTimeline = currentTimeline.copyUntiltoCurrentIndex(actionIndex);

            timelineHistory.insertLast(newTimeline);
            this.currentTimeline = newTimeline;

            restoreFromState(restoredState);

            System.out.println("Timeline Branched!");
            System.out.println("You are now in " + this.currentRoom + ". room. İt is " + this.currentExplorer.getName() + "'s turn.");
        }
    }

    private void restoreFromState(GameState state) {
        this.currentRoom = state.getCurrentRoom();
        this.expectedStone = state.getExpectedStone();
        this.puzzleLog = state.getPuzzleLogState().copy();

        allExplorers.clear();
        Node<Explorer> rebuilder = state.getExplorersState().head;
        while (rebuilder != null) {
            allExplorers.insertLast(rebuilder.data.copy());
            rebuilder = rebuilder.next;
        }

        this.turnOrder = new GenericCircularLinkedList<>();
        Node<String> turnNameNode = state.getTurnOrderNames().getHeadNode();

        if (turnNameNode == null) {
            return;
        }

        do {
            String explorerName = turnNameNode.data;
            Node<Explorer> findRef = allExplorers.head;
            while (findRef != null) {
                if (findRef.data.getName().equals(explorerName)) {
                    this.turnOrder.add(findRef.data);
                    break;
                }
                findRef = findRef.next;
            }
            turnNameNode = turnNameNode.next;
        } while (turnNameNode != state.getTurnOrderNames().getHeadNode());

        this.currentExplorer = this.turnOrder.top();
    }
}
