package simulation.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Offer implements Comparable<Offer> {

    protected long price;
    @JsonIgnore
    private int provider;
    @JsonIgnore
    private int period;

    public Offer(final int provider, final long price, final int period) {
        this.provider = provider;
        this.price = price;
        this.period = period;
    }

    /***
     * Getter
     * @return the provider id of this offer
     */
    public int getProvider() {
        return provider;
    }

    /***
     * Getter
     * @return the offer price
     */
    public long getPrice() {
        return price;
    }

    /***
     * Getter
     * @return the offer initial time frame
     */
    public int getPeriod() {
        return period;
    }

    /***
     * Comparator returning an order by price/then provider
     */
    @Override
    public int compareTo(final Offer o) {
        int comp = Long.compare(this.price, o.price);
        if (comp == 0) {
            return Integer.compare(this.provider, o.provider);
        } else {
            return comp;
        }
    }
}
