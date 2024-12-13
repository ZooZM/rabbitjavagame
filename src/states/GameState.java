package states;

public class GameState {
    public String gameState;
    public String start ="start";
    public String instruction="instruction";
    public String chooseMode ="chooseMode";
    public String chooseNumberOfPlayers="chooseNumberOfPlayers";
    public String startPlay="startPlay";

    public GameState() {
        this.gameState = "start";
    }

    public String getGameState() {
        return gameState;
    }
    public String getStartState() {
        return start;
    }
    public void setStartPlay() {
        this.gameState = startPlay;
    }

    public void setStart() {
        this.gameState = start;
    }

    public void setInstruction() {
        this.gameState = instruction;
    }

    public void setChooseMode() {
        this.gameState = chooseMode;
    }

    public void setChooseNumberOfPlayers() {
        this.gameState = chooseNumberOfPlayers;
    }
}
