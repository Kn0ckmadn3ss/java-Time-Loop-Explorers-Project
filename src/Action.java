
package thetimeloopexplorers1;


public class Action {

    private String description;
    private Explorer explorer;
    private GameState previousActionState;

    public Action(String description, Explorer explorer, GameState previousActionState) {
        this.description = description;
        this.explorer = explorer;
        this.previousActionState = previousActionState;
    }

    public GameState getPreActionState() {
        return previousActionState;
    }

    @Override
    public String toString() {
        return "[" + explorer.getName() + "] " + description;
    }
}
