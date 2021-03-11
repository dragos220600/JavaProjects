package simulation.entity;

public final class EntityFactory {
    /***
     * Factory method to create an entity
     * @param entityType type of the entity
     * @return an entity instance of flavor type
     */
    public Entity create(final EntityType entityType) {
        Entity entity = null;
        switch (entityType) {
            case CONSUMER:
                entity = new Consumer();
                break;
            case DISTRIBUTOR:
                entity = new Distributor();
                break;
            default:
                throw new UnsupportedOperationException("Unknown entity type");
        }
        return entity;
    }
}
