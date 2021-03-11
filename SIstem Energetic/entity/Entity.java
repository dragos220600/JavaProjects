package simulation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

    @JsonProperty("id")
    protected int id;
    @JsonProperty("isBankrupt")
    protected boolean isBankrupt;

    protected long budget;

    /***
     * Getter
     * @return the contract id
     */
    public int getId() {
        return id;
    }

    /***
     * Checks entity state
     * @return bankrupt or not
     */
    public boolean active() {
        return !isBankrupt;
    }

    /***
     * Function used to declare bankruptcy
     */
    public void bankruptcy() {
        isBankrupt = true;
    }

    /***
     * Getter
     * @return bankrupt or not
     */
    @JsonProperty("isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /***
     * Getter
     * @return the budget
     */
    @JsonProperty("budget")
    public long getBudget() {
        return budget;
    }

    /***
     * Setter
     * @param budget the new budget
     */
    @JsonProperty("initialBudget")
    public void setBudget(final long budget) {
        this.budget = budget;
    }
}
