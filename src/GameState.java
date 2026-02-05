package thetimeloopexplorers1;


public class GameState {

    private int currentRoom;
    private int expectedStone;

    private GenericLinkedList<Explorer> explorers;
    private GenericCircularLinkedList<String> turnOrderNames;
    private GenericQueue<Integer> puzzleLog;

    public GameState(int room, int expected, GenericCircularLinkedList<Explorer> currentTurnOrder, GenericQueue<Integer> currentPuzzleLog) {
        this.currentRoom = room;
        this.expectedStone = expected;

        this.explorers = new GenericLinkedList<>();

        Node<Explorer> currentExplorerNode = currentTurnOrder.getHeadNode();
        if (currentExplorerNode != null) {
            do {
                this.explorers.insertLast(currentExplorerNode.data.copy());
                currentExplorerNode = currentExplorerNode.next;
            } while (currentExplorerNode != currentTurnOrder.getHeadNode());
        }

        this.turnOrderNames = new GenericCircularLinkedList<>();
        Node<Explorer> currentTurnNode = currentTurnOrder.getHeadNode();
        if (currentTurnNode != null) {
            do {
                this.turnOrderNames.add(currentTurnNode.data.getName());
                currentTurnNode = currentTurnNode.next;
            } while (currentTurnNode != currentTurnOrder.getHeadNode());
        }

        this.puzzleLog = currentPuzzleLog.copy();
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public int getExpectedStone() {
        return expectedStone;
    }

    public GenericLinkedList<Explorer> getExplorersState() {
        return explorers;
    }

    public GenericCircularLinkedList<String> getTurnOrderNames() {
        return turnOrderNames;
    }

    public GenericQueue<Integer> getPuzzleLogState() {
        return puzzleLog;
    }
}
