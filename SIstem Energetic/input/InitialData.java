package simulation.input;

import simulation.entity.Consumer;
import simulation.entity.Distributor;

import java.util.Collection;

public class InitialData {
    private Collection<Consumer> consumers;
    private Collection<Distributor> distributors;

    /**
     * Getter
     */
    public Collection<Consumer> getConsumers() {
        return consumers;
    }

    /**
     * Getter
     */
    public Collection<Distributor> getDistributors() {
        return distributors;
    }
}
