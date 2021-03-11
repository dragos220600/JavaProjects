package simulation.contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public final class ContractService {

    private Set<Offer> offers = new TreeSet<>();
    private Collection<Contract> contracts = new ArrayList<>();

    private ContractService() {
    }

    /***
     *
     * @return the ContractService instance
     */
    public static ContractService instance() {
        return ContractServiceHolder.INSTANCE;
    }

    /***
     * Collects an offer from a provider
     * @param offer the offer
     */
    public void addOffer(final Offer offer) {
        this.offers.removeIf(offer1 -> offer1.getProvider() == offer.getProvider());
        this.offers.add(offer);
    }

    /***
     * Getter
     */
    public Optional<Offer> getOffer() {
        return offers.stream().findFirst();
    }

    /***
     * Tests for existing offers
     */
    public boolean hasOffers() {
        return !offers.isEmpty();
    }

    /***
     * Creates a contract for a certain consumer id based on the best offer
     * @param id consumer id
     * @return whether it was created or not
     */
    public boolean assignContract(final int id) {
        if (this.getForClient(id).isPresent()) {
            return true;
        }
        Optional<Offer> offer = getOffer();
        if (offer.isEmpty()) {
            return false;
        }

        this.contracts.add(new Contract(offer.get(), id));

        return true;
    }

    /***
     * Performs date roll on all contracts
     */
    public void dateRoll() {
        contracts.forEach(Contract::roll);
    }

    /***
     * Removes expired contracts while keeping any due amount prior to expiry
     * @return a map of consumer and due amount
     */
    public Map<Integer, Long> updateContracts() {
        Map<Integer, Long> overDuePayments = new HashMap<>();
        contracts.forEach(contract -> {
            long overDueAmount = contract.expireWithPayment();
            overDuePayments.put(contract.getConsumerId(), overDueAmount);
        });

        contracts.removeIf(contract -> !contract.active());

        return overDuePayments;
    }

    /***
     * Retrieves client current contract
     * @param id the consumer id
     */
    public Optional<Contract> getForClient(final int id) {
        return contracts.stream().filter(contract -> contract.getConsumerId() == id).findFirst();
    }

    /***
     * Retrieves provider current contracts
     * @param id the provider id
     * @return the contracts
     */
    public Collection<Contract> getForProvider(final int id) {
        return contracts.stream().filter(contract -> contract.getProvider() == id)
                .collect(Collectors.toList());
    }

    /***
     * Removes all provider activity, used when provider goes bankrupt
     * @param id the provider id
     */
    public void removeForProvider(final int id) {
        this.offers.removeIf(contract -> contract.getProvider() == id);
        this.contracts.removeIf(contract -> contract.getProvider() == id);
    }

    /**
     * Cleans up internal state
     */
    public void clean() {
        this.offers.clear();
        this.contracts.clear();
    }

    private static final class ContractServiceHolder {
        private static final ContractService INSTANCE = new ContractService();
    }
}
