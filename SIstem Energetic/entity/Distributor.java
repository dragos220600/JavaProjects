package simulation.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import simulation.contract.Contract;
import simulation.contract.Offer;
import simulation.input.CostChange;

import java.util.Collection;
import java.util.Objects;

public final class Distributor extends Entity {

    @JsonProperty(value = "contractLength", access = JsonProperty.Access.WRITE_ONLY)
    private int contractLength;
    @JsonProperty(value = "initialInfrastructureCost", access = JsonProperty.Access.WRITE_ONLY)
    private long infrastructureCost;
    @JsonProperty(value = "initialProductionCost", access = JsonProperty.Access.WRITE_ONLY)
    private long productionCost;
    @JsonProperty("contracts")
    private Collection<Contract> contracts;

    /***
     * Update distributor data
     * @param costChange bean with changes
     */
    public void apply(final CostChange costChange) {
        if (this.id != costChange.getId()) {
            return;
        }
        this.infrastructureCost = costChange.getInfrastructureCost();
        this.productionCost = costChange.getProductionCost();
    }

    /***
     * Creates an offer out of existing data
     * @return the offer
     */
    public Offer offer() {

        long infraCost = infrastructureCost;
        if (!Objects.isNull(contracts) && !contracts.isEmpty()) {
            infraCost = Math.round(Math.floor(infrastructureCost / contracts.size()));
        }
        final long profit = Math.round(Math.floor(0.2 * productionCost));
        final long contractCost = infraCost + productionCost + profit;

        return new Offer(id, contractCost, contractLength);
    }

    /***
     * Adjusts internal budget with contract adjustements
     */
    public void updateBudget() {
        final long profit = contracts.stream().mapToLong(Contract::yield).sum();
        this.budget = this.budget + profit;
    }

    /***
     *
     * @return boolean deciding if this distributor can pay its dues
     */
    public boolean canPay() {
        return this.budget >= productionCost * contracts.size() + infrastructureCost;
    }

    /***
     * Update internal state with dues
     */
    public void payDues() {
        this.budget = this.budget - productionCost * contracts.size() - infrastructureCost;
    }

    /***
     * Refresh internal contract list
     * @param contracts the current contract list of this distributor
     */
    public void setContracts(final Collection<Contract> contracts) {
        this.contracts = contracts;
    }
}

