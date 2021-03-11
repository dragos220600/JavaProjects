package simulation.input;

import java.util.List;

public class SimulationInputData {
    private int numberOfTurns;
    private InitialData initialData;
    private List<MonthlyUpdate> monthlyUpdates;

    /**
     * Getter
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     * Getter
     */
    public InitialData getInitialData() {
        return initialData;
    }

    /**
     * Getter
     */
    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}
