package simulation.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Contract extends Offer {
    private static final int MAX_POSTPONE_MONTHS = 1;
    private static final double POSTPONE_ADJUSTMENT = 1.2;

    private int consumerId;
    private int remainedContractMonths;
    @JsonIgnore
    private int postponeTimes;
    private State state;

    public Contract(final Offer offer, final int consumerId) {
        super(offer.getProvider(), offer.getPrice(), offer.getPeriod());
        this.remainedContractMonths = offer.getPeriod();
        this.consumerId = consumerId;
        this.state = State.ACTIVE;
        postponeTimes = MAX_POSTPONE_MONTHS;
    }

    /***
     * Computes the price of this contract
     * Used for consumers
     * @return price
     */
    public long cost() {
        if (state != State.POSTPONED) {
            return price;
        }

        return price + Math.round(Math.floor(POSTPONE_ADJUSTMENT * price));
    }

    /***
     * Computes the yield of the contract
     * Used for providers
     * @return yield
     */
    public long yield() {
        return state != State.ACTIVE ? 0 : this.getPrice();
    }

    /***
     * Contract date roll
     */
    public void roll() {
        if (active()) {
            this.remainedContractMonths--;
        }
    }

    /***
     * Method to try and postpone a contract
     * @return true or false
     */
    public boolean postpone() {
        if (state == State.POSTPONED || postponeTimes == 0) {
            return false;
        }
        postponeTimes--;
        this.state = State.POSTPONED;
        return true;
    }

    /***
     * Method to expire a certain contract
     * @return the amount for expiry
     */
    public long expireWithPayment() {

        if (!active() || remainedContractMonths > 0) {
            return 0L;
        }

        long overduePayment = 0L;
        if (remainedContractMonths == 0 && state == State.POSTPONED) {
            overduePayment = Math.round(Math.floor(POSTPONE_ADJUSTMENT * price));
        }

        state = State.EXPIRED;

        return overduePayment;
    }

    /***
     * Expires contract when bankruptcy occurs
     */
    public void bankruptcy() {
        if (!active()) {
            return;
        }

        state = State.EXPIRED;
    }

    /***
     * Sets this contract to an active state
     */
    public void resume() {
        if (!active()) {
            return;
        }
        state = State.ACTIVE;
    }

    /***
     * Checks the state of the contract
     * @return the state
     */
    public boolean active() {
        return state != State.EXPIRED;
    }

    /***
     * Getter
     * @return remained contract months
     */
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    /***
     * Getter
     * @return consumer Id
     */
    public int getConsumerId() {
        return consumerId;
    }

    @Override
    public int compareTo(final Offer o) {
        int comp = super.compareTo(o);
        if (o instanceof Contract) {
            return Integer.compare(this.consumerId, ((Contract) o).consumerId);
        } else {
            return comp;
        }
    }

    private enum State {
        ACTIVE, POSTPONED, EXPIRED
    }
}

