package simulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import simulation.contract.Contract;
import simulation.contract.ContractService;
import simulation.entity.Consumer;
import simulation.entity.Distributor;
import simulation.entity.Entity;
import simulation.input.InitialData;
import simulation.input.MonthlyUpdate;

import java.util.*;
import java.util.stream.Collectors;

public final class Simulation {

    @JsonProperty("consumers")
    private Collection<Consumer> consumers = new ArrayList<>();
    @JsonProperty("distributors")
    private Collection<Distributor> distributors = new ArrayList<>();
    @JsonIgnore
    private ContractService contractService = ContractService.instance();
    @JsonIgnore
    private Map<Integer, Long> overDuePayments = new HashMap<>();

    private Simulation() {
    }

    /***
     * Singleton implementation
     * @return the instance of Simulation
     */
    public static Simulation instance() {
        return SimulationHolder.INSTANCE;
    }

    /***
     * Setup Stage
     * @param initialData bean containing initial producer and consumer data
     */
    public void setup(final InitialData initialData) {
        this.consumers.addAll(initialData.getConsumers());
        this.distributors.addAll(initialData.getDistributors());
    }

    /***
     * Apply Stage
     * @param monthlyUpdate Bean with new consumers and distributor cost changes
     */
    public void apply(final MonthlyUpdate monthlyUpdate) {
        this.consumers.addAll(monthlyUpdate.getNewConsumers());
        monthlyUpdate.getCostsChanges().forEach(costChange -> distributors.forEach(
                distributor -> distributor.apply(costChange)));
    }

    /***
     * Offer Stage - Collects offers from all distributors
     * @return true if any offers were collected, false if not
     */
    public boolean offer() {
        distributors.stream().filter(Entity::active)
                .forEach(distributor -> contractService.addOffer(distributor.offer()));

        overDuePayments = contractService.updateContracts();

        return contractService.hasOffers();
    }

    /***
     * Assign Stage : Assigns contracts to all consumers
     * @return true if contracts could be assigned for all consumers, false if not
     */
    public boolean assign() {
        return consumers.stream()
                .filter(Entity::active)
                .allMatch(consumer -> contractService.assignContract(consumer.getId()));
    }

    /***
     * Update Stage : Updates consumers, contracts and distributors
     */
    public void update() {
        consumers.forEach(this::updateConsumer);

        contractService.dateRoll();

        distributors.forEach(this::updateDistributor);
    }

    /***
     * Updates distributor state with incoming/outgoing amounts
     *     and distributor contracts accordingly when bankruptcy occurs
     * @param distributor a distributor instance
     */
    private void updateDistributor(final Distributor distributor) {

        if (!distributor.active()) {
            return;
        }
        /* receive payment */
        Collection<Contract> contracts = contractService.getForProvider(distributor.getId());
        distributor.setContracts(contracts);
        distributor.updateBudget();

        if (!distributor.canPay()) {
            distributor.bankruptcy();
            contractService.removeForProvider(distributor.getId());
        }

        distributor.payDues();

        distributor.setContracts(contractService.getForProvider(distributor.getId()).stream()
                .filter(Contract::active).collect(Collectors.toList()));
    }

    /***
     * Update consumer state with incoming/outgoing amounts
     *          and distributor contracts accordingly when bankruptcy occurs
     * @param consumer a consumer instance
     */
    private void updateConsumer(final Consumer consumer) {

        if (!consumer.active()) {
            return;
        }

        /* get salary */
        consumer.income();

        final Optional<Contract> contractOpt = contractService.getForClient(consumer.getId());
        if (contractOpt.isEmpty()) {
            return;
        }

        /* Pay dues */
        long overDuePayment = overDuePayments.getOrDefault(consumer.getId(), 0L);
        if (overDuePayment != 0 && !consumer.canPay(overDuePayment)) {
            consumer.bankruptcy();
            contractService.getForClient(consumer.getId()).ifPresent(Contract::bankruptcy);
        }
        overDuePayments.put(consumer.getId(), 0L);

        Contract contract = contractOpt.get();
        /* pay contract  */
        final long dueAmount = contract.cost() + overDuePayment;
        if (!consumer.canPay(dueAmount)) {
            if (overDuePayment != 0) {
                consumer.bankruptcy();
                contract.bankruptcy();
            } else {
                if (!contract.postpone()) {
                    consumer.bankruptcy();
                    contract.bankruptcy();
                }
            }
        } else {
            consumer.payDues(dueAmount);
            contract.resume();
        }
    }

    /***
     * Clean up internal state
     */
    public void clean() {
        this.consumers.clear();
        this.distributors.clear();
        this.overDuePayments.clear();
        contractService.clean();
    }

    /***
     * Getter
     */
    public Collection<Consumer> getConsumers() {
        return consumers;
    }

    /***
     * Getter
     */
    public Collection<Distributor> getDistributors() {
        return distributors;
    }

    private static final class SimulationHolder {
        private static final Simulation INSTANCE = new Simulation();
    }
}
