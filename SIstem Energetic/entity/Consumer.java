package simulation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Consumer extends Entity {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int monthlyIncome;

    /***
     * Budget Update
     */
    public void income() {
        this.budget += monthlyIncome;
    }

    /***
     * Decides if this consumer can pay a bill
     * @param amount
     * @return
     */
    public boolean canPay(final long amount) {
        if (this.isBankrupt) {
            return false;
        }
        return this.budget >= amount;
    }

    /***
     * Pays the bill
     * @param amount
     */
    public void payDues(final long amount) {
        this.budget -= amount;
    }
}

