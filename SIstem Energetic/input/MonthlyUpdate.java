package simulation.input;

import simulation.entity.Consumer;

import java.util.List;

public class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> costsChanges;

    /**
     * Getter
     */
    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    /**
     * Getter
     */
    public List<CostChange> getCostsChanges() {
        return costsChanges;
    }
}
